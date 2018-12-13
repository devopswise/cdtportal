package com.devopswise.cdtportal.tool;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devopswise.cdtportal.api.CDTException;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.helper.JenkinsVersion;
import com.offbytwo.jenkins.model.Job;

@Service
public class Jenkins implements CIToolIf {

	private JenkinsServer jenkins = null;
	JenkinsVersion jenkinsVersion = null;
    private String baseUrl = null;
    private String username = null;
    private String password = null;
    private boolean debug = false;
    
	public Jenkins(@Value("${jenkins.baseUrl}")String baseUrl, 
			@Value("${jenkins.username}")String username, 
			@Value("${jenkins.password}")String password) {
		super();
		this.baseUrl=baseUrl;
		this.username=username;
		this.password=password;
		try {
			JenkinsServer client = new JenkinsServer(new URI(this.baseUrl), this.username, this.password);
			this.jenkins = client;
			this.jenkinsVersion = jenkins.getVersion();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createFolder(String folderName) throws CDTException, IOException{
		jenkins.createFolder(folderName);
	}
	
	public void deleteFolder(String folderName) throws IOException{
		jenkins.deleteJob(null,folderName);
	}
	@Override
	public String getVersion() throws CDTException {
        try {
            jenkinsVersion = jenkins.getVersion();
			if (jenkinsVersion == null)
				throw new CDTException("Unknown jenkins version");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		// TODO Auto-generated method stub
		return jenkinsVersion.toString();
	}

	@Override
	public boolean projectExist(String projectKey) throws CDTException {
		boolean result = false;
		Map<String, Job> jobs;
		try {
			jobs = jenkins.getJobs();
			if (jobs.containsKey(projectKey)){
				result = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
