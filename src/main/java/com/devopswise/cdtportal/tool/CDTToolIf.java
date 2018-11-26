package com.devopswise.cdtportal.tool;

import com.devopswise.cdtportal.api.CDTException;

public interface CDTToolIf {
	public String getVersion();
	public boolean ping();
	public boolean projectExist(String projectKey) throws CDTException;
}
