package com.store.service;

import com.store.dto.orderDTOs.OrderDTO;
import com.store.dto.orderDTOs.OrderProductDTO;
import com.store.entity.Category;
import com.store.entity.Order;
import com.store.entity.OrderProduct;
import com.store.entity.Product;
import com.store.enums.OrderStatus;
import com.store.exception.DataNotFoundException;
import com.store.mapper.OrderMapper;
import com.store.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTests {
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderRepository orderRepository;


    @InjectMocks
    private OrderService orderService;

    @Test
    public void testGetOrderByUserIdAndOrderNumber() {
        Order order = createOrderList().get(0);
        OrderDTO expectedOrderDTO = createOrderDTOList().get(0);

        when(orderRepository.existsByNumberAndUserId(expectedOrderDTO.getNumber(), "userId")).thenReturn(true);
        when(orderRepository.findOrderByUserIdAndNumber("userId", order.getNumber())).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(expectedOrderDTO);

        OrderDTO result = orderService.getOrderByUserIdAndOrderNumber("userId", "number");

        verify(orderRepository).existsByNumberAndUserId(expectedOrderDTO.getNumber(), "userId");
        verify(orderRepository).findOrderByUserIdAndNumber("userId", order.getNumber());
        verify(orderMapper).toDto(order);

        assertEquals(expectedOrderDTO.getNumber(), result.getNumber());
        assertEquals(expectedOrderDTO.getStatus(), result.getStatus());
        assertEquals(expectedOrderDTO.getCreatedDate(), result.getCreatedDate());
        assertEquals(expectedOrderDTO.getPrice(), result.getPrice());
        assertEquals(expectedOrderDTO.getCount(), result.getCount());
        assertEquals(expectedOrderDTO.getUserId(), result.getUserId());
        assertEquals(expectedOrderDTO.getOrderProductDTOList().size(), result.getOrderProductDTOList().size());
    }

    @Test
    public void testGetAllOrderByUserId() {
        List<Order> orderList = createOrderList();
        List<OrderDTO> expectedOrderDTOList = createOrderDTOList();

        String userId = "userId";

        Page<Order> orderPage = new PageImpl<>(orderList);

        when(orderRepository.existsAllByUserId(userId)).thenReturn(true);
        when(orderRepository.findAllByUserId(userId, Pageable.unpaged())).thenReturn(orderPage);
        when(orderMapper.toDto(any(Order.class))).thenAnswer(invocationOnMock -> {
            Order order = invocationOnMock.getArgument(0);
            String orderNumber = order.getNumber();
            return expectedOrderDTOList.stream()
                    .filter(dto -> dto.getNumber().equals(orderNumber))
                    .findFirst()
                    .orElse(null);
        });


        Page<OrderDTO> result = orderService.getAllOrdersByUserId(userId, Pageable.unpaged());

        verify(orderRepository).existsAllByUserId(userId);
        verify(orderRepository).findAllByUserId(userId, Pageable.unpaged());
        verify(orderMapper, times(2)).toDto(any(Order.class));

        assertEquals(expectedOrderDTOList.size(), result.getContent().size());

        assertEquals(expectedOrderDTOList.get(0).getNumber(), result.getContent().get(0).getNumber());
        assertEquals(expectedOrderDTOList.get(0).getStatus(), result.getContent().get(0).getStatus());
        assertEquals(expectedOrderDTOList.get(0).getCreatedDate(), result.getContent().get(0).getCreatedDate());
        assertEquals(expectedOrderDTOList.get(0).getPrice(), result.getContent().get(0).getPrice());
        assertEquals(expectedOrderDTOList.get(0).getCount(), result.getContent().get(0).getCount());
        assertEquals(expectedOrderDTOList.get(0).getUserId(), result.getContent().get(0).getUserId());
        assertEquals(expectedOrderDTOList.get(0).getOrderProductDTOList().size(), result.getContent().get(0).getOrderProductDTOList().size());

        assertEquals(expectedOrderDTOList.get(1).getNumber(), result.getContent().get(1).getNumber());
        assertEquals(expectedOrderDTOList.get(1).getStatus(), result.getContent().get(1).getStatus());
        assertEquals(expectedOrderDTOList.get(1).getCreatedDate(), result.getContent().get(1).getCreatedDate());
        assertEquals(expectedOrderDTOList.get(1).getPrice(), result.getContent().get(1).getPrice());
        assertEquals(expectedOrderDTOList.get(1).getCount(), result.getContent().get(1).getCount());
        assertEquals(expectedOrderDTOList.get(1).getUserId(), result.getContent().get(1).getUserId());
        assertEquals(expectedOrderDTOList.get(1).getOrderProductDTOList().size(), result.getContent().get(1).getOrderProductDTOList().size());
    }

    @Test
    public void testCancelOrder() {
        Order order = createOrderList().get(0);
        OrderDTO orderDTO = createOrderDTOList().get(0);

        String userId = "userId";

        OrderDTO expectedOrderDTO = createOrderDTOList().get(0);
        expectedOrderDTO.setStatus(OrderStatus.CANCELLED);

        when(orderRepository.existsByNumberAndUserId(expectedOrderDTO.getNumber(), userId)).thenReturn(true);
        when(orderRepository.findOrderByUserIdAndNumber(userId, order.getNumber())).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDTO);
        when(orderRepository.save(orderMapper.toEntity(expectedOrderDTO))).thenReturn(order);

        orderService.cancelOrder(userId, "number");

        verify(orderRepository).existsByNumberAndUserId(expectedOrderDTO.getNumber(), userId);
        verify(orderRepository).findOrderByUserIdAndNumber(userId, expectedOrderDTO.getNumber());
        verify(orderMapper).toDto(order);
        verify(orderRepository).save(orderMapper.toEntity(expectedOrderDTO));
    }

    @Test
    public void testConfirmOrder() {
        Order order = createOrderList().get(0);
        OrderDTO orderDTO = createOrderDTOList().get(0);

        OrderDTO expectedOrderDTO = createOrderDTOList().get(0);
        expectedOrderDTO.setStatus(OrderStatus.DONE);


        when(orderRepository.existsByNumberAndUserId(expectedOrderDTO.getNumber(), "userId")).thenReturn(true);
        when(orderRepository.findOrderByUserIdAndNumber("userId", order.getNumber())).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDTO);
        when(orderRepository.save(orderMapper.toEntity(expectedOrderDTO))).thenReturn(order);

        orderService.confirmOrder("number", "userId");

        verify(orderRepository).existsByNumberAndUserId(expectedOrderDTO.getNumber(), "userId");
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orderList = createOrderList();
        List<OrderDTO> expectedOrderDTOList = createOrderDTOList();

        Page<Order> orderPage = new PageImpl<>(orderList);

        when(orderRepository.findAll(Pageable.unpaged())).thenReturn(orderPage);
        when(orderMapper.toDto(any(Order.class))).thenAnswer(invocationOnMock -> {
            Order order = invocationOnMock.getArgument(0);
            String orderNumber = order.getNumber();
            return expectedOrderDTOList.stream()
                    .filter(dto -> dto.getNumber().equals(orderNumber))
                    .findFirst()
                    .orElse(null);
        });
        Page<OrderDTO> result = orderService.getAllOrders(Pageable.unpaged());

        verify(orderRepository).findAll(Pageable.unpaged());
        verify(orderMapper, times(2)).toDto(any(Order.class));

        assertEquals(expectedOrderDTOList.size(), result.getContent().size());

        assertEquals(expectedOrderDTOList.get(0).getNumber(), result.getContent().get(0).getNumber());
        assertEquals(expectedOrderDTOList.get(0).getStatus(), result.getContent().get(0).getStatus());
        assertEquals(expectedOrderDTOList.get(0).getCreatedDate(), result.getContent().get(0).getCreatedDate());
        assertEquals(expectedOrderDTOList.get(0).getPrice(), result.getContent().get(0).getPrice());
        assertEquals(expectedOrderDTOList.get(0).getCount(), result.getContent().get(0).getCount());
        assertEquals(expectedOrderDTOList.get(0).getUserId(), result.getContent().get(0).getUserId());
        assertEquals(expectedOrderDTOList.get(0).getOrderProductDTOList().size(), result.getContent().get(0).getOrderProductDTOList().size());

        assertEquals(expectedOrderDTOList.get(1).getNumber(), result.getContent().get(1).getNumber());
        assertEquals(expectedOrderDTOList.get(1).getStatus(), result.getContent().get(1).getStatus());
        assertEquals(expectedOrderDTOList.get(1).getCreatedDate(), result.getContent().get(1).getCreatedDate());
        assertEquals(expectedOrderDTOList.get(1).getPrice(), result.getContent().get(1).getPrice());
        assertEquals(expectedOrderDTOList.get(1).getCount(), result.getContent().get(1).getCount());
        assertEquals(expectedOrderDTOList.get(1).getUserId(), result.getContent().get(1).getUserId());
        assertEquals(expectedOrderDTOList.get(1).getOrderProductDTOList().size(), result.getContent().get(1).getOrderProductDTOList().size());
    }

    @Test
    public void testGetOrderByUserIdAndOrderNumber_WhenNoOrderFoundForUser_ShouldThrowException() {
        String userId = "userId";

        when(orderRepository.existsByNumberAndUserId("number", userId)).thenThrow(
                new DataNotFoundException("There is no order with number number for current logged user")
        );

        assertThrows(DataNotFoundException.class, () -> orderService.getOrderByUserIdAndOrderNumber("userId", "number"));
    }

    @Test
    public void testGetAllOrdersByUserId_WhenNoOrdersFoundForUser_ShouldThrowException() {
        String userId = "userId";

        when(orderRepository.existsAllByUserId(userId)).thenThrow(
                new DataNotFoundException("There is no orders for current logged user")
        );

        assertThrows(DataNotFoundException.class, () -> orderService.getAllOrdersByUserId(userId, Pageable.unpaged()));
    }

    @Test
    public void testCancelOrder_WhenNoOrderFoundForUser_ShouldThrowException() {
        String userId = "userId";

        when(orderRepository.existsByNumberAndUserId("number", userId)).thenThrow(
                new DataNotFoundException("There is no order with number number for current logged user")
        );

        assertThrows(DataNotFoundException.class, () -> orderService.cancelOrder(userId, "number"));
    }

    @Test
    public void testConfirmOrder_WhenNoOrderFoundForUser_ShouldThrowException() {
        String userId = "userId";

        when(orderRepository.existsByNumberAndUserId("number", userId)).thenThrow(
                new DataNotFoundException("There is no order for current logged user with number number")
        );

        assertThrows(DataNotFoundException.class, () -> orderService.confirmOrder("number", userId));
    }

    private List<Order> createOrderList() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category");

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Item 1");
        product1.setDescription("Description 1");
        product1.setCategory(category);
        product1.setPrice(BigDecimal.valueOf(100.99));
        product1.setImagePath("Image src 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Item 2");
        product2.setDescription("Description 2");
        product2.setCategory(category);
        product1.setPrice(BigDecimal.valueOf(99.00));
        product2.setImagePath("Image src 2");

        Order order1 = new Order();
        order1.setNumber("number");
        order1.setStatus(OrderStatus.NEW);
        order1.setCreatedDate(ZonedDateTime.of(LocalDateTime.of(2023, 1, 1, 0, 0),
                ZoneId.of("UTC")));
        order1.setPrice(BigDecimal.valueOf(199, 99));
        order1.setCount(2);
        order1.setUserId("userId");

        OrderProduct orderProduct1 = new OrderProduct();
        orderProduct1.setCount(1);
        orderProduct1.setProduct(product1);
        orderProduct1.setId(1L);
        orderProduct1.setOrder(order1);

        OrderProduct orderProduct2 = new OrderProduct();
        orderProduct2.setCount(1);
        orderProduct2.setProduct(product2);
        orderProduct2.setId(1L);
        orderProduct2.setOrder(order1);

        order1.setOrderProductList(List.of(orderProduct1, orderProduct2));

        Order order2 = new Order();
        order2.setNumber("number1");
        order2.setStatus(OrderStatus.NEW);
        order2.setCreatedDate(ZonedDateTime.of(LocalDateTime.of(2023, 11, 23, 1, 0),
                ZoneId.of("UTC")));
        order2.setPrice(BigDecimal.valueOf(199, 99));
        order2.setCount(2);
        order2.setUserId("userId");

        OrderProduct orderProduct3 = new OrderProduct();
        orderProduct3.setCount(1);
        orderProduct3.setProduct(product1);
        orderProduct3.setId(1L);
        orderProduct3.setOrder(order2);

        OrderProduct orderProduct4 = new OrderProduct();
        orderProduct4.setCount(1);
        orderProduct4.setProduct(product2);
        orderProduct4.setId(1L);
        orderProduct4.setOrder(order2);

        order2.setOrderProductList(List.of(orderProduct3, orderProduct4));

        return List.of(order1, order2);
    }

    private List<OrderDTO> createOrderDTOList() {
        OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setNumber("number");
        orderDTO1.setStatus(OrderStatus.NEW);
        orderDTO1.setCreatedDate(ZonedDateTime.of(LocalDateTime.of(2023, 1, 1, 0, 0),
                ZoneId.of("UTC")));
        orderDTO1.setPrice(BigDecimal.valueOf(199, 99));
        orderDTO1.setCount(2);
        orderDTO1.setUserId("userId");

        OrderProductDTO orderProductDTO1 = new OrderProductDTO();
        orderProductDTO1.setCount(1);
        orderProductDTO1.setProductId(1L);
        orderProductDTO1.setId(1L);
        orderProductDTO1.setOrderNumber(orderDTO1.getNumber());

        OrderProductDTO orderProductDTO2 = new OrderProductDTO();
        orderProductDTO2.setCount(1);
        orderProductDTO2.setProductId(2L);
        orderProductDTO2.setId(1L);
        orderProductDTO2.setOrderNumber(orderDTO1.getNumber());

        orderDTO1.setOrderProductDTOList(List.of(orderProductDTO1, orderProductDTO2));

        OrderDTO orderDTO2 = new OrderDTO();
        orderDTO2.setNumber("number1");
        orderDTO2.setStatus(OrderStatus.NEW);
        orderDTO2.setCreatedDate(ZonedDateTime.of(LocalDateTime.of(2023, 11, 23, 1, 0),
                ZoneId.of("UTC")));
        orderDTO2.setPrice(BigDecimal.valueOf(199, 99));
        orderDTO2.setCount(2);
        orderDTO2.setUserId("userId");

        OrderProductDTO orderProductDTO3 = new OrderProductDTO();
        orderProductDTO3.setCount(1);
        orderProductDTO3.setProductId(1L);
        orderProductDTO3.setProductPrice(BigDecimal.valueOf(100.99));
        orderProductDTO3.setProductName("Item 1");
        orderProductDTO3.setId(1L);
        orderProductDTO3.setOrderNumber("number1");

        OrderProductDTO orderProductDTO4 = new OrderProductDTO();
        orderProductDTO4.setCount(1);
        orderProductDTO4.setProductId(2L);
        orderProductDTO4.setProductName("Item 2");
        orderProductDTO4.setProductPrice(BigDecimal.valueOf(99.00));
        orderProductDTO4.setId(1L);
        orderProductDTO4.setOrderNumber("number1");

        orderDTO2.setOrderProductDTOList(List.of(orderProductDTO3, orderProductDTO4));

        return List.of(orderDTO1, orderDTO2);
    }
}