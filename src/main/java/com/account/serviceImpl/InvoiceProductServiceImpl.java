package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.*;
import com.account.entity.AddSingleProduct;
import com.account.entity.InvoiceProduct;
import com.account.entity.Product;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.AddSingleProductRepository;
import com.account.repository.InvoiceProductRepository;
import com.account.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private ProductService productService;
    @Autowired
    private AddSingleProductRepository addSingleProductRepository;


    @Override
    public InvoiceProductDTO createNewInvoiceProductTemplate(String vendorName, String invoiceType) throws UserNotFoundInSystem {

        InvoiceDTO invoiceDTO = invoiceService.createNewInvoiceTemplate(vendorName,invoiceType);

        InvoiceProductDTO invoiceProductDTO = new InvoiceProductDTO();

        invoiceProductDTO.setInvoice(invoiceDTO);

        InvoiceProduct invoiceProduct = invoiceProductRepository.save(mapperUtility.convert(invoiceDTO,new InvoiceProduct()));

        return mapperUtility.convert(invoiceProduct,new InvoiceProductDTO());
    }

    @Override
    public InvoiceProductDTO addProductItem(String invoiceNumber, String inventoryNo, BigDecimal price, Integer qty) throws AccountingApplicationException {

        Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findByInvoiceNumber(invoiceNumber);
        if(!foundInvoiceProduct.isPresent())
            throw new AccountingApplicationException("Invoice Product not found in system");
        InvoiceProduct invoiceProduct = foundInvoiceProduct.get();

        ProductDTO productDTO = productService.findProductByInventoryNo(inventoryNo);

        AddSingleProductDTO addItemDTO = new AddSingleProductDTO();

        addItemDTO.setProduct(productDTO);
        addItemDTO.setPrice(price);
        addItemDTO.setQty(qty);

        AddSingleProduct addItem = mapperUtility.convert(addItemDTO,new AddSingleProduct());

        addItem.setTransactionType(invoiceProduct.getInvoice().getInvoiceType().getValue());

        AddSingleProduct saveAddItem = addSingleProductRepository.save(addItem);

        invoiceProduct.getProductList().add(saveAddItem);


        InvoiceProduct saveInvoiceProduct = invoiceProductRepository.save(invoiceProduct);


        InvoiceProductDTO savedInvoiceProductDTO = mapperUtility.convert(saveInvoiceProduct,new InvoiceProductDTO());

        return savedInvoiceProductDTO;
    }



    @Override
    public InvoiceProductDTO getInvoice(String invoiceNumber) {


        return null;
    }









}
