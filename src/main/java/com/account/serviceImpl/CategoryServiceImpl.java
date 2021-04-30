package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.CategoryDTO;
import com.account.dto.VendorDTO;
import com.account.entity.Category;
import com.account.entity.Vendor;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.repository.CategoryRepository;
import com.account.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MapperUtility mapperUtility;


    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) throws AccountingApplicationException {

        categoryDTO.setCategory(categoryDTO.getCategory().toUpperCase());
        categoryDTO.setEnabled(true);

        Optional<Category> foundCategory = categoryRepository.findByCategory(categoryDTO.getCategory());

        if(foundCategory.isPresent())
            throw new AccountingApplicationException("Category is already existed in system");

        Category category = mapperUtility.convert(categoryDTO,new Category());
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO dto = mapperUtility.convert(savedCategory,new CategoryDTO());

        return dto;
    }

    @Override
    public CategoryDTO findCategory(String category){

        category = category.toUpperCase();

        CategoryDTO dto = new CategoryDTO();

        Optional<Category> category1 = categoryRepository.findByCategory(category);

        if(!category1.isPresent()){
            try {
                throw new AccountingApplicationException("Category not found in system");
            } catch (AccountingApplicationException e) {
                e.printStackTrace();
            }
        }else {
            dto = mapperUtility.convert(category1.get(), new CategoryDTO());
        }

        return dto;
    }

    @Override
    public CategoryDTO deleteCategory(String category) throws AccountingApplicationException {

        CategoryDTO foundDto = findCategory(category);


        foundDto.setEnabled(false);

        Category foundCategory = mapperUtility.convert(foundDto,new Category());

        Category deleteCategory = categoryRepository.save(foundCategory);

        CategoryDTO deletedDTO = mapperUtility.convert(deleteCategory, new CategoryDTO());

        return deletedDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        List<Category> categoryList = categoryRepository.findAll();

        List<CategoryDTO> categoryDTOList = categoryList.stream().map(entity-> mapperUtility.convert(entity,new CategoryDTO())).collect(Collectors.toList());

        return categoryDTOList;

    }

    @Override
    public List<CategoryDTO> getAllCategoriesByCompany(Integer companyID) {

        List<Category> categoryList = categoryRepository.getAllCategoriesByCompany(companyID);

        List<CategoryDTO> categoryDTOList = categoryList.stream().map(entity-> mapperUtility.convert(entity,new CategoryDTO())).collect(Collectors.toList());

        return categoryDTOList;
    }
}
