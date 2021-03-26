package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.CategoryDTO;
import com.account.dto.ProductNameDTO;
import com.account.entity.Category;
import com.account.entity.ProductName;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.repository.CategoryRepository;
import com.account.repository.ProductNameRepository;
import com.account.service.CategoryService;
import com.account.service.ProductNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductNameServiceImpl implements ProductNameService {

    @Autowired
    private ProductNameRepository productNameRepository;
    @Autowired
    private MapperUtility mapperUtility;


    @Override
    public ProductNameDTO saveProductNameDTO(ProductNameDTO productNameDTO) throws AccountingApplicationException {

        productNameDTO.setProductName(productNameDTO.getProductName().toUpperCase());
        productNameDTO.setEnabled(true);

        Optional<ProductName> foundProductName = productNameRepository.findByProductName(productNameDTO.getProductName().toUpperCase());

        if(foundProductName.isPresent())
            throw new AccountingApplicationException("Product is already registered in system");

        ProductName productName = mapperUtility.convert(productNameDTO,new ProductName());
        ProductName savedProductName = productNameRepository.save(productName);
        ProductNameDTO dto = mapperUtility.convert(savedProductName,new ProductNameDTO());

        return dto;
    }

    @Override
    public ProductNameDTO findProductNameDTO(String productName) throws AccountingApplicationException {
        productName = productName.toUpperCase();

        Optional<ProductName> foundProductName = productNameRepository.findByProductName(productName);

        if(!foundProductName.isPresent())
            throw new AccountingApplicationException("the Product is not registered yet in system");

        ProductNameDTO dto = mapperUtility.convert(foundProductName.get(), new ProductNameDTO());

        return dto;
    }

    @Override
    public ProductNameDTO deleteProductNameDTO(String productName) throws AccountingApplicationException {
        productName = productName.toUpperCase();

        Optional<ProductName> foundProductName = productNameRepository.findByProductName(productName);

        if(!foundProductName.isPresent())
            throw new AccountingApplicationException("the Product is not registered yet in system");
        ProductNameDTO foundDto = mapperUtility.convert(foundProductName.get(), new ProductNameDTO());

        foundDto.setEnabled(false);

        ProductName updateProductName = mapperUtility.convert(foundDto,new ProductName());

        ProductName deleteProductName = productNameRepository.save(updateProductName);

        ProductNameDTO deletedDTO = mapperUtility.convert(deleteProductName, new ProductNameDTO());

        return deletedDTO;
    }

    @Override
    public List<ProductNameDTO> getAllProductNameDTOList() {

        List<ProductName> productNameList = productNameRepository.findAll();

        List<ProductNameDTO> productNameDTOList = productNameList.stream().map(entity-> mapperUtility.convert(entity,new ProductNameDTO())).collect(Collectors.toList());

        return productNameDTOList;
    }
}
