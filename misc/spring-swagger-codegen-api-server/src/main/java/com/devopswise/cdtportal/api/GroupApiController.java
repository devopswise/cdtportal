package com.devopswise.cdtportal.api;

import com.devopswise.cdtportal.model.Group;
import java.util.List;
import com.devopswise.cdtportal.model.User;

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
public class GroupApiController implements GroupApi {



    public ResponseEntity<Void> createGroupWithArrayInput(@ApiParam(value = "List of user object" ,required=true )  @Valid @RequestBody List<User> body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteGroup(@ApiParam(value = "The Name of group that needs to be deleted",required=true ) @PathVariable("groupName") String groupName) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<Group>> findAllGroups() {
        // do some magic!
        return new ResponseEntity<List<Group>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> findGroupMembers(@ApiParam(value = "ID of pet to fetch",required=true ) @PathVariable("groupName") Integer groupName,
        @ApiParam(value = "Updated user object" ,required=true )  @Valid @RequestBody User body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> removeUserFromGroup(@ApiParam(value = "ID of pet to fetch",required=true ) @PathVariable("groupName") Integer groupName,
        @ApiParam(value = "Updated user object" ,required=true )  @Valid @RequestBody User userId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
