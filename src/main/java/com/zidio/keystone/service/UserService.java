package com.zidio.keystone.service;
import com.zidio.keystone.entity.User;
import com.zidio.keystone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> existing = userRepository.findById(id);

        if (existing.isPresent()) {
            User updated = existing.get();

            updated.setUsername(userDetails.getUsername());
            updated.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            updated.setRole(userDetails.getRole());

            return userRepository.save(updated);
        }

        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}