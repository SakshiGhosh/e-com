package com.example.quiz.service;

import com.example.quiz.dto.ProductRequestDTO;
import com.example.quiz.dto.ProductResponseDTO;
import com.example.quiz.entity.Product;
import com.example.quiz.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // ADD product
    public ProductResponseDTO addProduct(ProductRequestDTO request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(request.getCategory());

        product = productRepository.save(product);
        return mapToDTO(product);
    }

    /*
     * // GET all products
     * public List<ProductResponseDTO> getAllProducts() {
     * return productRepository.findAll()
     * .stream()
     * .map(this::mapToDTO)
     * .collect(Collectors.toList());
     * }
     */

    // GET all products with pagination
    
    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    // GET product by id
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDTO(product);
    }

    // UPDATE product
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(request.getCategory());

        product = productRepository.save(product);
        return mapToDTO(product);
    }

    // DELETE product
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.deleteById(id);
    }

    // Entity → DTO helper
    private ProductResponseDTO mapToDTO(Product product) {
        ProductResponseDTO res = new ProductResponseDTO();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setPrice(product.getPrice());
        res.setStock(product.getStock());
        res.setImageUrl(product.getImageUrl());
        res.setCategory(product.getCategory());
        return res;
    }
}
