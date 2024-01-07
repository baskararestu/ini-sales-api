package com.enigma.inisalesapi.service;

import com.enigma.inisalesapi.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);
}
