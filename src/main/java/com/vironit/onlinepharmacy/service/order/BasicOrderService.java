package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.dto.OrderCreateData;
import com.vironit.onlinepharmacy.dto.OrderUpdateData;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.OrderStatus;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.OrderServiceException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BasicOrderService implements OrderService {

    private final OrderDao orderDao;
    private final OrderPositionService orderPositionService;
    private final StockService stockService;
    private final UserService userService;
    private final ProductService productService;

    public BasicOrderService(OrderDao orderDao, OrderPositionService orderPositionService, StockService stockService, UserService userService, ProductService productService) {
        this.orderDao = orderDao;
        this.orderPositionService = orderPositionService;
        this.stockService = stockService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public long add(OrderCreateData orderCreateData) {
        User owner = userService.get(orderCreateData.getOwnerId());
        Order order = new Order(-1, Instant.now(), owner, OrderStatus.PREPARATION);
        long id=orderDao.add(order);
                order.setId(id);
        List<OrderPosition> orderPositions = orderCreateData.getPositionDataList()
                .stream()
                .map(positionData -> new OrderPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()), order))
                .collect(Collectors.toList());
        orderPositionService.addAll(orderPositions);
        return id;
    }

    @Override
    public void payForOrder(long id) {
        Order order = orderDao.get(id)
                .orElseThrow(() -> new OrderServiceException("Can't pay for order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.PAID);
        orderDao.update(order);
    }

    @Override
    public void confirmOrder(long id) {
        Order order = orderDao.get(id)
                .orElseThrow(() -> new OrderServiceException("Can't confirm order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.IN_PROGRESS);
        Collection<OrderPosition> positions = orderPositionService.getAllByOwnerId(id);
        stockService.reserveInStock(positions);
        orderDao.update(order);
    }

    @Override
    public void completeOrder(long id) {
        Order order = orderDao.get(id)
                .orElseThrow(() -> new OrderServiceException("Can't complete order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.COMPLETE);
        orderDao.update(order);
        Collection<OrderPosition> orderPositions = orderPositionService.getAllByOwnerId(id);
        stockService.takeFromStock(orderPositions);
    }

    @Override
    public void cancelOrder(long id) {
        Order order = orderDao.get(id)
                .orElseThrow(() -> new OrderServiceException("Can't cancel order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.CANCELED);
        orderDao.update(order);
        Collection<OrderPosition> orderPositions = orderPositionService.getAllByOwnerId(id);
        stockService.annulReservationInStock(orderPositions);
    }

    @Override
    public Order get(long id) {
        return orderDao.get(id)
                .orElseThrow(() -> new OrderServiceException("Can't get order. Order with id " + id + " not found."));
    }

    @Override
    public Collection<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public void update(OrderUpdateData orderUpdateData) {
        User owner = userService.get(orderUpdateData.getOwnerId());
        Order order = get(orderUpdateData.getId());
        order.setOwner(owner);
        List<OrderPosition> operationPositions = orderUpdateData.getPositionDataList()
                .stream()
                .map(positionData -> new OrderPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()), order))
                .collect(Collectors.toList());
        orderPositionService.removeAllByOwnerId(orderUpdateData.getOwnerId());
        orderPositionService.addAll(operationPositions);
    }

    @Override
    public Collection<Order> getAllByOwnerId(long id) {
        return orderDao.getAllByOwnerId(id);
    }

    @Override
    public void remove(long id) {
        orderDao.remove(id);
        orderPositionService.removeAllByOwnerId(id);
    }
}
