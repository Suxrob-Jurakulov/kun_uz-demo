package com.company.controller;

import com.company.dto.RegionDTO;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody RegionDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        RegionDTO regionDTO = regionService.create(dto);
        return ResponseEntity.ok().body(regionDTO);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<?> list(HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<RegionDTO> list = regionService.list();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody RegionDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.update(dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @DeleteMapping("/adm/delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }
}
