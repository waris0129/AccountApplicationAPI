package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.ProductDTO;
import com.account.entity.Category;
import com.account.entity.Company;
import com.account.entity.Product;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.repository.CompanyRepository;
import com.account.repository.ProductRepository;
import com.account.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CompanyRepository companyRepository;

    private static Integer number = 0;

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) throws AccountingApplicationException {

        String companyTitle = companyRepository.findById(1).get().getTitle();


        productDTO.setName(productDTO.getName().toUpperCase());
        productDTO.setEnabled(true);
        productDTO.setInventoryNo(companyTitle.toUpperCase().substring(0,3).trim()+"_"+productDTO.getName().toUpperCase()+"_00"+ ++number);

        Product product = mapperUtility.convert(productDTO, new Product());
        Product savedProduct = productRepository.save(product);
        ProductDTO newProductDTO = mapperUtility.convert(savedProduct,new ProductDTO());

        return newProductDTO;
    }

    @Override
    public ProductDTO findProductByName(String name) throws AccountingApplicationException {

        Optional<Product> foundProduct = productRepository.findByName(name.toUpperCase());

        if(!foundProduct.isPresent())
            throw new AccountingApplicationException("Product not found in system");

        ProductDTO productDTO = mapperUtility.convert(foundProduct.get(),new ProductDTO());

        return productDTO;
    }


    @Override
    public ProductDTO findProductByInventoryNo(String inventoryNo) throws AccountingApplicationException {
        Optional<Product> foundProduct = productRepository.findByInventoryNo(inventoryNo.toUpperCase());

        if(!foundProduct.isPresent())
            throw new AccountingApplicationException("Product not found in system");

        ProductDTO productDTO = mapperUtility.convert(foundProduct.get(),new ProductDTO());

        return productDTO;
    }

    @Override
    public ProductDTO updateProduct(String name, ProductDTO productDTO) throws AccountingApplicationException {

        name = name.toUpperCase();

        Optional<Product> foundProduct = productRepository.findByName(name);

        if(!foundProduct.isPresent())
            throw new AccountingApplicationException("Product not found in system");

        Product product = foundProduct.get();

        Integer id = product.getId();
        Category category = product.getCategory();
        Company company = product.getCompany();
        Boolean enabled = product.getEnabled();

        Product updatedProduct = mapperUtility.convert(productDTO,new Product());

        updatedProduct.setName(name);
        updatedProduct.setId(id);
        updatedProduct.setCategory(category);
        updatedProduct.setCompany(company);
        updatedProduct.setEnabled(enabled);

        Product savedProduct = productRepository.save(updatedProduct);

        ProductDTO savedProductDTO = mapperUtility.convert(savedProduct,new ProductDTO());

        return savedProductDTO;
    }

    @Override
    public ProductDTO deleteProduct(String name) throws AccountingApplicationException {
        name = name.toUpperCase();

        Optional<Product> foundProduct = productRepository.findByName(name);

        if(!foundProduct.isPresent())
            throw new AccountingApplicationException("Product not found in system");

        Product product = foundProduct.get();

        product.setEnabled(false);

        Product deleteProduct = productRepository.save(product);

        ProductDTO deleteDTO = mapperUtility.convert(deleteProduct, new ProductDTO());

        return deleteDTO;
    }

    @Override
    public List<ProductDTO> findAllProduct() {

        List<Product> productList = productRepository.findAll();

        List<ProductDTO> productDTOList = productList.stream().map(p->mapperUtility.convert(p,new ProductDTO())).collect(Collectors.toList());

        return productDTOList;
    }
}
