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
    private CustomerDAO customerRepository;
    //@Autowired
    //private KeycloakSecurityContext securityContext;
    //@Autowired
    //private AccessToken accessToken;

	//List<Workspace> workspaces = new ArrayList<>();

    @Autowired
    private WorkspaceRepository workspaceRepository;

	@Value("${cdt.baseDomain}")
	private String baseDomain;

	@RequestMapping("/")
	public String landingPage(Map<String, Object> model) {
		model.put("baseDomain", baseDomain);

		// Return the index page
		return "index";
	}

    @GetMapping("/customers")
    public String customers(Model model, Principal principal) {
        addCustomers();
        /*
        log.info("principal: {}", principal);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            System.out.println("" + headerName);
            System.out.println("" + request.getHeader(headerName));
        }
        */
        //KeycloakPrincipal kcPrincipal=(KeycloakPrincipal)principal;
        /*
        KeycloakSecurityContext session = ((KeycloakPrincipal)(request.getUserPrincipal())).getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        String username = accessToken.getPreferredUsername();
        log.info("User: {} / {}", username, accessToken.getName());
        */
        //log.info("AccessToken: " + securityContext.getTokenString());
        //log.info("User: {} / {}", accessToken.getPreferredUsername(), accessToken.getName());
        //log.info("Principal: {}", principal.getName());
        model.addAttribute(customerRepository.findAll());
        return "customers";
    }

    @GetMapping("/customers/{id}")
    public String customer(@PathVariable("id") Long id, Model model) {
        model.addAttribute(customerRepository.findOne(id));
        return "customer";
    }

	@RequestMapping(value = "/workspace", method=RequestMethod.GET)
	public String showForm(Model model) {

	  model.addAttribute("workspaces", workspaceRepository.findAll());
      model.addAttribute("baseDomain", baseDomain);
      return "workspace";
	}

    // add customers for demonstration
    public void addCustomers() {

        Customer customer1 = new Customer();
        customer1.setAddress("1111 foo blvd");
        customer1.setName("Foo Industries");
        customer1.setServiceRendered("Important services");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setAddress("2222 bar street");
        customer2.setName("Bar LLP");
        customer2.setServiceRendered("Important services");
        customerRepository.save(customer2);

        Customer customer3 = new Customer();
        customer3.setAddress("33 main street");
        customer3.setName("Big LLC");
        customer3.setServiceRendered("Important services");
        customerRepository.save(customer3);
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
