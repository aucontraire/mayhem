package com.mm.mayhem.service;

import com.mm.mayhem.model.db.users.Admin;
import com.mm.mayhem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Optional<Admin> getAdminByUuid(UUID uuid) {
        return adminRepository.findAdminByUuid(uuid);
    }

    public Optional<List<Admin>> getAdminsByUsername(String username) {
        return adminRepository.findAdminsByUsername(username);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}
