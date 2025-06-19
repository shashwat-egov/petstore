package com.example.petstore.controller;

import com.example.petstore.model.User;
import com.example.petstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/createWithList")
    public List<User> createUsersWithList(@Valid @RequestBody List<User> users) {
        return userService.saveAll(users);
    }

    @GetMapping("/login")
    public String loginUser(@RequestParam(required = false) String username,
                            @RequestParam(required = false) String password) {
        return "logged in";
    }

    @GetMapping("/logout")
    public void logoutUser() {
        // nothing to do
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username,
                                           @Valid @RequestBody User user) {
        Optional<User> existing = userService.findByUsername(username);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User entity = existing.get();
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPassword(user.getPassword());
        entity.setPhone(user.getPhone());
        entity.setUserStatus(user.getUserStatus());
        return ResponseEntity.ok(userService.save(entity));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        Optional<User> existing = userService.findByUsername(username);
        if (existing.isPresent()) {
            userService.delete(existing.get());
        }
        return ResponseEntity.ok().build();
    }
}
