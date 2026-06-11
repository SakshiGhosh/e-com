package com.example.quiz.dto;

import lombok.Data;
import java.util.List;

@Data
public class CartResponseDTO {
    private Long id;
    private List<CartItemResponseDTO> items;
    private Double grandTotal;
}