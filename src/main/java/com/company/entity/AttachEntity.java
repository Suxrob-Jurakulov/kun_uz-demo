package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;
    @Column(name = "origin_name")
    private String originName;
    @Column
    private String extension;
    @Column
    private long size;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column
    private String path;

    public AttachEntity() {
    }

    public AttachEntity(String id) {
        this.id = id;
    }
}
