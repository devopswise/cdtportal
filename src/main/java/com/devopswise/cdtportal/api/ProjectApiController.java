package com.devopswise.cdtportal.api;

import com.devopswise.cdtportal.model.Group;
import com.devopswise.cdtportal.model.Project;
import com.devopswise.cdtportal.model.User;
import com.devopswise.cdtportal.project.ProjectService;
import com.devopswise.cdtportal.tool.Gitea;
import com.devopswise.cdtportal.tool.Jenkins;
import com.devopswise.cdtportal.tool.RocketChat;
import com.devopswise.cdtportal.user.GroupRepository;
import com.devopswise.cdtportal.user.UserRepository;
import com.offbytwo.jenkins.helper.JenkinsVersion;

import io.gitea.ApiException;
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

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-22T22:35:56.040Z")

@Controller
public class ProjectApiController implements ProjectApi {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private Jenkins jenkins;
	
	@Autowired
	private RocketChat rocketChat;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Gitea gitea;
	
    public ResponseEntity<Object> addproject(@ApiParam(value = "Project description to create" ,required=true )  @Valid @RequestBody Project body) {
    	Project project = new Project();
    	project.setKey(body.getKey());
    	project.setName(body.getName());
        boolean succeeded = false;
    	
    	try {
    	    //validate users on ldap
    		//verify lead is also in the users, if not add him/her as well
    	    List<String> projectMembers = body.getUsers();
    	    if (! projectMembers.contains(body.getLead())){
    	    	projectMembers.add(body.getLead());
    	    }
    	    
    	    for(String username:projectMembers){
        	    if (! userRepository.userExists(username)) {
        			throw new CDTException("This user doesnot exists: "+ username);   			
        	    }
    	    }
    		String groupName = "prj_"+body.getKey().toLowerCase()+"-all";
    		if (groupRepository.groupExists(groupName)){
    			throw new CDTException("This group already exists in directory: "+ groupName);   			
    		}

    		if (gitea.projectExist(body.getKey())){
    			throw new CDTException("Gitea organization already exists");
    		}

    		if (jenkins.projectExist(body.getKey())){
    	    	throw new CDTException("Jenkins folder already exists");
    	    }

    	    if(rocketChat.projectExist(body.getKey())){
    	    	throw new CDTException("RocketChat private group already exists: "+body.getKey());
    	    }

    	    for(String username:projectMembers){
    	    	User u = userRepository.findByName(username).get(0);
    	    	groupRepository.addMemberToGroup(groupName, u);
    	    }
    	    
    	    Organization giteaOrg = gitea.createOrg(body.getKey(), body.getName(), body.getDescription());
    	        	    
    	    jenkins.createFolder(body.getKey());
    	    
    	    rocketChat.createPrivateRoom(body.getKey());
    	        	    
    		succeeded = true;		
    	} catch (CDTException e) {
    		
    	} catch (ApiException e) {
    		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
    		if (succeeded) {
            	project.setKey(body.getKey());
            	project.setDescription(body.getDescription());
            	project.setUsers(body.getUsers());
    		} else {
    		}
    	}
    	if (succeeded){
        	projectService.addProject(project);
        	Project projectCreated = projectService.getProject(project.getKey());
            return new ResponseEntity<Object>(projectCreated, HttpStatus.OK);    		
    	} else {
    		ApiException ex = new ApiException("Cannot create");
            return new ResponseEntity<Object>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    public ResponseEntity<Void> deleteProject(@ApiParam(value = "project id to delete",required=true ) @PathVariable("projectId") Long projectId,
        @ApiParam(value = "" ) @RequestHeader(value="api_key", required=false) String apiKey) {
    	Project projectToDelete = projectService.getProject(projectId);
        
    	boolean succeeded = false;
        try {    		
    		if (! gitea.projectExist(projectToDelete.getKey())){
    			throw new CDTException("Gitea organization doesnt exists");
    		}
    		gitea.deleteOrg(projectToDelete.getKey());
    		jenkins.deleteFolder(projectToDelete.getKey());
    		rocketChat.deletePrivateRoom(projectToDelete.getKey());
    		
    		String groupName = "prj_"+projectToDelete.getKey().toLowerCase()+"-all";
    		Group g = groupRepository.findOne(groupName);
    		groupRepository.delete(g);
    		
    		succeeded = true;			
        } catch (CDTException e) {
    		succeeded = false;
    	} catch (ApiException e) {
    		succeeded = false;
    		//"Exception when calling AdminApi#adminDeleteUser"
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
