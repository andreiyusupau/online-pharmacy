package com.vironit.onlinepharmacy;

import com.vironit.onlinepharmacy.dao.*;
import com.vironit.onlinepharmacy.dao.collection.*;
import com.vironit.onlinepharmacy.dto.OperationPositionData;
import com.vironit.onlinepharmacy.dto.OrderCreateData;
import com.vironit.onlinepharmacy.dto.ProcurementCreateData;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.order.BasicOrderService;
import com.vironit.onlinepharmacy.service.order.OrderService;
import com.vironit.onlinepharmacy.service.procurement.BasicProcurementService;
import com.vironit.onlinepharmacy.service.procurement.ProcurementService;
import com.vironit.onlinepharmacy.service.product.BasicProductService;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.BasicStockService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.BasicUserService;
import com.vironit.onlinepharmacy.service.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //TODO:put into tests
//Test
        //Create products
        IdGenerator productDaoIdGenerator = new BasicIdGenerator();
        ProductDao productDAO = new CollectionBasedProductDao(productDaoIdGenerator);
        ProductService productService = new BasicProductService(productDAO);
        ProductCategory productCategory = new ProductCategory(1, "Medicine");
        productService.add(new Product(-1, "Coal", new BigDecimal("2.2"), productCategory));
        productService.add(new Product(-1, "Azithromicin", new BigDecimal("22.2"), productCategory));
        productService.add(new Product(-1, "Aspirine", new BigDecimal("12.5"), productCategory));
        productService.add(new Product(-1, "Insuline", new BigDecimal("52.2"), productCategory));
//Create stock
        IdGenerator stockDaoIdGenerator = new BasicIdGenerator();
        StockDao stockDAO = new CollectionBasedStockDao(stockDaoIdGenerator);
        ReserveDao reserveDao = new CollectionBasedReserveDao();

        StockService stockService = new BasicStockService(stockDAO, reserveDao);

        //create users
        IdGenerator userDaoIdGenerator = new BasicIdGenerator();
        UserDao userDAO = new CollectionBasedUserDao(userDaoIdGenerator);
        UserService userService = new BasicUserService(userDAO);
        User supplier = new User(-1, "supplier", "name", "name", LocalDate.of(2000, 12, 12), "email2", "password", Role.PROCUREMENT_SPECIALIST);
        userService.add(supplier);
//Create procurement
        IdGenerator procurementDaoIdGenerator = new BasicIdGenerator();
        ProcurementDao procurementDAO = new CollectionBasedProcurementDao(procurementDaoIdGenerator);
        IdGenerator operationPositionDaoIdGenerator = new BasicIdGenerator();
        OperationPositionDao operationPositionDaoProcurement = new CollectionBasedOperationPositionDao(operationPositionDaoIdGenerator);
        ProcurementService procurementService = new BasicProcurementService(procurementDAO, operationPositionDaoProcurement, stockService, productService, userService);
        OperationPositionData operationPositionData = new OperationPositionData(1, 5);
        OperationPositionData operationPositionData2 = new OperationPositionData(2, 10);
        OperationPositionData operationPositionData3 = new OperationPositionData(3, 15);
        List<OperationPositionData> operationPositionDataList = new ArrayList<>();
        operationPositionDataList.add(operationPositionData);
        operationPositionDataList.add(operationPositionData2);
        operationPositionDataList.add(operationPositionData3);
        ProcurementCreateData procurementCreateData = new ProcurementCreateData(1, operationPositionDataList);
        long procurementId = procurementService.add(procurementCreateData);
        procurementService.approveProcurement(procurementId);
        procurementService.completeProcurement(procurementId);

        System.out.println(procurementService.getAll().toString());
        System.out.println(stockService.getAll().toString());
        //Create orders
        User customer = new User(1, "customer", "name", "name", LocalDate.of(2000, 12, 12), "email", "password", Role.CONSUMER);
        userService.add(customer);
        IdGenerator orderDaoIdGenerator = new BasicIdGenerator();
        OrderDao orderDao = new CollectionBasedOrderDao(orderDaoIdGenerator);
        IdGenerator operationPositionDaoIdGeneratorOrder = new BasicIdGenerator();
        OperationPositionDao operationPositionDaoOrder = new CollectionBasedOperationPositionDao(operationPositionDaoIdGeneratorOrder);
        OrderService orderService = new BasicOrderService(orderDao, operationPositionDaoOrder, stockService, userService, productService);


        OperationPositionData operationPositionDataOrder1 = new OperationPositionData(1, 3);
        OperationPositionData operationPositionDataOrder2 = new OperationPositionData(2, 2);
        OperationPositionData operationPositionDataOrder3 = new OperationPositionData(3, 5);
        List<OperationPositionData> operationPositionDataOrder = new ArrayList<>();
        operationPositionDataOrder.add(operationPositionDataOrder1);
        operationPositionDataOrder.add(operationPositionDataOrder2);
        operationPositionDataOrder.add(operationPositionDataOrder3);

        OrderCreateData orderCreateData = new OrderCreateData(2, operationPositionDataOrder);

        long orderId = orderService.add(orderCreateData);
        orderService.payForOrder(orderId);
        orderService.confirmOrder(orderId);
        orderService.completeOrder(orderId);

        System.out.println(orderService.getAll().toString());
        System.out.println(stockService.getAll().toString());
    }
}
