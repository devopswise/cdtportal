package io.swagger;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories("com.devopswise.cdtportal")
@EntityScan(basePackages = "com.devopswise.cdtportal")
@ComponentScan(basePackages = { "io.swagger", "com.devopswise.cdtportal" })
public class Swagger2SpringBoot implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(Swagger2SpringBoot.class);
	
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
        //log.info("Spring LDAP - CRUD Operations Binding and Unbinding Example");
    	
    	//System.out.println("test");
    	
        //System.exit(-1);
        //User alice = userRepository.findByName("bob.developer").get(0);
        //log.info("alice:"+alice.getUid());
        
		//String groupName = "prj_"+"hello"+"-all";
		//Group g = groupRepository.findOne(groupName);
		//groupRepository.delete(g);
		
        
        
        
        //System.exit(-1);
    }
    
    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
