package com.zidio.keystone.controller;

import com.zidio.keystone.dto.UserDTO;
import com.zidio.keystone.entity.User;
import com.zidio.keystone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
     @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @PostMapping
    public User createUser(@Valid@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return userService.createUser(user);
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,@Valid @RequestBody UserDTO userDTO) {
        User userDetails = new User();
        userDetails.setUsername(userDTO.getUsername());
        userDetails.setPassword(userDTO.getPassword());
        userDetails.setRole(userDTO.getRole());
        return userService.updateUser(id, userDetails);
    }
    @DeleteMapping("/{id}")
        public void deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
        }
    
}
