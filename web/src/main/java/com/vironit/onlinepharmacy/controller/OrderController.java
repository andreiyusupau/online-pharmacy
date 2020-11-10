package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.OrderDto;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.service.order.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Collection<Order> getAll() {
        return orderService.getAll();
    }

    @PostMapping
    public long add(@RequestBody  @Valid OrderDto orderDto) {
        return orderService.add(orderDto);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return orderService.get(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody  @Valid OrderDto orderDto, @PathVariable Long id) {
        orderDto.setId(id);
        orderService.update(orderDto);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        orderService.remove(id);
    }

    @PatchMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id) {
        orderService.confirmOrder(id);
    }

    @PatchMapping("/{id}/pay")
    public void pay(@PathVariable Long id) {
        orderService.payForOrder(id);
    }

    @PatchMapping("/{id}/complete")
    public void complete(@PathVariable Long id) {
        orderService.completeOrder(id);
    }

    @PatchMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }
}