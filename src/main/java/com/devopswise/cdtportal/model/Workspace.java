package com.devopswise.cdtportal.model;

public class Workspace {
	  private Long id = null;
	  private String name;
	  private String owner;
	  private String git_url;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getGit_url() {
		return git_url;
	}
	public void setGit_url(String git_url) {
		this.git_url = git_url;
	}
	}