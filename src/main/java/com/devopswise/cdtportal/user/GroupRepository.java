package com.devopswise.cdtportal.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import com.devopswise.cdtportal.model.Group;
import com.devopswise.cdtportal.model.User;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class GroupRepository implements BaseLdapNameAware {

    @Autowired
    private LdapTemplate ldapTemplate;
    private LdapName baseLdapPath;

    @Value("${spring.ldap.username}")
    private String adminUser;
    
    public void setBaseLdapPath(LdapName baseLdapPath) {
        this.baseLdapPath = baseLdapPath;
    }

    public List<Group> findAll(){
        return ldapTemplate.search(
                query().where("objectclass").is("groupOfUniqueNames"),
                new GroupContextMapper());
    }

    public Group findOne(String name){
        //not working
    	Name dn = LdapNameBuilder.newInstance(baseLdapPath)
        		.add("ou", "groups")
                .add("cn", name)
                .build();    	
        return ldapTemplate.lookup(dn, new GroupContextMapper());
    }
    
    public boolean groupExists(String groupName){
    	//TODO implement findOne and use lookup
    	Group result = null;
    	/*
    	try {
    		result = findOne(groupName);
    	}
    	catch (NameNotFoundException e){
    	}*/
    	List<Group> groups = findAll();
    	for(Group g:groups){
    		if (g.getName().equalsIgnoreCase(groupName)){
    			return true;
    		}
    	}
    	return false;
    }
    
    public void addMemberToGroup(String groupName, User p) {
        Name groupDn = buildGroupDn(groupName);
        Name personDn = buildPersonDn(p);

        if (! rdnExists(ldapTemplate, groupDn)){
        	Group g = new Group();
        	g.setName(groupName);
        	ldapTemplate.bind(groupDn, null, buildAttributes(g,p));
        }
        DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
        ctx.addAttributeValue("uniqueMember", personDn);

        ldapTemplate.modifyAttributes(ctx);
    }

    public void removeMemberFromGroup(String groupName, User p) {
        Name groupDn = buildGroupDn(groupName);
        Name personDn = buildPersonDn(p);

        DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
        ctx.removeAttributeValue("uniqueMember", personDn);

        ldapTemplate.modifyAttributes(ctx);
    }

    private Name buildGroupDn(String groupName) {
        return LdapNameBuilder.newInstance()
                .add("ou", "groups")
                .add("cn", groupName)
                .build();
    }

    private Name buildPersonDn(User user) {
        return LdapNameBuilder.newInstance()
                .add("ou", "people")
                .add("cn", user.getUid())
                .build();
    }

    private static class GroupContextMapper extends AbstractContextMapper<Group> {
        public Group doMapFromContext(DirContextOperations context) {
            Group group = new Group();
            group.setName(context.getStringAttribute("cn"));
            Object[] members = context.getObjectAttributes("uniqueMember");
            for (Object member : members){
                Name memberDn = LdapNameBuilder.newInstance(String.valueOf(member)).build();
                group.addMember(memberDn);
            }
            return group;
        }
    }
    
    private static boolean rdnExists(LdapTemplate ldapTemplate, Name rdn) {
        try {
            ldapTemplate.lookup(rdn);
            return true;
        } catch (org.springframework.ldap.NamingException ne) {
            return false;
        }
    }
    
    private Attributes buildAttributes(Group g, User p) {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocAttr = new BasicAttribute("objectclass");
        ocAttr.add("top");
        ocAttr.add("groupOfUniqueNames");
        attrs.put("uniqueMember", buildPersonDn(p).toString()+baseLdapPath.toString());
        attrs.put(ocAttr);
        attrs.put("ou", "groups");
        attrs.put("cn", g.getName());
        return attrs;
    }
    
    public void delete(Group g) {
        ldapTemplate.unbind(buildGroupDn(g.getName()));
    }
}
