package com.vironit.onlinepharmacy;

import com.vironit.onlinepharmacy.dao.CollectionBasedOrderDAO;
import com.vironit.onlinepharmacy.dao.CollectionBasedProcurementDAO;
import com.vironit.onlinepharmacy.dao.OrderDAO;
import com.vironit.onlinepharmacy.dao.ProcurementDAO;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.order.BasicOrderService;
import com.vironit.onlinepharmacy.service.procurement.BasicProcurementService;
import com.vironit.onlinepharmacy.service.order.OrderService;
import com.vironit.onlinepharmacy.service.procurement.ProcurementService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        User supplier = new User(2, "supplier", "name", "name", LocalDate.of(2000, 12, 12), "email2", Role.PROCUREMENT_SPECIALIST);
        ProcurementDAO procurementDAO = new CollectionBasedProcurementDAO();
        ProcurementService procurementService=new BasicProcurementService(procurementDAO, stockService);
        long procurementId= procurementService.createProcurement(supplier);
        procurementService.approveProcurement(procurementId);
        procurementService.completeProcurement(procurementId);

        User customer = new User(1, "customer", "name", "name", LocalDate.of(2000, 12, 12), "email", Role.CONSUMER);

        OrderDAO orderDAO = new CollectionBasedOrderDAO();
        OrderService orderService = new BasicOrderService(orderDAO, stockService);
        long orderId = orderService.createOrder(customer);
        orderService.payForOrder(orderId);
        orderService.confirmOrder(orderId);
        orderService.completeOrder(orderId);

        long orderId2 = orderService.createOrder(customer);
        orderService.payForOrder(orderId2);
        orderService.confirmOrder(orderId2);
        orderService.completeOrder(orderId2);
        System.out.println(orderService.getOrdersByUserId(1).toString());
    }
}
