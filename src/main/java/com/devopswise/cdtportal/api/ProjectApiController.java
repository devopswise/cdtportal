package com.devopswise.cdtportal.api;

import com.devopswise.cdtportal.model.Project;
import com.devopswise.cdtportal.project.ProjectService;
import com.devopswise.cdtportal.tool.Gitea;

import io.gitea.ApiClient;
import io.gitea.ApiException;
import io.gitea.Configuration;
import io.gitea.api.AdminApi;
import io.gitea.api.OrganizationApi;
import io.gitea.auth.HttpBasicAuth;
import io.gitea.model.CreateOrgOption;
import io.gitea.model.Organization;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-22T22:35:56.040Z")

@Controller
public class ProjectApiController implements ProjectApi {

	@Autowired
	private ProjectService projectService;
	
    public ResponseEntity<Object> addproject(@ApiParam(value = "Project description to create" ,required=true )  @Valid @RequestBody Project body) {
        // do some magic!
    	//TODO read config from file
    	//create proper exception classes
    	/* 
			Try
			    Get gittool
			    If (gitttool.orgExist())
			        Throw existexc
			    Get citool
			    If (citool.exist)
			         Throw existExc
			     Gittool.createProj()
			     Citool.createProj()
			Catch existException
			Catch createexception
			      Succeeded = false
			Catch exception
			      Succeeded = false
			Finally
			     If not succeeded
			      If gitproject != null 
			         Gittool.delete()
			      If ciproject != null
			         Citool.delete()
			Try
			     Try
			         Check jira
			         Check ...
			      Catch apiexception
			          If existexc set msg throwexistexception
			      Create jira
			      Create
			 Catch apiexception
			 Catch exist exception
			 Finally
			Return
    	 */
    	
    	
    	Project project = new Project();
    	project.setKey(body.getKey());
    	project.setName(body.getName());
        boolean succeeded = false;
    	
    	try {
    		// make this singleton
    		// https://stackoverflow.com/a/6205288/1047804
    		Gitea giteaClient = new Gitea("http://gitea.cdt.devopswise.co.uk/api/v1", "local.admin", "Jah8q123!");
    		if (giteaClient.projectExist(body.getKey())){
    			throw new CDTException("Gitea organization already exists");
    		}
    		
    	    Organization giteaOrg = giteaClient.createOrg(body.getKey(), body.getName(), body.getDescription());
    		succeeded = true;		
    	} catch (CDTException e) {
    		
    	} catch (ApiException e) {
    		
    	} finally {
    		if (succeeded) {
            	project.setKey(body.getKey());
            	project.setDescription(body.getDescription());    			
    		} else {
    		}

    	}
    	
    	projectService.addProject(project);
        return new ResponseEntity<Object>(project, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProject(@ApiParam(value = "project id to delete",required=true ) @PathVariable("projectId") Long projectId,
        @ApiParam(value = "" ) @RequestHeader(value="api_key", required=false) String apiKey) {
    	Project projectToDelete = projectService.getProject(projectId);
        
    	boolean succeeded = false;
        try {
    		// https://stackoverflow.com/a/6205288/1047804
    		Gitea giteaClient = new Gitea("http://gitea.cdt.devopswise.co.uk/api/v1", "local.admin", "Jah8q123!");
    		if (! giteaClient.projectExist(projectToDelete.getKey())){
    			throw new CDTException("Gitea organization doesnt exists");
    		}
    		giteaClient.deleteOrg(projectToDelete.getKey());
    		succeeded = true;			
        } catch (CDTException e) {
    		succeeded = false;
    	} catch (ApiException e) {
    		succeeded = false;
    		//"Exception when calling AdminApi#adminDeleteUser"
    	} finally {
    		if (succeeded) {
    	    	projectService.deleteProject(projectId);    			
    		}
    	}
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<Project>> findProjectsByStatus( @NotNull@ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @RequestParam(value = "status", required = true) List<String> status) {
        // do some magic!
    	List<Project> projects = projectService.getAllProjects();
        return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
    }

    public ResponseEntity<List<Project>> findProjectsByTags( @NotNull@ApiParam(value = "Tags to filter by", required = true) @RequestParam(value = "tags", required = true) List<String> tags) {
        // do some magic!
        return new ResponseEntity<List<Project>>(HttpStatus.OK);
    }

    public ResponseEntity<Project> getProjectById(@ApiParam(value = "ID of project to return",required=true ) @PathVariable("projectId") Long projectId) {
        // do some magic!
        return new ResponseEntity<Project>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateProject(@ApiParam(value = "project object that needs to be added to the store" ,required=true )  @Valid @RequestBody Project body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateProjectWithForm(@ApiParam(value = "ID of project that needs to be updated",required=true ) @PathVariable("projectId") Long projectId,
        @ApiParam(value = "Updated name of the project") @RequestPart(value="name", required=false)  String name,
        @ApiParam(value = "Updated status of the project") @RequestPart(value="status", required=false)  String status) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
