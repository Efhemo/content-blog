package com.efhem.demo.entity;

import com.efhem.demo.model.ContentMediaType;
import com.efhem.demo.model.ContentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_content")
public class ContentEntity {


    @Id
    @SequenceGenerator(
            name = "sequence_content_generator",
            sequenceName = "sequence_content_generator"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_content_generator"
    )

    private Integer id;
    private String title;
    private String desc;
    @Enumerated(EnumType.STRING)
    private ContentStatus status;

    @Enumerated(EnumType.STRING)
    ContentMediaType type;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String url;

    public ContentEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

    public ContentMediaType getType() {
        return type;
    }

    public void setType(ContentMediaType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
