package com.dame.slackde.security;

import com.dame.slackde.entity.User;
import com.dame.slackde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Utilisateur introuvable !");

        List<GrantedAuthority> auths = new ArrayList<>();
        //Si l'utilisateur existe boucler (forEach) pour récupérer les roles
        user.getRoles().forEach(role -> {
            GrantedAuthority auhority = new SimpleGrantedAuthority(role);
            //ajouter les roles à l'utilisateur
            auths.add(auhority);
        });

        return new org.springframework.security.core.
                userdetails.User(user.getUsername(), user.getPassword(), auths);
    }
}
