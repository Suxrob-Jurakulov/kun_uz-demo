package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create/{role}")
    public ResponseEntity<?> create(@PathVariable("role") String role, @RequestBody ProfileDTO dto, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        ProfileDTO profileDTO = profileService.create(dto, role);
        return ResponseEntity.ok().body(profileDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ProfileDTO dto, @RequestHeader("Authorization") String token) {
        Integer id = JwtUtil.decode(token);
        profileService.update(id, dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @PutMapping("/updateByAdmin/{id}")
    public ResponseEntity<?> updateByAdmin(@PathVariable("id") Integer id,
                                           @RequestBody ProfileDTO dto,
                                           @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        profileService.update(id, dto);
        return ResponseEntity.ok().body("Updated");
    }

    @GetMapping("/list/{role}")
    public ResponseEntity<?> list(@PathVariable("role") String role, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        List<ProfileDTO> list = profileService.list(role);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        profileService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

}
