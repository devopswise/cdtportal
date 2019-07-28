package com.devopswise.cdtportal.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.devopswise.cdtportal.model.Workspace;
import com.devopswise.cdtportal.workspace.WorkspaceRepository;
import com.devopswise.cdtportal.workspace.WorkspaceService;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private static Logger log = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private WorkspaceService workspaceService;

    @Autowired
    private KeycloakSecurityContext securityContext;

    @Autowired
    private AccessToken accessToken;

    @Autowired
    private WorkspaceRepository workspaceRepository;

	@Value("${cdt.baseDomain}")
	private String baseDomain;

	@RequestMapping("/")
	public String landingPage(Map<String, Object> model) {
        model.put("username", accessToken.getPreferredUsername());
		model.put("baseDomain", baseDomain);
		return "index";
	}

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        request.logout();
        return "redirect:/";
    }

	@RequestMapping("/workspace")
    public String workspace(Model model, Principal principal) {
        String username = accessToken.getPreferredUsername();
        Boolean isAdmin = accessToken.getRealmAccess().getRoles().contains("admin");
        log.debug("realm roles: "+accessToken.getRealmAccess().getRoles().toString());
        log.debug("others: "+accessToken.getOtherClaims().toString());

        /* user with admin role can delete other's workspaces */
        if (isAdmin){
            log.debug("listing all workspaces:"+workspaceService.getAllWorkspaces());
            model.addAttribute("workspaces", workspaceService.getAllWorkspaces());
        } else {
            model.addAttribute("workspaces", workspaceService.getWorkspaceByOwner(username));
            log.debug("listing user workspaces:"+workspaceService.getWorkspaceByOwner(username));
        }

        model.addAttribute("username", username);
        model.addAttribute("baseDomain", baseDomain);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            System.out.println("" + headerName);
            System.out.println("" + request.getHeader(headerName));
        }

        log.debug("AccessToken: " + securityContext.getTokenString());
        log.debug("User: {} / {}", accessToken.getPreferredUsername(), accessToken.getName());
        log.debug("Principal: {}", principal.getName());
		return "workspace";
	}

	@RequestMapping("/debug")
    public String debug(Model model, Principal principal) {
        model.addAttribute("baseDomain", baseDomain);
        model.addAttribute("username", accessToken.getPreferredUsername());

		return "debug";
	}

	@RequestMapping(value = "/workspace", method=RequestMethod.POST)
	public String workspaceCreate(Map<String, Object> model, @RequestParam String owner, @RequestParam String git_url) {
        workspaceService.createWorkspace(owner, git_url);
		return "redirect:workspace";
	}

	@RequestMapping(value = "/deleteWorkspace", method=RequestMethod.POST)
	public String workspaceDelete(@RequestParam Long id) {
        workspaceService.deleteWorkspace(id);
		return "redirect:workspace";
	}
}
