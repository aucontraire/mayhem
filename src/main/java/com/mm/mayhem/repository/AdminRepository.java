package com.mm.mayhem.repository;

import com.mm.mayhem.model.db.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findAdminByUuid(UUID uuid);
    Optional<List<Admin>> findAdminsByUsername(String username);

}
