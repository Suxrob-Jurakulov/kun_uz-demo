package com.company.service;

import com.company.entity.TagEntity;
import com.company.enums.TagStatus;
import com.company.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagEntity create(String name) {
        TagEntity tag = new TagEntity();
        tag.setName(name);
        tag.setStatus(TagStatus.ACTIVE);
        tagRepository.save(tag);
        return tag;
    }

    public TagEntity createIfNotExists(String tagName) {
//        Optional<TagEntity> tagOptional = tagRepository.findByName(tagName);
//        if (tagOptional.isEmpty()) {
//            return create(tagName);
//        }
//        return tagOptional.get();
          return tagRepository.findByName(tagName).orElse(create(tagName));
    }

    public boolean isExists(String name) {
        return tagRepository.existsByName(name);
    }

    public TagEntity getByName(String name) {
        return tagRepository.findByName(name).orElse(null);
    }
}
