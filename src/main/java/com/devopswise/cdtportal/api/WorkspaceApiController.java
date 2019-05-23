package com.devopswise.cdtportal.api;

import com.devopswise.cdtportal.model.Workspace;

import io.swagger.annotations.*;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-22T14:33:59.301+01:00")

@Controller
public class WorkspaceApiController implements WorkspaceApi {

    public ResponseEntity<Void> createWorkspace(@ApiParam(value = "Workspace description to create" ,required=true )  @Valid @RequestBody Workspace body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteWorkspace(@ApiParam(value = "workspace id to delete",required=true ) @PathVariable("workspaceId") Long workspaceId,
        @ApiParam(value = "" ) @RequestHeader(value="api_key", required=false) String apiKey) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<Workspace>> findWorkspacesByStatus( @NotNull@ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @RequestParam(value = "status", required = true) List<String> status) {
        // do some magic!
        return new ResponseEntity<List<Workspace>>(HttpStatus.OK);
    }

    public ResponseEntity<Workspace> getWorkspaceById(@ApiParam(value = "ID of workspace to return",required=true ) @PathVariable("workspaceId") Long workspaceId) {
        // do some magic!
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
