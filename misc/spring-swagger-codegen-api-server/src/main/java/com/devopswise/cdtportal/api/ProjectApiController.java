package com.devopswise.cdtportal.api;

import com.devopswise.cdtportal.model.Project;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-10T16:50:20.069Z")

@Controller
public class ProjectApiController implements ProjectApi {



    public ResponseEntity<Void> createProject(@ApiParam(value = "Project description to create" ,required=true )  @Valid @RequestBody Project body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProject(@ApiParam(value = "project id to delete",required=true ) @PathVariable("projectId") Long projectId,
        @ApiParam(value = "" ) @RequestHeader(value="api_key", required=false) String apiKey) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<Project>> findProjectsByStatus( @NotNull@ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @RequestParam(value = "status", required = true) List<String> status) {
        // do some magic!
        return new ResponseEntity<List<Project>>(HttpStatus.OK);
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
