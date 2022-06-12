package com.company.service;

import com.company.dto.comment.CommentDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.exp.BadRequestException;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleService articleService;

    public CommentDTO create(CommentDTO dto, String id) {
        boolean existArticle = articleService.isExistArticle(id);

        if (!existArticle){
            throw new BadRequestException("Article not found");
        }

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setVisible(true);

        ProfileEntity profile = new ProfileEntity(dto.getProfileId());
        entity.setProfile(profile);

        ArticleEntity article = new ArticleEntity(id);
        entity.setArticle(article);
        commentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void update(Integer id, CommentDTO dto, Integer profileId) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()){
            throw new BadRequestException("Not found");
        }
        Integer pID = optional.get().getProfile().getId();
        if (!Objects.equals(pID, profileId)){
            throw new BadRequestException("Mazgi bu sening commenting emas");
        }

        CommentEntity entity = optional.get();
        entity.setContent(dto.getContent());
        entity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(entity);
    }
}
