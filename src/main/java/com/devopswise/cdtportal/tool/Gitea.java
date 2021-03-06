package com.devopswise.cdtportal.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devopswise.cdtportal.api.CDTException;

import io.gitea.ApiClient;
import io.gitea.ApiException;
import io.gitea.Configuration;
import io.gitea.api.AdminApi;
import io.gitea.api.MiscellaneousApi;
import io.gitea.api.OrganizationApi;
import io.gitea.auth.HttpBasicAuth;
import io.gitea.model.CreateOrgOption;
import io.gitea.model.Organization;
import io.gitea.model.ServerVersion;
import io.swagger.Swagger2SpringBoot;

@Service
public class Gitea extends GitTool {
    private ApiClient client = null;
    private String baseUrl = null;
    private String username = null;
    private String password = null;
    private String version = null;
    private static Logger logger = null;

    @Value("${gitea.debug}")
    private boolean debug = false;
    
	public Gitea(@Value("${gitea.baseUrl}")String baseUrl, 
			@Value("${gitea.username}")String username, 
			@Value("${gitea.password}")String password) {
		super();
		this.baseUrl = baseUrl;
		this.username = username;
		this.password = password;
        ApiClient giteaClient = Configuration.getDefaultApiClient();
        giteaClient.setBasePath(this.baseUrl);
        
		logger = LoggerFactory.getLogger(Gitea.class);
        if (logger.isDebugEnabled() || debug) {
            giteaClient.setDebugging(debug);        	
        }
        
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

	public String getVersion() {
		ServerVersion result = null;
		MiscellaneousApi apiInstance = new MiscellaneousApi();
		try {
		    result = apiInstance.getVersion();
		    //System.out.println(result);
		    logger.debug(result.toString());
		} catch (ApiException e) {
			logger.error("Exception when calling MiscellaneousApi#getVersion");
		    //System.err.println("Exception when calling MiscellaneousApi#getVersion");
		    e.printStackTrace();
		}
		version = result.getVersion();
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
