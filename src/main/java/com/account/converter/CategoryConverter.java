package com.account.converter;

import com.account.dto.CategoryDTO;
import com.account.service.CategoryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CategoryConverter implements Converter<String, CategoryDTO> {

    @Autowired
    @Lazy
    private CategoryService categoryService;

    @SneakyThrows
    @Override
    public CategoryDTO convert(String categoryName) {

        return categoryService.findCategory(categoryName);
    }
}
