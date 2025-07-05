package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Services.UserService;
import org.skillswap.skillswapbackend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PutMapping("/{id}/premium")
    public ResponseEntity<UserDTO> togglePremium(@PathVariable Long id) {
        return ResponseEntity.ok(userService.togglePremium(id));
    }

    @GetMapping("/premium")
    public ResponseEntity<List<UserDTO>> getPremiumUsers() {
        return ResponseEntity.ok(userService.getPremiumUsers());
    }
}