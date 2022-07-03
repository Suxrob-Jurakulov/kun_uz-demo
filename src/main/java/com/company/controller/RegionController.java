package com.company.controller;

import com.company.dto.RegionDTO;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "Region")
@RestController
@RequestMapping("region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "Create", notes = "Method for create region")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody @Valid RegionDTO dto,
                                    HttpServletRequest request) {
        log.info("Request for create {}", dto);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        RegionDTO regionDTO = regionService.create(dto);
        return ResponseEntity.ok().body(regionDTO);
    }

    @PostMapping("/adm/create/region")
    public ResponseEntity<?> create(@RequestBody RegionDTO dto){
        RegionDTO response = regionService.create(dto);
        return ResponseEntity.ok().body(dto);
    }


    @ApiOperation(value = "List", notes = "Method for get all region for admin")
    @GetMapping("/adm/list")
    public ResponseEntity<?> list(HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<RegionDTO> list = regionService.list();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Update", notes = "Method for update region")
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody @Valid RegionDTO dto,
                                    HttpServletRequest request) {
        log.info("Request for update {}", dto);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.update(dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @ApiOperation(value = "Delete", notes = "Method for delete region for admin")
    @DeleteMapping("/adm/delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key,
                                    HttpServletRequest request) {
        log.info("Request for delete {}", key);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        regionService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }
}
