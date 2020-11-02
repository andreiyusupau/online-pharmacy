package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.OrderStatus;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
 class JpaOrderDaoTest {

    @Container
    private PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:13.0-alpine")
            .withDatabaseName("online_pharmacy")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    private User user;
    private User secondUser;
    private Order order;
    private Order secondOrder;
    private Order thirdOrder;

    @BeforeEach
    void set() {
        user = new User();
        user.setFirstName("testFirstName");
        user.setMiddleName("testMiddleName");
        user.setLastName("testLastName");
        user.setDateOfBirth(LocalDate.of(2000, 12, 12));
        user.setEmail("test@email.com");
        user.setPassword("testpass123");
        user.setRole(Role.CONSUMER);
        userDao.add(user);
        secondUser = new User();
        secondUser.setFirstName("testFirstName");
        secondUser.setMiddleName("testMiddleName");
        secondUser.setLastName("testLastName");
        secondUser.setDateOfBirth(LocalDate.of(2000, 10, 12));
        secondUser.setEmail("test2@email.com");
        secondUser.setPassword("testpass123");
        secondUser.setRole(Role.CONSUMER);
        userDao.add(secondUser);
        order = new Order();
        order.setDate(Instant.now());
        order.setOwner(user);
        order.setStatus(OrderStatus.PREPARATION);
        secondOrder = new Order();
        secondOrder.setDate(Instant.now());
        secondOrder.setOwner(secondUser);
        secondOrder.setStatus(OrderStatus.PREPARATION);
        thirdOrder = new Order();
        thirdOrder.setDate(Instant.now());
        thirdOrder.setOwner(user);
        thirdOrder.setStatus(OrderStatus.PREPARATION);
    }

    @Test
    void addShouldAddOrderToCollection() {
        long id = orderDao.add(order);

        long sizeAfterAdd = orderDao.getAll()
                .size();
        assertEquals(1, sizeAfterAdd);
        assertEquals(1, id);
    }

    @Test
    void getShouldGetOrderFromCollection() {
        long id = orderDao.add(order);

        Order acquiredOrder = orderDao.get(id)
                .get();

        assertEquals(order.getDate()
                .truncatedTo(ChronoUnit.MICROS), acquiredOrder.getDate());
        assertEquals(order.getStatus(), acquiredOrder.getStatus());
        assertEquals(order.getOwner(), acquiredOrder.getOwner());
    }

    @Test
    void addAllGetAllShouldAddAndGetAllOrdersFromCollection() {
        Collection<Order> orders = List.of(order,secondOrder,thirdOrder);

        boolean successfulAddAll=orderDao.addAll(orders);

        assertTrue(successfulAddAll);
    }

    @Test
    void updateShouldUpdateOrderInCollection() {
        orderDao.add(order);
        Order orderForUpdate = new Order(1, Instant.now(), user, OrderStatus.IN_PROGRESS);

        orderDao.update(orderForUpdate);

        Order updatedOrder = orderDao.get(1)
                .get();

        assertEquals(orderForUpdate.getDate()
                .truncatedTo(ChronoUnit.MICROS), updatedOrder.getDate());
        assertEquals(orderForUpdate.getStatus(), updatedOrder.getStatus());
        assertEquals(orderForUpdate.getOwner(), updatedOrder.getOwner());
    }

    @Test
    void removeShouldRemoveOrderFromCollection() {
        orderDao.add(order);

        orderDao.remove(1);
        long sizeAfterRemove = orderDao.getAll()
                .size();

        assertEquals(0, sizeAfterRemove);
    }

    @Test
    void getAllByOwnerIdShouldReturnAllOrdersOfUser() {
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        int actualOrdersSize = orderDao.getAllByOwnerId(1)
                .size();

        assertEquals(2,actualOrdersSize);
    }

    @Test
    void removeAllByOwnerIdShouldRemoveAllOrdersOfUser() {
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        orderDao.removeAllByOwnerId(1);

        int actualOrdersSize = orderDao.getAll()
                .size();

        assertEquals(1, actualOrdersSize);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualZero() {
        long totalElements = orderDao.getTotalElements();
        assertEquals(0, totalElements);
    }

    @Test
    void getTotalElementsShouldReturnTotalElementsEqualTwo() {

        orderDao.add(order);
        orderDao.add(secondOrder);
        long totalElements = orderDao.getTotalElements();
        assertEquals(2, totalElements);
    }

    @Test
    void getPageShouldReturnASecondPageWithOneElement() {
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        Collection<Order> orderPage = orderDao.getPage(2, 2);

        Collection<Order> expectedOrderPage = new ArrayList<>();
        expectedOrderPage.add(thirdOrder);
        assertEquals(expectedOrderPage, orderPage);
    }

    @Test
    void getPageShouldReturnAThirdPageWithNoElements() {
        orderDao.add(order);
        orderDao.add(secondOrder);
        orderDao.add(thirdOrder);

        Collection<Order> orderPage = orderDao.getPage(3, 2);

        Collection<Order> expectedOrderPage = new ArrayList<>();
        assertEquals(expectedOrderPage, orderPage);
    }
}
