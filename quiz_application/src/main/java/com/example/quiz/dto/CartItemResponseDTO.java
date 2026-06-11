package com.example.quiz.dto;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double totalPrice;
}