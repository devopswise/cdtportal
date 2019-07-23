package io.swagger;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.devopswise.cdtportal.model.Group;
import com.devopswise.cdtportal.model.User;
import com.devopswise.cdtportal.user.GroupRepository;
import com.devopswise.cdtportal.user.UserRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories("com.devopswise.cdtportal")
@EntityScan(basePackages = "com.devopswise.cdtportal")
@ComponentScan(basePackages = { "io.swagger", "com.devopswise.cdtportal" })
public class Swagger2SpringBoot implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(Swagger2SpringBoot.class);

    @Value("${keycloak.auth-server-url}")
    private String keycloakAuthServerUrl = null;

    @Value("${keycloak.realm}")
    private String keycloakRealm = null;

    @Value("${keycloak.resource}")
    private String keycloakClientName = null;

    @Value("${keycloak.credentials.secret}")
    private String keycloakClientSecret = null;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(Swagger2SpringBoot.class).run(args);
    }

    @PostConstruct
    public void setup(){
        log.info("keycloakAuthServerUrl:"+keycloakAuthServerUrl);

        /*
		Keycloak kc = KeycloakBuilder.builder() //
				.serverUrl(keycloakAuthServerUrl) //
				.realm(keycloakRealm) //
				.grantType(OAuth2Constants.PASSWORD) //
				.clientId(keycloakClientName) //
				.clientSecret(keycloakClientSecret) //
                .username("alice.developer")
                .password("password")
				.build();
                */
            /*
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName("testrole");
        roleRepresentation.setClientRole(true);
        kc.realm("master").roles().create(roleRepresentation);
        roleRepresentation = kc.realm("master").roles().get("testrole").toRepresentation();
        log.info("testrole: "+roleRepresentation.toString());


        // Get realm
        RealmResource realmResource = kc.realm("master");
        UsersResource userRessource = realmResource.users();
        // Assign realm role tester to user
        //userRessource.get("alice.developer").roles().realmLevel() //
        //        .add(Arrays.asList(roleRepresentation));

		// Get client
		ClientRepresentation app1Client = realmResource.clients() //
				.findByClientId("cdt").get(0);
            log.info("here5");

		// Get client level role (requires view-clients role)
		RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()) //
				.roles().get("testrole").toRepresentation();

        //log.info("role: " + userRessource.get("onur.ozkol").roles().realmLevel().listAll());
		// Assign client level role to user
		userRessource.get("d8f39672-cf7c-4cac-b3da-bcc82c225a1d").roles().clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));
        */
        /*

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("test123");
        credential.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        user.setUsername("testuser2");
        user.setFirstName("Test2");
        user.setLastName("User2");
        user.setEmail("aaa@gmail.com");
        user.setCredentials(Arrays.asList(credential));
        user.setEnabled(true);
        user.setRealmRoles(Arrays.asList("admin"));

        // Create testuser
        Response result = kc.realm("ait-platform").users().create(user);
        if (result.getStatus() != 201) {
            System.err.println("Couldn't create user.");
            System.exit(0);
        } */
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AccessToken getAccessToken() {
        log.info("Accestoken: {}", ((KeycloakPrincipal) getRequest().getUserPrincipal()).getKeycloakSecurityContext().getToken());
        return ((KeycloakPrincipal) getRequest().getUserPrincipal()).getKeycloakSecurityContext().getToken();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public KeycloakSecurityContext getKeycloakSecurityContext() {
        return ((KeycloakPrincipal) getRequest().getUserPrincipal()).getKeycloakSecurityContext();
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
