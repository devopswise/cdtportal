package com.devopswise.cdtportal.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import com.devopswise.cdtportal.model.User;

import javax.naming.Name;
import javax.naming.directory.*;
import javax.naming.ldap.LdapName;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class UserRepository implements BaseLdapNameAware {

    @Autowired
    private LdapTemplate ldapTemplate;
    private LdapName baseLdapPath;

    public void setBaseLdapPath(LdapName baseLdapPath) {
        this.baseLdapPath = baseLdapPath;
    }


    public void create(User p) {
        Name dn = buildDn(p);
        ldapTemplate.bind(dn, null, buildAttributes(p));
    }

    public List<User> findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass", "inetOrgPerson");
        return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), new PersonContextMapper());
    }

    public User findOne(String uid) {
        Name dn = LdapNameBuilder.newInstance(baseLdapPath)
                .add("ou", "people")
                .add("uid", uid)
                .build();
        return ldapTemplate.lookup(dn, new PersonContextMapper());
    }

    public List<User> findByName(String name) {
        LdapQuery q = query()
                .where("objectclass").is("inetOrgPerson")
                .and("cn").whitespaceWildcardsLike(name);
        return ldapTemplate.search(q, new PersonContextMapper());
    }

    public void update(User p) {
        ldapTemplate.rebind(buildDn(p), null, buildAttributes(p));
    }

    public void updateLastName(User p) {
        Attribute attr = new BasicAttribute("sn", p.getLastName());
        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        ldapTemplate.modifyAttributes(buildDn(p), new ModificationItem[] {item});
    }

    public void delete(User p) {
        ldapTemplate.unbind(buildDn(p));
    }

    private Name buildDn(User p) {
    	Name result = LdapNameBuilder.newInstance()
                .add("ou", "people")
                .add("cn", p.getUid())
                .build();
        return result;
    }

    private Attributes buildAttributes(User p) {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocAttr = new BasicAttribute("objectclass");
        ocAttr.add("top");
        ocAttr.add("inetOrgPerson");
        attrs.put(ocAttr);
        attrs.put("ou", "people");
        attrs.put("cn", p.getUid());
        attrs.put("cn", p.getFullName());
        attrs.put("sn", p.getLastName());
        return attrs;
    }


    private static class PersonContextMapper extends AbstractContextMapper<User> {
        public User doMapFromContext(DirContextOperations context) {
            User user = new User();
            user.setFullName(context.getStringAttribute("cn"));
            user.setLastName(context.getStringAttribute("sn"));
            user.setUid(context.getStringAttribute("uid"));
            return user;
        }
    }
}
