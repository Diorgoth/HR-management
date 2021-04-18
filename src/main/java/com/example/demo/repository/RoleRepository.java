package com.example.demo.repository;


import com.example.demo.entity.Role;
import com.example.demo.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {



    Optional<Role> findByRoleName(RoleName roleName);

}
