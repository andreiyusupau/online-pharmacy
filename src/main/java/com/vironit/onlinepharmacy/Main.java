package com.vironit.onlinepharmacy;

import com.vironit.onlinepharmacy.dao.*;
import com.vironit.onlinepharmacy.dao.collection.*;
import com.vironit.onlinepharmacy.dto.ProcurementData;
import com.vironit.onlinepharmacy.dto.ProcurementPositionData;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.user.BasicUserService;
import com.vironit.onlinepharmacy.service.user.UserService;
import com.vironit.onlinepharmacy.service.procurement.BasicProcurementService;
import com.vironit.onlinepharmacy.service.procurement.ProcurementService;
import com.vironit.onlinepharmacy.service.product.BasicProductService;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.BasicStockService;
import com.vironit.onlinepharmacy.service.stock.StockService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//Test
        //Create products
        ProductDAO productDAO=new CollectionBasedProductDAO();
        ProductService productService=new BasicProductService(productDAO);
        ProductCategory productCategory= new ProductCategory(1,"Medicine");
        productService.add(new Product(-1,"Coal",new BigDecimal("2.2"), productCategory));
        productService.add(new Product(-1,"Azithromicin",new BigDecimal("22.2"), productCategory));
        productService.add(new Product(-1,"Aspirine",new BigDecimal("12.5"), productCategory));
        productService.add(new Product(-1,"Insuline",new BigDecimal("52.2"), productCategory));
//Create stock
        StockDAO stockDAO=new CollectionBasedStockDAO();
        StockService stockService=new BasicStockService(stockDAO);

        //create users
        UserDAO userDAO = new CollectionBasedUserDAO();
        UserService userService=new BasicUserService(userDAO);
        User supplier = new User(2, "supplier", "name", "name", LocalDate.of(2000, 12, 12), "email2", Role.PROCUREMENT_SPECIALIST);
        userService.add(supplier);
//Create procurement
          ProcurementDAO procurementDAO = new CollectionBasedProcurementDAO();
        OperationPositionDAO operationPositionDAOp=new CollectionBasedOperationPositionDAO();
        ProcurementService procurementService=new BasicProcurementService(procurementDAO, operationPositionDAOp, stockService, productService, userService);
        ProcurementPositionData procurementPositionData=new ProcurementPositionData(1,5);
        ProcurementPositionData procurementPositionData2=new ProcurementPositionData(2,10);
        ProcurementPositionData procurementPositionData3=new ProcurementPositionData(3,15);
        List<ProcurementPositionData> procurementPositionDataList=new ArrayList<>();
        procurementPositionDataList.add(procurementPositionData);
        procurementPositionDataList.add(procurementPositionData2);
        procurementPositionDataList.add(procurementPositionData3);
        ProcurementData procurementData=new ProcurementData(0,procurementPositionDataList);
        long procurementId=  procurementService.add(procurementData);
        procurementService.approveProcurement(procurementId);
        procurementService.completeProcurement(procurementId);

        System.out.println(procurementService.getAll().toString());
        System.out.println(stockService.getAll().toString());
        //Create orders
//        User customer = new User(1, "customer", "name", "name", LocalDate.of(2000, 12, 12), "email", Role.CONSUMER);
//        OrderDAO orderDAO = new CollectionBasedOrderDAO();
//        OrderService orderService = new BasicOrderService(orderDAO, stockService);
//        long orderId = orderService.createOrder(customer);
//        orderService.payForOrder(orderId);
//        orderService.confirmOrder(orderId);
//        orderService.completeOrder(orderId);
//
//        long orderId2 = orderService.createOrder(customer);
//        orderService.payForOrder(orderId2);
//        orderService.confirmOrder(orderId2);
//        orderService.completeOrder(orderId2);
//        System.out.println(orderService.getOrdersByUserId(1).toString());
    }
}
