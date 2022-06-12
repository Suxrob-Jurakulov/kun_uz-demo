package com.company.service;

import com.company.dto.RegionDTO;
import com.company.entity.RegionEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        Optional<RegionEntity> optional = regionRepository.findByKey(dto.getKey());
        if (optional.isPresent()){
            throw new BadRequestException("This region already exist");
        }
        RegionEntity entity = new RegionEntity();
        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        regionRepository.save(entity);

        dto.setCreatedDate(entity.getCreated_Date());
        dto.setId(entity.getId());
        return dto;
    }

    public List<RegionDTO> list() {
        Iterable<RegionEntity> all = regionRepository.findAll();
        List<RegionDTO> list = new LinkedList<>();
        for (RegionEntity entity : all) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setCreatedDate(entity.getCreated_Date());

            list.add(dto);
        }
        return list;
    }

    public void update(RegionDTO dto, String key) {
       RegionEntity entity = get(key);
       entity.setKey(dto.getKey());
       entity.setNameUz(dto.getNameUz());
       entity.setNameRu(dto.getNameRu());
       entity.setNameEn(dto.getNameEn());
       regionRepository.save(entity);
    }

    public RegionEntity get(String key){
       return regionRepository.findByKey(key).orElseThrow(() -> {
            throw new ItemNotFoundException("This region not found");
        });
    }

    public void delete(String key) {
        Optional<RegionEntity> byKey = regionRepository.findByKey(key);
        if (byKey.isEmpty()){
            throw new BadRequestException("This region not found");
        }
        regionRepository.deleteByKey(key);
    }
    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Region not found");
        });
    }
}
