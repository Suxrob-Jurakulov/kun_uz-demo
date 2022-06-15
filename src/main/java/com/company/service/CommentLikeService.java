package com.company.service;

import com.company.entity.CommentEntity;
import com.company.entity.CommentLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.exp.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentService commentService;

    public void like(int mark, Integer commentId, Integer profileId) {
        CommentEntity comment = commentService.get(commentId);
        ProfileEntity profile = new ProfileEntity(profileId);

        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentAndProfile(comment, profile);
        CommentLikeEntity entity;
        if (optional.isEmpty()) {
            entity = new CommentLikeEntity();
            entity.setComment(comment);
            entity.setProfile(profile);
            notExist(entity, mark);
        } else {
            entity = optional.get();
            entity.setComment(comment);
            entity.setProfile(profile);
            exist(entity, mark);
        }
        commentLikeRepository.save(entity);
    }

    private void notExist(CommentLikeEntity entity, int mark) {
        switch (mark) {
            case 1 -> entity.setStatus(true);
            case 0 -> entity.setStatus(null);
            case -1 -> entity.setStatus(false);
            default -> throw new BadRequestException(" 1 Like\n -1 Dislike\n 0 Cancel");

        }
    }

    private void exist(CommentLikeEntity entity, int mark) {
        if (entity.getStatus()) {
            switch (mark) {
                case 0 -> entity.setStatus(null);
                case -1 -> entity.setStatus(false);
                default -> throw new BadRequestException("Siz allaqachon like bosgansiz");

            }
        } else if (entity.getStatus() == null) {
            switch (mark) {
                case 1 -> entity.setStatus(true);
                case -1 -> entity.setStatus(false);
            }
        } else {
            switch (mark) {
                case 1 -> entity.setStatus(true);
                case 0 -> entity.setStatus(null);
                default -> throw new BadRequestException("Siz allaqachon dislike bosgansiz");
            }
        }
    }
}
