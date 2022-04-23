package com.alphabetas.nymosgroup.service;

import com.alphabetas.nymosgroup.model.ClientModel;
import com.alphabetas.nymosgroup.repo.UserRepo;
import com.alphabetas.nymosgroup.service.utils.EncodePasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientModel clientModel = userRepo.findByUsername(username);
        if(clientModel == null){
            System.out.println("User Not Found!");
            return null;
        }
        System.out.println("User found!");
//        clientModel.setPassword(EncodePasswordUtil.encode(clientModel.getPassword()));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(clientModel.getRole()));
        return new User(
                username,
                clientModel.getPassword(),
                authorities
        );
    }
}
//bucket