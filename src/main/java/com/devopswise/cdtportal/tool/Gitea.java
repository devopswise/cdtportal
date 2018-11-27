package com.devopswise.cdtportal.tool;

import com.devopswise.cdtportal.api.CDTException;

import io.gitea.ApiClient;
import io.gitea.ApiException;
import io.gitea.Configuration;
import io.gitea.api.AdminApi;
import io.gitea.api.OrganizationApi;
import io.gitea.auth.HttpBasicAuth;
import io.gitea.model.CreateOrgOption;
import io.gitea.model.Organization;

public class Gitea extends GitTool {
    private ApiClient client = null;
    private String baseUrl = null;
    private String username = null;
    private String password = null;
    private boolean debug = false;
    
	public Gitea(String baseUrl, String username, String password) {
		super();
		this.baseUrl = baseUrl;
		this.username = username;
		this.password = password;
        ApiClient giteaClient = Configuration.getDefaultApiClient();
        giteaClient.setBasePath(this.baseUrl);
        giteaClient.setDebugging(true);
        
        HttpBasicAuth giteaBasicAuth = (HttpBasicAuth) giteaClient.getAuthentication("BasicAuth");
        giteaBasicAuth.setUsername(this.username);
        giteaBasicAuth.setPassword(this.password);
        
        this.client = giteaClient;
	}

	public ApiClient getClient() {
		return client;
	}

	@Override
	public boolean projectExist(String projectKey) throws CDTException {
		// a gitea organization is used as namespace
		// assume not exists only set true if it is exists
		String giteaOrg = projectKey;
		boolean result = false;
        if (giteaOrg == null) 
        	throw new CDTException("Project key is not valid");
        
        Organization resultGiteaOrg = null;
        OrganizationApi apiInstance = new OrganizationApi();
        
        try {
          resultGiteaOrg = apiInstance.orgGet(giteaOrg);
        } catch (ApiException e) {
        	//TODO only if it is 404 Exception
        	return result;
        }
        if (resultGiteaOrg != null)
        	return true;
		return result;
	}
    
	public Organization createOrg(String orgName, String orgFullName, String orgDescription) throws ApiException {
        AdminApi giteaAdminApiInstance = new AdminApi();
        CreateOrgOption createOrgOption = new CreateOrgOption();
        createOrgOption.description(orgDescription);
        createOrgOption.fullName(orgFullName); // project key
        createOrgOption.username(orgName);
        createOrgOption.website("created using cdt");
        createOrgOption.location("UK");
            
        Organization result = giteaAdminApiInstance.adminCreateOrg(username, createOrgOption);
        return result;
	}
	
    public void deleteOrg(String orgName) throws ApiException{
    	AdminApi apiInstance = new AdminApi();
    	String username = orgName; // String | username of user to delete
    	apiInstance.adminDeleteUser(username);
    }

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
