package com.company.mapper;

import java.time.LocalDateTime;

public interface ArticleShortInfo {
    String getId();
    String getTitle();
    String getDescription();
    LocalDateTime getPublishDate();
}
