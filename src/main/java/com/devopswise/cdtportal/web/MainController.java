package com.devopswise.cdtportal.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.devopswise.cdtportal.model.Workspace;

@Controller
public class MainController {

	List<Workspace> workspaces = new ArrayList<>();

	@Value("${cdt.baseDomain}")
	private String baseDomain;

	@RequestMapping("/")
	public String landingPage(Map<String, Object> model) {
		model.put("baseDomain", baseDomain);

		// Return the index page
		return "index";
	}

	@RequestMapping(value = "/workspace", method=RequestMethod.GET)
	public String showForm(Model model) {
	  model.addAttribute("workspaces", workspaces);
      return "workspace";
	}

	@RequestMapping(value = "/workspace", method=RequestMethod.POST)
	public String workspaceCreate(Map<String, Object> model, @RequestParam String owner, @RequestParam String git_url) {
		System.out.println("owner :"+ owner);
		System.out.println("git_url :" + git_url );
		String workspaceName = null;
        //ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "E:/home/onur/workspace/cdtportal/misc/start-ws.bat", owner, git_url, "up");
        ProcessBuilder pb = new ProcessBuilder("start-ws", owner, git_url, "up");
        Map<String, String> envs = pb.environment();

        //envs.put("VAR1", "myValue");
        //envs.remove("OTHERVAR");
        //envs.put("VAR2", env.get("VAR1") + "suffix");

        File workingFolder = new File("/opt/cdt/script");
        pb.directory(workingFolder);

        //System.out.println(envs.get("Path"));
        envs.put("BASE_DOMAIN", baseDomain);
        pb.redirectErrorStream();
        try {
        	Process proc = pb.start();
            BufferedReader stdInput = new BufferedReader(new
           	     InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
           	     InputStreamReader(proc.getErrorStream()));

             // read the output from the command
             //System.out.println("Here is the standard output of the command:\n");
             String s = null;
             while ((s = stdInput.readLine()) != null) {
                 System.out.println(s);
                 workspaceName = new String(s);
             }

             System.out.println("workspaceName= " + workspaceName);
             //workspaceName = s;
             // read any errors from the attempted command
             System.out.println("Here is the standard error of the command (if any):\n");
             while ((s = stdError.readLine()) != null) {
               System.out.println(s);
             }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("workspaceName: " + workspaceName);

		Workspace workspace = new Workspace();
		workspace.setName(workspaceName);
		workspace.setId(new Long(workspaces.size()));
		workspace.setOwner(owner);
		workspace.setGit_url(git_url);
		workspaces.add(workspace);
		return "redirect:workspace";
	}

	@RequestMapping(value = "/deleteWorkspace", method=RequestMethod.POST)
	public String workspaceDelete(@RequestParam Long id) {
	    System.out.println("Student_Id : "+id);
	    Workspace workspaceToDelete = null;
	    for (Workspace workspace : workspaces) {
		    if (workspace.getId().equals(id)){
		    	workspaceToDelete = workspace;
		    	break;
		    }
		}

	    System.out.println("deleting: " + workspaceToDelete.getId()+ workspaceToDelete.getName());

	       //ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C",
	    	//	   "E:/home/onur/workspace/cdtportal/misc/start-ws.bat",
	    	//	   workspaceToDelete.getOwner(),  workspaceToDelete.getGit_url(), "down");
	        ProcessBuilder pb = new ProcessBuilder("start-ws",
	    		   workspaceToDelete.getOwner(),
	    		   workspaceToDelete.getGit_url(), "down");
	        Map<String, String> envs = pb.environment();

	        //envs.put("VAR1", "myValue");
	        //envs.remove("OTHERVAR");
	        //envs.put("VAR2", env.get("VAR1") + "suffix");

	        File workingFolder = new File("/opt/cdt/script");
	        pb.directory(workingFolder);

	        //System.out.println(envs.get("Path"));
	        envs.put("BASE_DOMAIN", baseDomain);
	        pb.redirectErrorStream();
	        try {
	        	Process proc = pb.start();
	            BufferedReader stdInput = new BufferedReader(new
	           	     InputStreamReader(proc.getInputStream()));

	            BufferedReader stdError = new BufferedReader(new
	           	     InputStreamReader(proc.getErrorStream()));

	             // read the output from the command
	             System.out.println("Here is the standard output of the command:\n");
	             String s = null;
	             while ((s = stdInput.readLine()) != null) {
	                 System.out.println(s);
	             }

	             //System.out.println("workspaceName= " + workspaceName);
	             //workspaceName = s;
	             // read any errors from the attempted command
	             System.out.println("Here is the standard error of the command (if any):\n");
	             while ((s = stdError.readLine()) != null) {
	               System.out.println(s);
	             }
	             int returnCode = proc.waitFor();
	             System.out.println("returnCode: " + returnCode);
	             if (returnCode == 0) {
	            	 workspaces.remove(workspaceToDelete);
	             }
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "redirect:workspace";
	}
}
