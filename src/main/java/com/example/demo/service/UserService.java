package com.example.demo.service;

import com.example.demo.common.constant.UserConstant;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.UserRole;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    @Autowired private RoleRepository roleRepository;

    @Autowired private UserRoleRepository userRoleRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public Page<User> findAll(Specification<User> filter, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        return userRepository.findAll(filter, paging);
    }

    @Transactional
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }

    @Transactional
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }

    @Transactional
    public User create(User user) {
        Boolean userExists = userRepository.existsByEmail(user.getEmail());
        if (userExists) {
            throw new EntityExistsException("User already exists");
        }

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(UserConstant.SYSTEM_USER.getId());

        return userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        User userEntity = findById(user.getId());

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        return userRepository.save(userEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Transactional
    public User assignRole(User user, String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new EntityNotFoundException("Role not found");
        }

        UserRole userRole = userRoleRepository.findByUserAndRole(user, role.get());
        if (userRole != null) {
            throw new EntityExistsException("User role already exists");
        }

        userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role.get());
        userRoleRepository.save(userRole);

        return findById(user.getId());
    }

    @Transactional
    public User unassignRole(User user, String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new EntityNotFoundException("Role not found");
        }

        UserRole userRole = userRoleRepository.findByUserAndRole(user, role.get());
        if (userRole == null) {
            throw new EntityNotFoundException("User role not found");
        }

        userRoleRepository.delete(userRole);
        return findById(user.getId());
    }
}
