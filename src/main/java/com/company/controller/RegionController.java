package com.company.controller;

import com.company.dto.RegionDTO;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RegionDTO dto, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        RegionDTO regionDTO = regionService.create(dto);
        return ResponseEntity.ok().body(regionDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        List<RegionDTO> list = regionService.list();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/update/{key}")
    public ResponseEntity<?> update(@PathVariable("key") String key, @RequestBody RegionDTO dto, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        regionService.update(dto, key);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @DeleteMapping("/delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        regionService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }
}
