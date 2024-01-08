package com.enigma.inisalesapi.service;


import com.enigma.inisalesapi.dto.response.AdminResponse;
import com.enigma.inisalesapi.entity.Admin;

public interface AdminService {
    AdminResponse create(Admin admin);

    AdminResponse getById(String id);
}
