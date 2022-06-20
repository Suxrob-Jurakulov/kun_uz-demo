package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.TypesDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.TypesEntity;
import com.company.enums.LangEnum;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        Optional<CategoryEntity> optional = categoryRepository.findByKey(dto.getKey());
        if (optional.isPresent()) {
            throw new BadRequestException("This region already exist");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        categoryRepository.save(entity);

        dto.setCreatedDate(entity.getCreated_Date());
        dto.setId(entity.getId());
        return dto;
    }

    public List<CategoryDTO> list() {
        Iterable<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryDTO> list = new LinkedList<>();
        for (CategoryEntity entity : all) {
            CategoryDTO dto = new CategoryDTO();
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

    public void update(CategoryDTO dto) {
        CategoryEntity entity = get(dto.getKey());
        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        categoryRepository.save(entity);
    }

    public CategoryEntity get(String key) {
        return categoryRepository.findByKey(key).orElseThrow(() -> {
            throw new ItemNotFoundException("This category not found");
        });
    }

    public void delete(String key) {
        Optional<CategoryEntity> byKey = categoryRepository.findByKey(key);
        if (byKey.isEmpty()) {
            throw new BadRequestException("This category not found");
        }
        categoryRepository.deleteByKey(key);
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Region not found");
        });
    }


    public List<CategoryDTO> getList(LangEnum lang) {
        Iterable<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryDTO> list = new LinkedList<>();
        for (CategoryEntity entity: all){
            CategoryDTO dto = new CategoryDTO();
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
}
