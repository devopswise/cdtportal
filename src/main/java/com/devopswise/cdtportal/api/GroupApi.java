/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.devopswise.cdtportal.api;

import com.devopswise.cdtportal.model.Group;
import java.util.List;
import com.devopswise.cdtportal.model.User;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-12-12T17:43:26.471Z")

@Api(value = "group", description = "the group API")
public interface GroupApi {

    @ApiOperation(value = "Creates group with users with given input array", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "api_key"),
        @Authorization(value = "cdtportal_auth")
    }, tags={ "group", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Void.class) })
    
    @RequestMapping(value = "/group",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> createGroupWithArrayInput(@ApiParam(value = "List of user object" ,required=true )  @Valid @RequestBody List<User> body);


    @ApiOperation(value = "Delete group", notes = "This can only be done by the admin", response = Void.class, authorizations = {
        @Authorization(value = "api_key"),
        @Authorization(value = "cdtportal_auth")
    }, tags={ "group", })
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid group supplied", response = Void.class),
        @ApiResponse(code = 404, message = "Group not found", response = Void.class) })
    
    @RequestMapping(value = "/group/{groupName}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteGroup(@ApiParam(value = "The Name of group that needs to be deleted",required=true ) @PathVariable("groupName") String groupName);


    @ApiOperation(value = "Finds all groups", notes = "Multiple status values can be provided with comma separated strings", response = Group.class, responseContainer = "List", authorizations = {
        @Authorization(value = "api_key"),
        @Authorization(value = "cdtportal_auth")
    }, tags={ "group", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Group.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid status value", response = Void.class) })
    
    @RequestMapping(value = "/group",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Group>> findAllGroups();


    @ApiOperation(value = "Add Members to group", notes = "Multiple status values can be provided with comma separated strings", response = Void.class, authorizations = {
        @Authorization(value = "api_key"),
        @Authorization(value = "cdtportal_auth")
    }, tags={ "group", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Invalid status value", response = Void.class) })
    
    @RequestMapping(value = "/group/{groupName}/members",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> findGroupMembers(@ApiParam(value = "ID of pet to fetch",required=true ) @PathVariable("groupName") Integer groupName,@ApiParam(value = "Updated user object" ,required=true )  @Valid @RequestBody User body);


    @ApiOperation(value = "Remove members from group", notes = "Multiple status values can be provided with comma separated strings", response = Void.class, authorizations = {
        @Authorization(value = "api_key"),
        @Authorization(value = "cdtportal_auth")
    }, tags={ "group", })
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid status value", response = Void.class),
        @ApiResponse(code = 200, message = "successful operation", response = Void.class) })
    
    @RequestMapping(value = "/group/{groupName}/members",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<Void> removeUserFromGroup(@ApiParam(value = "ID of pet to fetch",required=true ) @PathVariable("groupName") Integer groupName,@ApiParam(value = "Updated user object" ,required=true )  @Valid @RequestBody User userId);

}