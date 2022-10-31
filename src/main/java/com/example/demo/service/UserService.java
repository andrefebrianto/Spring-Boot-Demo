package com.example.demo.service;

import com.example.demo.common.constant.UserConstant;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

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
}
