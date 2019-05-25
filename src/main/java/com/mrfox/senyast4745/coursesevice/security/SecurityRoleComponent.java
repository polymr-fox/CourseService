package com.mrfox.senyast4745.coursesevice.security;

import com.mrfox.senyast4745.articleservice.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("securityService")
public class SecurityRoleComponent {


        public boolean hasPermission( Role...permissions){
            // loop over each submitted role and validate the user has at least one
            Collection<? extends GrantedAuthority> userAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            for( Role permission : permissions){
                if( userAuthorities.contains( new SimpleGrantedAuthority(permission.name())))
                    return true;
            }

            // no matching role found
            return false;
        }

}
