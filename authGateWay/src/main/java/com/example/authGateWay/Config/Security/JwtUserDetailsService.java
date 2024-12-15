package com.example.authGateWay.Config.Security;


import com.example.authGateWay.Entity.UserEntity;
import com.example.authGateWay.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity _user = userRepository.findByUserName(userName);
        if (_user == null) {
            System.out.println("test2");
            throw new UsernameNotFoundException("User not found with userName: " + userName);

        }
        GrantedAuthority authority = new SimpleGrantedAuthority(_user.getRole());

        return new org.springframework.security.core.userdetails.User(
                _user.getUserName(),
                _user.getPassword(),
                Collections.singletonList(authority) // Single role as a list
        );

    }

//    private UserDetails buildUserForAuthentication(UserEntity user, List<GrantedAuthority> authorities) {
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//    }
}