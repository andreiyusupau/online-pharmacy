package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dao.OperationPositionDao;
import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.dto.OrderCreateData;
import com.vironit.onlinepharmacy.dto.OrderUpdateData;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.OrderStatus;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.order.exception.OrderException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BasicOrderService implements OrderService {

    private final OrderDao orderDao;
    private final OperationPositionDao operationPositionDao;
    private final StockService stockService;
    private final UserService userService;
    private final ProductService productService;


    public BasicOrderService(OrderDao orderDao, OperationPositionDao operationPositionDao, StockService stockService, UserService userService, ProductService productService) {
        this.orderDao = orderDao;
        this.operationPositionDao = operationPositionDao;
        this.stockService = stockService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public long add(OrderCreateData orderCreateData) {
        User owner = userService.get(orderCreateData.getOwnerId());
        Order order = new Order(-1, Instant.now(), owner, OrderStatus.PREPARATION);
        List<OperationPosition> operationPositions = orderCreateData.getOperationPositionDataList()
                .stream()
                .map(positionData -> new OperationPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()), order))
                .collect(Collectors.toList());
        operationPositionDao.addAll(operationPositions);
        return orderDao.add(order);
    }

    @Override
    public void payForOrder(long id) throws OrderException {
        Order order = orderDao.get(id)
                .orElseThrow(() -> new OrderException("Can't pay for order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.PAID);
        orderDao.update(order);
    }

    @Override
    public void confirmOrder(long id) throws OrderException {
        Order order = orderDao.get(id)
                .orElseThrow(() -> new OrderException("Can't confirm order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.IN_PROGRESS);
        Collection<OperationPosition> positions = operationPositionDao.getAllByOwnerId(id);
        stockService.reserve(positions);
        orderDao.update(order);
    }

    @Override
    public void completeOrder(long id) throws OrderException {
        Order order = orderDao.get(id)
                .orElseThrow(() -> new OrderException("Can't complete order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.COMPLETE);
        stockService.take(id);
        orderDao.update(order);
    }

    @Override
    public void cancelOrder(long id) throws OrderException {
        Order order = orderDao.get(id)
                .orElseThrow(() -> new OrderException("Can't cancel order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.CANCELED);
        stockService.annul(id);
        orderDao.update(order);
    }

    @Override
    public Order get(long id) {
        return orderDao.get(id)
                .orElseThrow(() -> new OrderException("Can't get order. Order with id " + id + " not found."));
    }

    @Override
    public Collection<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public void update(OrderUpdateData orderUpdateData) {
        Order order = get(orderUpdateData.getId());
        List<OperationPosition> operationPositions = orderUpdateData.getOperationPositionDataList()
                .stream()
                .map(positionData -> new OperationPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()), order))
                .collect(Collectors.toList());
        operationPositionDao.removeAllByOwnerId(orderUpdateData.getOwnerId());
        operationPositionDao.addAll(operationPositions);
    }

    @Override
    public Collection<Order> getOrdersByUserId(long id) {
        return orderDao.getAllByOwnerId(id);
    }

    @Override
    public void remove(long id) {
        orderDao.remove(id);
        operationPositionDao.removeAllByOwnerId(id);
    }
}
