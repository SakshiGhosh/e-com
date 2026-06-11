package com.example.quiz.dto;

import lombok.Data;

@Data
public class CartRequestDTO {
    private Long productId; // which product to add
    private Integer quantity; // how many
}