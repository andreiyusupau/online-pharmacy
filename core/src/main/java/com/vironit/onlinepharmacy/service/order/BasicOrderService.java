package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dto.OrderDto;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.repository.OrderRepository;
import com.vironit.onlinepharmacy.service.exception.OrderServiceException;
import com.vironit.onlinepharmacy.service.stock.StockService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderPositionService orderPositionService;
    private final StockService stockService;

    public BasicOrderService(OrderRepository orderRepository, OrderPositionService orderPositionService, StockService stockService) {
        this.orderRepository = orderRepository;
        this.orderPositionService = orderPositionService;
        this.stockService = stockService;
    }

    @Override
    public long add(OrderDto orderDto) {
        User owner = new User();
        owner.setId(orderDto.getOwnerId());
        Order order = new Order(-1, Instant.now(), owner, OrderStatus.PREPARATION);
        long id = orderRepository.save(order)
                .getId();
        order.setId(id);
        List<OrderPosition> orderPositions = orderDto.getPositionDataList()
                .stream()
                .map(positionData -> {
                    Product product = new Product();
                    product.setId(positionData.getProductId());
                    return new OrderPosition(-1, positionData.getQuantity(), product, order);
                })
                .collect(Collectors.toList());
        orderPositionService.addAll(orderPositions);
        return id;
    }

    @Override
    public void payForOrder(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Can't pay for order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Override
    public void confirmOrder(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Can't confirm order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.IN_PROGRESS);
        Collection<OrderPosition> positions = orderPositionService.getAllByOwnerId(id);
        stockService.reserveInStock(positions);
        orderRepository.save(order);
    }

    @Override
    public void completeOrder(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Can't complete order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.COMPLETE);
        orderRepository.save(order);
        Collection<OrderPosition> orderPositions = orderPositionService.getAllByOwnerId(id);
        stockService.takeFromStock(orderPositions);
    }

    @Override
    public void cancelOrder(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Can't cancel order. Order with id " + id + " not found."));
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        Collection<OrderPosition> orderPositions = orderPositionService.getAllByOwnerId(id);
        stockService.annulReservationInStock(orderPositions);
    }

    @Override
    public Order get(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Can't get order. Order with id " + id + " not found."));
    }

    @Override
    public Collection<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void update(OrderDto orderDto) {
        User owner = new User();
        owner.setId(orderDto.getOwnerId());
        Order order = get(orderDto.getId());
        order.setOwner(owner);
        List<OrderPosition> operationPositions = orderDto.getPositionDataList()
                .stream()
                .map(positionData -> {
                    Product product = new Product();
                    product.setId(positionData.getProductId());
                    return new OrderPosition(-1, positionData.getQuantity(), product, order);
                })
                .collect(Collectors.toList());
        orderPositionService.removeAllByOwnerId(orderDto.getOwnerId());
        orderPositionService.addAll(operationPositions);
    }

    @Override
    public Collection<Order> getAllByOwnerId(long id) {
        return orderRepository.findByOwner_Id(id);
    }

    @Override
    public void remove(long id) {
        orderRepository.deleteById(id);
        orderPositionService.removeAllByOwnerId(id);
    }
}
