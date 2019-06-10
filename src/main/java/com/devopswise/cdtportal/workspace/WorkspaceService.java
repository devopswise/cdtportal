package com.devopswise.cdtportal.workspace;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devopswise.cdtportal.model.Workspace;

@Service
public class WorkspaceService {
	@Autowired
	private WorkspaceRepository workspaceRepository;

    private static Logger log = LoggerFactory.getLogger(WorkspaceService.class);

	@Value("${cdt.baseDomain}")
	private String baseDomain;

	public List<Workspace> getAllWorkspaces() {
		List<Workspace> workspaces = new ArrayList<>();
		Iterable workspaceIterable = workspaceRepository.findAll();

		for (Iterator<Workspace> i = workspaceIterable.iterator(); i.hasNext();) {
		    Workspace item = i.next();
		    workspaces.add(item);
		}
		return workspaces;
	}

    public Workspace getWorkspace(String name){
		List<Workspace> workspaces = new ArrayList<>();
		Iterable workspaceIterable = workspaceRepository.findAll();

		for (Iterator<Workspace> i = workspaceIterable.iterator(); i.hasNext();) {
		    Workspace item = i.next();
		    if (item.getName().equalsIgnoreCase(name)){
                return item;
            }
		}
		return null;
	}

    public List<Workspace> getWorkspaceByOwner(String username){
		List<Workspace> workspaces = new ArrayList<>();
		List<Workspace> result = new ArrayList<>();
		Iterable workspaceIterable = workspaceRepository.findAll();
            log.debug("username: " + username);

		for (Iterator<Workspace> i = workspaceIterable.iterator(); i.hasNext();) {
		    Workspace item = i.next();
            log.debug("workspace-owner: " + item.getOwner());

		    if (item.getOwner().equalsIgnoreCase(username)){
                result.add(item);
            }
		}
		return result;
	}

    public List<Workspace> getWorkspaceByName(String workspaceName){
		List<Workspace> workspaces = new ArrayList<>();
		List<Workspace> result = new ArrayList<>();
		Iterable workspaceIterable = workspaceRepository.findAll();

		for (Iterator<Workspace> i = workspaceIterable.iterator(); i.hasNext();) {
		    Workspace item = i.next();
            log.debug("workspace-name: " + item.getName());
		    if (item.getName().equalsIgnoreCase(workspaceName)){
                result.add(item);
            }
		}
		return result;
	}

	public Workspace getWorkspace(Long id){
		return (Workspace) workspaceRepository.findOne(id);
	}

	public Workspace createWorkspace(String owner, String gitUrl){
		String workspaceName = null;
        int returnCode = -1;
        ProcessBuilder pb = new ProcessBuilder("start-ws", owner, gitUrl, "up");
        Map<String, String> envs = pb.environment();

        //envs.put("VAR2", env.get("VAR1") + "suffix");

        File workingFolder = new File("/opt/cdt/script");
        pb.directory(workingFolder);

        envs.put("BASE_DOMAIN", baseDomain);
        pb.redirectErrorStream();
        try {
            log.debug("running command: " + pb.command());
        	Process proc = pb.start();
            BufferedReader stdInput = new BufferedReader(new
           	     InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
           	     InputStreamReader(proc.getErrorStream()));

            returnCode = proc.waitFor();
	        log.debug("returnCode : " + returnCode);

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                 log.debug("stdout : " + s);
                 workspaceName = new String(s);
            }

            log.info("workspaceName: " + workspaceName);

            while ((s = stdError.readLine()) != null) {
               log.debug("stderr : " + s);
            }

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

        if ((returnCode == 0) && workspaceName != null){
            Workspace workspace = new Workspace();
            workspace.setName(workspaceName);
            workspace.setOwner(owner);
            workspace.setGitUrl(gitUrl);
            workspace = workspaceRepository.save(workspace);
            return workspace;
        } else {
            return null;
        }
	}

    /*
	public void updateWorkspace(String id, Workspace workspace){
		workspaceRepository.save(workspace);
	}*/

    public void deleteWorkspace(String name){
        Workspace workspaceToDelete = getWorkspace(name);
        if (workspaceToDelete != null){
            this.deleteWorkspace(workspaceToDelete.getId());
        }
    }

	public void deleteWorkspace(Long workspaceId){
        log.info("now, will delete workspace with Id: " + workspaceId);

        Workspace workspaceToDelete = workspaceRepository.findOne(workspaceId);

	    log.info("found workspace: " + workspaceToDelete.getId()+ workspaceToDelete.getName());
    	    ProcessBuilder pb = new ProcessBuilder("start-ws",
	    		   workspaceToDelete.getOwner(),
	    		   workspaceToDelete.getGitUrl(), "down");
	        Map<String, String> envs = pb.environment();

	        File workingFolder = new File("/opt/cdt/script");
	        pb.directory(workingFolder);

	        envs.put("BASE_DOMAIN", baseDomain);
	        pb.redirectErrorStream();
	        try {
	        	Process proc = pb.start();
	            BufferedReader stdInput = new BufferedReader(new
	           	     InputStreamReader(proc.getInputStream()));

	            BufferedReader stdError = new BufferedReader(new
	           	     InputStreamReader(proc.getErrorStream()));

	             // read the output from the command
	             String s = null;
	             while ((s = stdInput.readLine()) != null) {
	                 log.debug("stdout : " + s);
	             }

	             // read any errors from the attempted command
	             while ((s = stdError.readLine()) != null) {
	                 log.debug("stderr : " + s);
	             }
	             int returnCode = proc.waitFor();
	             log.debug("returnCode : " + returnCode);
	             if (returnCode == 0) {
                     workspaceRepository.delete(workspaceToDelete);
	             }
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
	}
}
