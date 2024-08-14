package com.rev_connect_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PostCreateRequest {

    private LocalDateTime createdAt;
    @NotEmpty(message = "text is blank")
    private String text;

    public PostCreateRequest() {}

    public PostCreateRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
