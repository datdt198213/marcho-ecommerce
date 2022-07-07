package com.ncc.repository;

import com.ncc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
