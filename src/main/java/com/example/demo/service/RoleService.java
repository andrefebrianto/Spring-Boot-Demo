package com.example.demo.service;

import com.example.demo.model.entity.Role;
import com.example.demo.repository.RoleRepository;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired private RoleRepository roleRepository;

    @Transactional
    public Page<Role> findAll(Specification<Role> filter, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        return roleRepository.findAll(filter, paging);
    }

    @Transactional
    public Role findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new EntityNotFoundException("Role not found");
        }
        return role.get();
    }

    @Transactional
    public Role findByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            throw new EntityNotFoundException("Role not found");
        }
        return role.get();
    }

    @Transactional
    public Role create(Role role) {
        Boolean roleExists = roleRepository.existsByName(role.getName());
        if (roleExists) {
            throw new EntityExistsException("Role already exists");
        }
        return roleRepository.save(role);
    }

    @Transactional
    public Role update(Role role) {
        Role roleEntity = findById(role.getId());
        roleEntity.setName(role.getName());
        return roleRepository.save(roleEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        Role role = findById(id);
        roleRepository.delete(role);
    }
}
