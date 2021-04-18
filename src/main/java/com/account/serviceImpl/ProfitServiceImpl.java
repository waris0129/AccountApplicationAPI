package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.InvoiceDTO1;
import com.account.dto.ProductDTO;
import com.account.dto.ProfitDTO;
import com.account.entity.Product;
import com.account.entity.ProductName;
import com.account.entity.Profit;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.ProfitRepository;
import com.account.service.Invoice1Service;
import com.account.service.ProductNameService;
import com.account.service.ProductService;
import com.account.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfitServiceImpl implements ProfitService {

    @Autowired
    private ProductService productService;
    @Autowired
    private Invoice1Service invoice1Service;
    @Autowired
    private ProductNameService productNameService;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private ProfitRepository profitRepository;


    private static int totalCost;
    private static int totalSales;
    private static int finalProfit;
    private static int totalSoldQty;
    private String invoiceNumber;
    private ProfitDTO profitDTO= null;


    @Override
    public String createInvoiceNumber(String invoiceType) throws CompanyNotFoundException {
        return invoice1Service.createInvoiceNumber(invoiceType);
    }

    @Override
    public InvoiceDTO1 createNewInvoiceTemplate(String vendorName, String invoiceType) throws UserNotFoundInSystem, AccountingApplicationException, CompanyNotFoundException {
        profitDTO = new ProfitDTO();

        InvoiceDTO1 salesInvoice = invoice1Service.createNewInvoiceTemplate(vendorName, invoiceType);

        return salesInvoice;
    }



    @Override
    public List<ProductDTO> updateInventoryByFIFO(String salesInvoiceNumber, String productName, Integer totalSoldItem, Integer salesPrice) throws AccountingApplicationException {
        profitDTO.setSalesInvoiceNo(invoice1Service.findInvoice(salesInvoiceNumber));
        totalSales += totalSoldItem * salesPrice;
        totalSoldQty +=totalSoldItem;

        if(!profitDTO.getProductName().stream().filter(p->p.getProductName().equals(productName)).findAny().isPresent())
            profitDTO.getProductName().add(productNameService.findProductNameDTO(productName));

        List<ProductDTO> collectItems = new ArrayList<>();

        List<ProductDTO> allProductList = productService.findProductByName(productName);

        Integer totalAvailableStock = allProductList.stream().filter(p->p.getEnabled().equals(true)).mapToInt(p->p.getAvailableStock()).sum();

        if(totalSoldItem>totalAvailableStock)
            throw new AccountingApplicationException("Not enough stock in Inventory, Total Available Stock="+totalAvailableStock+", Total Sold Item="+totalSoldItem);

            for (ProductDTO productDTO:allProductList) {

                Integer availableStock = productDTO.getAvailableStock();
                String inventoryNo = productDTO.getInventoryNo();

                if(totalSoldItem >= availableStock){
                    totalCost += productDTO.getPrice()*availableStock;
                    totalSoldItem = totalSoldItem - availableStock;
                    productDTO.setAvailableStock(0);
                    ProductDTO updateProduct = productService.updateProduct(inventoryNo,productDTO);
                    ProductDTO deleteProduct = productService.deleteProduct(updateProduct.getInventoryNo());
                    collectItems.add(productDTO);
                }
                else if(availableStock>totalSoldItem){
                    availableStock = availableStock - totalSoldItem;
                    totalCost += productDTO.getPrice()*totalSoldItem;
                    productDTO.setAvailableStock(availableStock);
                    totalSoldItem = 0;
                    ProductDTO updateProduct = productService.updateProduct(inventoryNo,productDTO);
                    collectItems.add(productDTO);
                }
            }


        return collectItems;
    }


    @Override
    public ProfitDTO saveProfitTransaction(String invoiceNumber) throws AccountingApplicationException {
        finalProfit = totalSales-totalCost;

        profitDTO.setSoldQty(totalSoldQty);
        profitDTO.setTotalCost(BigDecimal.valueOf(totalCost));
        profitDTO.setTotalSales(BigDecimal.valueOf(totalSales));
        profitDTO.setSalesInvoiceNo(invoice1Service.findInvoice(invoiceNumber));
        profitDTO.setProfit(BigDecimal.valueOf(finalProfit));

        Profit profit = mapperUtility.convert(profitDTO,new Profit());

        Profit savedProfit = profitRepository.save(profit);

        return mapperUtility.convert(savedProfit, new ProfitDTO());
    }
}
