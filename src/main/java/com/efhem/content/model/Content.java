package com.efhem.content.model;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record Content (
        Integer id,
        @NotBlank(message = "Title can not be black")
        String title,
        String desc,
        ContentStatus status,
        ContentMediaType type,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String url
){

}