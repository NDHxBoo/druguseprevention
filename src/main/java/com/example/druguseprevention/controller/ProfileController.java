package com.example.druguseprevention.controller;

import com.example.druguseprevention.dto.*;
import com.example.druguseprevention.entity.User;
import com.example.druguseprevention.service.AuthenticationService;
import com.example.druguseprevention.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@SecurityRequirement(name = "api")
public class ProfileController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public ProfileController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    // Người dùng thường cập nhật profile
    @PatchMapping("/update-self")
    public ResponseEntity<String> updateOwnProfile(@RequestBody ProfileDTO profileDTO) {
        userService.updateProfile(profileDTO);
        return ResponseEntity.ok("Profile updated successfully");
    }

    // Admin cập nhật profile người dùng
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin-update")
    public ResponseEntity<String> adminUpdateProfile(@RequestBody AdminProfileDTO profileDTO) {
        userService.adminUpdateProfile(profileDTO);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/password")
    public ResponseEntity<String> resetUserPassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {

        userService.updatePassword(id, request);
        return ResponseEntity.ok("Password reset successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getProfileById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<AdminProfileDTO>> getAllProfiles() {  // Thay List<ProfileDTO> bằng List<AdminProfileDTO>
        return ResponseEntity.ok(userService.getAllProfiles());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<?> createUserByAdmin(@Valid @RequestBody CreateUserRequest request) {
        User newUser = authenticationService.createUserByAdmin(request);
        return ResponseEntity.ok(newUser);
    }
}