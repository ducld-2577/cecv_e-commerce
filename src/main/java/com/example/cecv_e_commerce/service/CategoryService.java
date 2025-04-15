package com.example.cecv_e_commerce.service;

import com.example.cecv_e_commerce.domain.dto.category.CategoryDTO;
import com.example.cecv_e_commerce.domain.dto.category.CategoryListResponseDTO;
import com.example.cecv_e_commerce.domain.model.Category;
import com.example.cecv_e_commerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryListResponseDTO getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(category -> convertToDTO(category)).collect(Collectors.toList());

        return new CategoryListResponseDTO(categoryDTOList, categoryDTOList.size());
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }
}
