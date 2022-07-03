package com.company.controller;

import com.company.dto.ProfileDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "Profile")
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "Create", notes = "Method for create profile")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody @Valid ProfileDTO dto,
                                    HttpServletRequest request) {
        log.info("Request for create {}", dto);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok().body(profileDTO);
    }

    @ApiOperation(value = "Update", notes = "Method for update profile")
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody @Valid ProfileDTO dto,
                                   HttpServletRequest request) {
        log.info("Request for update {}", dto);
        Integer profileId = HttpHeaderUtil.getId(request);
        profileService.update(profileId, dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @ApiOperation(value = "Update by admin", notes = "Method for update profile by admin")
    @PutMapping("/adm/updateByAdmin")
    public ResponseEntity<?> updateByAdmin(@RequestBody @Valid ProfileDTO dto,
                                           HttpServletRequest request) {
        log.info("Request for update by admin {}", dto);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.update(dto.getId(), dto);
        return ResponseEntity.ok().body("Updated");
    }

    @ApiOperation(value = "List", notes = "Method for list all by role")
    @GetMapping("/adm/list/{role}")
    public ResponseEntity<?> list(@PathVariable("role") String role,
                                  HttpServletRequest request) {
        log.info("Request for list by role {}", role);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<ProfileDTO> list = profileService.list(role);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Delete", notes = "Method for delete profile")
    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        log.info("Request for delete {}", id);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        profileService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @ApiOperation(value = "Set photo", notes = "Method for set photo as main")
    @PutMapping("/adm/image")
    public ResponseEntity<?> set(MultipartFile file, HttpServletRequest request){
        log.info("Request for set photo {}", file);
        Integer pId = HttpHeaderUtil.getId(request);
        String response = profileService.setImage(pId, file);
        return ResponseEntity.ok().body(response);
    }
}
