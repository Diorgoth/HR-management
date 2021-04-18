package com.example.demo.repository;



import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);

     Optional<User> findByEmail(String email);
List<User> findAllByRolesIn(Collection<Set<Role>> roles);

}
