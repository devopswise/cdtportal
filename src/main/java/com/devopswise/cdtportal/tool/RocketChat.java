package com.devopswise.cdtportal.tool;

import javax.naming.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devopswise.cdtportal.api.CDTException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

@Service
public class RocketChat implements ChatToolIf {

    private String baseUrl = null;
    private String username = null;
    private String password = null;
    private String version = null;
    
    @Value("${rocketChat.debug}")
    private boolean debug;
    private String apiUserId = null;
    private String authToken = null;
    private boolean loggedIn = false;

	public RocketChat(@Value("${rocketChat.baseUrl}")String baseUrl, 
			@Value("${rocketChat.username}")String username, 
			@Value("${rocketChat.password}")String password) {
		super();
		this.baseUrl=baseUrl;
		this.username=username;
		this.password=password;
        if (baseUrl.endsWith("/")) {
        	this.baseUrl = this.baseUrl.substring(0, this.baseUrl.length() - 1);
        } else {
        	this.baseUrl = baseUrl;
        }        
	}
    
    public boolean login(){
    	this.loggedIn = false;
    	try {
    		JSONObject args = new JSONObject();
    		args.put("user", this.username);
    		args.put("password", this.password);
        	JSONObject response = doREST("POST", "/api/v1/login", args.toString());
        	this.apiUserId = response.getJSONObject("data").getString("userId"); 
        	this.authToken = response.getJSONObject("data").getString("authToken");
        	this.loggedIn = true;
    	} catch (AuthenticationException ex){
    		this.loggedIn = false;
    	}
    	return this.loggedIn;
    }
    
    public String getVersion() throws CDTException{
    	this.version = null;
    	try {
        	JSONObject result = doREST("GET", "/api/v1/info", "");
        	this.version = result.getJSONObject("info").getString("version");    		
    	} catch  (AuthenticationException ex){
    		ex.printStackTrace();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
        if (this.version == null){
        	throw new CDTException("RocketChat instance is not accessible");
        }

        return this.version;
    }
    
	public boolean createPrivateRoom(String roomName) throws CDTException {
		boolean result = false;
    	if (!loggedIn){
    		login();
    	}

    	try {
    		JSONObject args = new JSONObject().put("name", roomName);
			JSONObject response = doREST("POST", "/api/v1/groups.create", args.toString());
			if (response.getBoolean("success")){
				result = true;
			}
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}
    
	public boolean deletePrivateRoom(String roomName) throws CDTException {
		boolean result = false;
    	if (!loggedIn){
    		login();
    	}

    	try {
    		JSONObject args = new JSONObject().put("roomName", roomName);
			JSONObject response = doREST("POST", "/api/v1/groups.delete", args.toString());
			if (response.getBoolean("success")){
				result = true;
			}
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	@Override
	public boolean projectExist(String projectKey) throws CDTException {
		boolean result = false;
    	if (!loggedIn){
    		login();
    	}

    	try {
			JSONObject response = doREST("GET", "/api/v1/groups.listAll", "");
			JSONArray groups = response.getJSONArray("groups");
			for (int i = 0 ; i < groups.length(); i++){
				if (groups.getJSONObject(i).get("name").toString().equalsIgnoreCase(projectKey)){
					result = true; 
					return result;
				}				
			}
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}
    private JSONObject doREST(String method, String context, String arg) throws AuthenticationException {
    	JSONObject responseJson = null;

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        if (this.debug){
            client.addFilter(new LoggingFilter(System.out));
        }

        WebResource webResource;
        String url;
        if ("GET".equalsIgnoreCase(method)) {
            url = this.baseUrl + context + arg;
        } else {
            url = this.baseUrl + context;
        }
        webResource = client.resource(url);
        
        WebResource.Builder builder;
        if (this.loggedIn){
            builder = webResource.header("X-Auth-Token", this.authToken)
            		.header("X-User-Id", this.apiUserId)
            		.type("application/json")
            		.accept("application/json");
        } else {
            builder = webResource.type("application/json")
            		.accept("application/json");        	
        }
        ClientResponse response;

        if ("GET".equalsIgnoreCase(method)) {
            response = builder.get(ClientResponse.class);
        } else if ("POST".equalsIgnoreCase(method)) {
            response = builder.post(ClientResponse.class, arg);
        } else {
            response = builder.method(method, ClientResponse.class);
        }

        if (response.getStatus() == 401) {
            throw new AuthenticationException("HTTP 401 received: Invalid Username or Password.");
        }

        String jsonResponse = response.getEntity(String.class);
        responseJson = new JSONObject(jsonResponse);

        return responseJson;
    }
    
    public boolean isDebug() {
		return debug;
	}


	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
