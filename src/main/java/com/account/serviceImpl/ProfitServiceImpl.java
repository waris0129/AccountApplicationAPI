package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.InvoiceDTO1;
import com.account.dto.ProductDTO;
import com.account.dto.ProfitDTO;
import com.account.dto.SalesInvoiceDTO;
import com.account.entity.Profit;
import com.account.enums.InvoiceStatus;
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
    private static ProfitDTO profitDTO;

    static {
        profitDTO = new ProfitDTO();
    }


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

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(salesInvoiceNumber);

        if(invoiceDTO1.getTotalQTY()==0)
            totalSoldQty =0;
        if(invoiceDTO1.getTotalPrice()==0)
            totalSales = 0;



        profitDTO.setSalesInvoiceNo(invoiceDTO1);
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
                //    ProductDTO deleteProduct = productService.deleteProduct(updateProduct.getInventoryNo());
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

        invoiceDTO1.setTotalPrice(totalSales);
        invoiceDTO1.setTotalQTY(totalSoldQty);
//        invoiceDTO1.setProductList(allProductList);

        invoice1Service.updateInvoice(salesInvoiceNumber,invoiceDTO1);

        return collectItems;
    }


    @Override
    public List<ProductDTO> updateInventoryByFIFO_SalesCancel(String salesInvoiceNumber) throws AccountingApplicationException {

        InvoiceDTO1  salesInvoiceDTO = invoice1Service.findInvoice(salesInvoiceNumber);

        List<String> inventoryList = salesInvoiceDTO.getProductList().stream().map(p->p.getInventoryNo()).sorted().collect(Collectors.toList());
        Integer totalSoldItem = salesInvoiceDTO.getTotalQTY();


        List<ProductDTO> collectItems = new ArrayList<>();

        for(String inventoryNo : inventoryList){

            ProductDTO foundProductItem = productService.findProductByInventoryNo(inventoryNo);

            Integer availStock = foundProductItem.getAvailableStock();
            Integer qty = foundProductItem.getQty();

            while (availStock!=qty){
                ++availStock;
                --totalSoldItem;
            }

            foundProductItem.setAvailableStock(availStock);

            ProductDTO updateProduct = productService.updateProduct(inventoryNo,foundProductItem);
            collectItems.add(updateProduct);
        }

        return collectItems;
    }


    @Override
    public ProfitDTO saveProfitTransaction(String invoiceNumber) throws AccountingApplicationException {
        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNumber);
        invoiceDTO1.setInvoiceStatus(InvoiceStatus.COMPLETE);
        InvoiceDTO1 updateInvoice= invoice1Service.updateInvoice(invoiceNumber,invoiceDTO1);

        finalProfit = totalSales-totalCost;

        profitDTO.setSoldQty(totalSoldQty);
        profitDTO.setTotalCost(BigDecimal.valueOf(totalCost));
        profitDTO.setTotalSales(BigDecimal.valueOf(totalSales));
        profitDTO.setSalesInvoiceNo(updateInvoice);
        profitDTO.setProfit(BigDecimal.valueOf(finalProfit));

        Profit profit = mapperUtility.convert(profitDTO,new Profit());

        Profit savedProfit = profitRepository.save(profit);

        return mapperUtility.convert(savedProfit, new ProfitDTO());
    }

    @Override
    public List<ProfitDTO> getAllProfit(Integer companyId){

        List<Profit> profits = profitRepository.getAllProfitByCompanyId(companyId);

        List<ProfitDTO> profitDTOList = profits.stream().map(p->mapperUtility.convert(p,new ProfitDTO())).collect(Collectors.toList());

        return profitDTOList;
    }


    @Override
    public ProfitDTO findProfitByInvoiceId(String salesInvoiceNumber){

        Profit profit = profitRepository.findProfitByInvoiceId(Integer.parseInt(salesInvoiceNumber));

        ProfitDTO profitDTO = mapperUtility.convert(profit,new ProfitDTO());

        return profitDTO;
    }

}
