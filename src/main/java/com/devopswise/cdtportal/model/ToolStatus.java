package com.devopswise.cdtportal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToolStatus {
	@JsonProperty("jenkins")
	private String jenkins = null;

	@JsonProperty("gitea")
	private String gitea = null;

	@JsonProperty("rocketChat")
	private String rocketChat = null;

	public String getGitea() {
		return gitea;
	}

	public void setGitea(String gitea) {
		this.gitea = gitea;
	}
	
	public String getRocketChat() {
		return rocketChat;
	}

	public void setRocketChat(String rocketChat) {
		this.rocketChat = rocketChat;
	}

	public String getJenkins() {
		return jenkins;
	}

	public void setJenkins(String jenkins) {
		this.jenkins = jenkins;
	}
}
