package com.company.service;

import com.company.dto.TypesDTO;
import com.company.entity.TypesEntity;
import com.company.enums.LangEnum;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        if (optional.isPresent()) {
            throw new BadRequestException("This type already exist");
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
        if (dto.getKey() != null) {
            entity.setKey(dto.getKey());
        }
        if (dto.getNameUz() != null) {
            entity.setNameUz(dto.getNameUz());
        }
        if (dto.getNameRu() != null) {
            entity.setNameRu(dto.getNameRu());
        }
        if (dto.getNameEn() != null) {
            entity.setNameEn(dto.getNameEn());
        }
        typesRepository.save(entity);
    }

    public TypesEntity get(String key) {
        return typesRepository.findByKey(key).orElseThrow(() -> {
            throw new ItemNotFoundException("This type not found");
        });
    }

    public void delete(String key) {
        TypesEntity entity = get(key);
        typesRepository.deleteById(entity.getId());
    }

    public List<TypesDTO> getList(LangEnum lang) {
        Iterable<TypesEntity> all = typesRepository.findAll();
        List<TypesDTO> list = new LinkedList<>();
        for (TypesEntity entity : all) {
            TypesDTO dto = new TypesDTO();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            switch (lang) {
                case ru -> dto.setName(entity.getNameRu());
                case en -> dto.setName(entity.getNameEn());
                case uz -> dto.setName(entity.getNameUz());
            }
            list.add(dto);
        }
        return list;
    }

//    public List<TypesDTO> sortByName(){
//        Sort sort = Sort.by()
//
//        return null;
//    }

}
