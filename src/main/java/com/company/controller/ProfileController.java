package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody ProfileDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok().body(profileDTO);
    }

    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody ProfileDTO dto,
                                   HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        profileService.update(profileId, dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @PutMapping("/adm/updateByAdmin")
    public ResponseEntity<?> updateByAdmin(@RequestBody ProfileDTO dto,
                                           HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.update(dto.getId(), dto);
        return ResponseEntity.ok().body("Updated");
    }

    @GetMapping("/adm/list/{role}")
    public ResponseEntity<?> list(@PathVariable("role") String role,
                                  HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<ProfileDTO> list = profileService.list(role);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

}
