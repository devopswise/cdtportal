package com.devopswise.cdtportal.api;

import com.devopswise.cdtportal.model.Workspace;
import com.devopswise.cdtportal.workspace.WorkspaceRepository;
import com.devopswise.cdtportal.workspace.WorkspaceService;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-10T16:50:20.069Z")

@Controller
public class WorkspaceApiController implements WorkspaceApi {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private WorkspaceRepository workspaceRepository;

    private static Logger log = LoggerFactory.getLogger(WorkspaceService.class);

    public ResponseEntity<Object> createWorkspace(@ApiParam(value = "Workspace description to create" ,required=true )  @Valid @RequestBody Workspace body) {
        Workspace workspace = null;
        workspace = workspaceService.createWorkspace(body.getOwner(), body.getGitUrl());

    	if (workspace != null){
            return new ResponseEntity<Object>(workspace, HttpStatus.OK);
    	} else {
    		ApiException ex = new ApiException(0, "Cannot create workspace");
            return new ResponseEntity<Object>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    public ResponseEntity<Void> deleteWorkspace(@ApiParam(value = "workspace id to delete",required=true ) @PathVariable("workspaceId") Long workspaceId,
        @ApiParam(value = "" ) @RequestHeader(value="api_key", required=false) String apiKey) {
        workspaceService.deleteWorkspace(workspaceId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<Workspace>> findWorkspacesByName( @NotNull@ApiParam(value = "name of the workspace", required = true) @RequestParam(value = "workspaceName", required = true) String workspaceName) {
    	List<Workspace> workspace = workspaceService.getWorkspaceByName(workspaceName);
        return new ResponseEntity<List<Workspace>>(workspace, HttpStatus.OK);
    }

    public ResponseEntity<List<Workspace>> findWorkspacesByOwner( @NotNull@ApiParam(value = "username of workspace owner", required = true) @RequestParam(value = "username", required = true) String username) {
    	List<Workspace> workspace = workspaceService.getWorkspaceByOwner(username);
        log.debug("controller-owner=" + username);
        return new ResponseEntity<List<Workspace>>(workspace, HttpStatus.OK);
    }

    public ResponseEntity<List<Workspace>> findWorkspacesByStatus( @NotNull@ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @RequestParam(value = "status", required = true) List<String> status) {
    	List<Workspace> workspace = workspaceService.getAllWorkspaces();
        return new ResponseEntity<List<Workspace>>(workspace, HttpStatus.OK);
    }

    public ResponseEntity<Workspace> getWorkspaceById(@ApiParam(value = "ID of workspace to return",required=true ) @PathVariable("workspaceId") Long workspaceId) {
        Workspace workspace = workspaceService.getWorkspace(workspaceId);
        return new ResponseEntity<Workspace>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateWorkspace(@ApiParam(value = "workspace object that needs to be added to the store" ,required=true )  @Valid @RequestBody Workspace body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateWorkspaceWithForm(@ApiParam(value = "ID of workspace that needs to be updated",required=true ) @PathVariable("workspaceId") Long workspaceId,
        @ApiParam(value = "Updated name of the workspace") @RequestPart(value="name", required=false)  String name,
        @ApiParam(value = "Updated status of the workspace") @RequestPart(value="status", required=false)  String status) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
