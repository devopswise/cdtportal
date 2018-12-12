package com.devopswise.cdtportal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToolStatus {
	@JsonProperty("jenkins")
	private String jenkins = null;

	public String getJenkins() {
		return jenkins;
	}

	public void setJenkins(String jenkins) {
		this.jenkins = jenkins;
	}
}
