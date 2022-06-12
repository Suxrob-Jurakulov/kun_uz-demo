package com.company.service;

import com.company.dto.TypesDTO;
import com.company.entity.TypesEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TypesService {
    @Autowired
    private TypesRepository typesRepository;

    public TypesDTO create(TypesDTO dto) {
        Optional<TypesEntity> optional = typesRepository.findByKey(dto.getKey());
        if (optional.isPresent()){
            throw new BadRequestException("This article_type already exist");
        }
        TypesEntity entity = new TypesEntity();
        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        typesRepository.save(entity);

        dto.setCreatedDate(entity.getCreated_Date());
        dto.setId(entity.getId());
        return dto;
    }

    public List<TypesDTO> list() {
        Iterable<TypesEntity> all = typesRepository.findAll();
        List<TypesDTO> list = new LinkedList<>();
        for (TypesEntity entity : all) {
            TypesDTO dto = new TypesDTO();
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

    public void update(TypesDTO dto, String key) {
       TypesEntity entity = get(key);
       entity.setKey(dto.getKey());
       entity.setNameUz(dto.getNameUz());
       entity.setNameRu(dto.getNameRu());
       entity.setNameEn(dto.getNameEn());
       typesRepository.save(entity);
    }

    public TypesEntity get(String key){
       return typesRepository.findByKey(key).orElseThrow(() -> {
            throw new ItemNotFoundException("This article_type not found");
        });
    }

    public void delete(String key) {
        Optional<TypesEntity> byKey = typesRepository.findByKey(key);
        if (byKey.isEmpty()){
            throw new BadRequestException("This article_type not found");
        }
        typesRepository.deleteByKey(key);
    }
}
