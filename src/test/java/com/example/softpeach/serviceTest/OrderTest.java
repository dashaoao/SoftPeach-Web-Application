package com.example.softpeach.serviceTest;

import com.example.softpeach.models.Order;
import com.example.softpeach.repositories.OrderRepository;
import com.example.softpeach.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderTest {
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    public OrderTest() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void testListOrders() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order("Product 1", "ph", "ad", "com", "p", 10);
        Order order2 = new Order("Product 2", "ph", "ad", "com", "p", 20);
        orders.add(order1);
        orders.add(order2);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.listOrders();

        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals(10, result.get(0).getAmount());
        assertEquals("Product 2", result.get(1).getName());
        assertEquals(20, result.get(1).getAmount());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order("Product 1", "ph", "ad", "com", "p", 10);

        orderService.saveOrder(order);

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetOrderById() {
        Order order = new Order( "Product 1", "ph", "ad", "com", "p", 10);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertEquals("Product 1", result.getName());
        assertEquals(10, result.getAmount());

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteOrder() {
        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }
}