package com.devopswise.cdtportal.api;


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

import com.devopswise.cdtportal.model.ToolStatus;
import com.devopswise.cdtportal.tool.Gitea;
import com.devopswise.cdtportal.tool.Jenkins;
import com.devopswise.cdtportal.tool.RocketChat;

import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-12-12T17:43:26.471Z")

@Controller
public class ToolApiController implements ToolApi {
	@Autowired
	private Jenkins jenkins;

	@Autowired
	private RocketChat rocketChat;

	@Autowired
	private Gitea gitea;
	
    public ResponseEntity<ToolStatus> toolStatus() throws CDTException {
    	ToolStatus status = new ToolStatus();
    	status.setJenkins(jenkins.getVersion());
    	status.setRocketChat(rocketChat.getVersion());
    	status.setGitea(gitea.getVersion());
    	
        return new ResponseEntity<ToolStatus>(status, HttpStatus.OK);
    }

}
