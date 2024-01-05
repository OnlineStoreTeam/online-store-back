package com.store.service;

import com.store.dto.cartDTOs.CartDTO;
import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.orderDTOs.OrderDTO;
import com.store.dto.orderDTOs.OrderProductDTO;
import com.store.dto.productDTOs.ProductDTO;
import com.store.entity.*;
import com.store.enums.OrderStatus;
import com.store.enums.ProductStatus;
import com.store.exception.DataNotFoundException;
import com.store.exception.InvalidDataException;
import com.store.mapper.CartMapper;
import com.store.repository.CartRepository;
import com.store.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartAndOrderCreationServiceTests {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartAndOrderCreationService cartAndOrderCreationService;


    @Test
    public void testGetAllCartOfUser() {
        String userId = "userId";

        List<Cart> cartList = createCartList();
        List<CartDTO> expectedCartDTOList = createCartDTOList();

        when(cartRepository.existsAllByUserId(any(String.class))).thenReturn(true);
        when(cartRepository.findAllByUserId(userId)).thenReturn(cartList);
        when(cartMapper.toDto(cartList)).thenReturn(expectedCartDTOList);


        List<CartDTO> result = cartAndOrderCreationService.getAllCarts(userId);

        verify(cartRepository).findAllByUserId(userId);
        verify(cartMapper).toDto(any(List.class));

        assertEquals(expectedCartDTOList.get(0).getUserId(), result.get(0).getUserId());
        assertEquals(expectedCartDTOList.get(0).getId(), result.get(0).getId());
        assertEquals(expectedCartDTOList.get(0).getProductId(), result.get(0).getProductId());
        assertEquals(expectedCartDTOList.get(0).getProductName(), result.get(0).getProductName());
        assertEquals(expectedCartDTOList.get(0).getProductPrice(), result.get(0).getProductPrice());
        assertEquals(expectedCartDTOList.get(0).getCount(), result.get(0).getCount());

        assertEquals(expectedCartDTOList.get(1).getUserId(), result.get(1).getUserId());
        assertEquals(expectedCartDTOList.get(1).getId(), result.get(1).getId());
        assertEquals(expectedCartDTOList.get(1).getProductId(), result.get(1).getProductId());
        assertEquals(expectedCartDTOList.get(1).getProductName(), result.get(1).getProductName());
        assertEquals(expectedCartDTOList.get(1).getProductPrice(), result.get(1).getProductPrice());
        assertEquals(expectedCartDTOList.get(1).getCount(), result.get(1).getCount());
    }


    @Test
    public void testAddProductToCart() {
        String userId = "userId";

        Long itemId = 1L;

        CartDTO expectedCartDTO = createCartDTOList().get(0);

        Cart cartToSave = createCartList().get(0);

        Cart savedCart = createCartList().get(0);

        when(productRepository.existsById(itemId)).thenReturn(true);
        when(cartMapper.toEntity(any(CartDTO.class))).thenReturn(cartToSave);
        when(cartRepository.existsByProductIdAndUserId(itemId, userId)).thenReturn(false);
        when(cartRepository.save(cartToSave)).thenReturn(savedCart);
        when(cartMapper.toDto(savedCart)).thenReturn(expectedCartDTO);


        CartDTO result = cartAndOrderCreationService.addProductToCart(itemId, userId);

        verify(cartMapper).toEntity(any(CartDTO.class));
        verify(cartRepository).existsByProductIdAndUserId(itemId, userId);
        verify(cartRepository).save(cartToSave);
        verify(cartMapper).toDto(savedCart);

        assertThat(expectedCartDTO.getUserId()).isEqualTo(userId);
        assertEquals(expectedCartDTO.getId(), result.getId());
        assertEquals(expectedCartDTO.getProductId(), result.getProductId());
        assertEquals(expectedCartDTO.getProductName(), result.getProductName());
        assertEquals(expectedCartDTO.getProductPrice(), result.getProductPrice());
    }

    @Test
    public void testUpdateCountOfProduct() {
        String userId = "userId";
        Long itemId = 1L;
        Integer count = 2;

        Cart cart = createCartList().get(0);

        CartDTO cartDTO = createCartDTOList().get(0);

        cartDTO.setCount(count);

        Cart cartToSave = cart;
        cartToSave.setCount(count);


        when(cartRepository.existsByProductIdAndUserId(itemId, userId)).thenReturn(true);
        when(cartRepository.findCartByProductIdAndUserId(itemId, userId)).thenReturn(cart);
        when(cartMapper.toEntity(any(CartDTO.class))).thenReturn(cartToSave);
        when(cartRepository.save(any())).thenReturn(cartToSave);
        when(cartMapper.toDto(any(Cart.class))).thenReturn(cartDTO);

        CartDTO result = cartAndOrderCreationService.updateCountOfProduct(itemId, count, userId);

        verify(cartRepository).existsByProductIdAndUserId(itemId, userId);
        verify(cartRepository).findCartByProductIdAndUserId(itemId, userId);
        verify(cartMapper).toEntity(any(CartDTO.class));
        verify(cartRepository).save(any());
        verify(cartMapper, times(2)).toDto(any(Cart.class));

        assertEquals(count, result.getCount());
    }

    @Test
    public void testDeleteProductFromCart() {
        String userId = "userId";
        Long productId = 1L;

        when(cartRepository.existsByProductIdAndUserId(productId, userId)).thenReturn(true);

        cartAndOrderCreationService.deleteCartByProductId(productId, userId);

        verify(cartRepository).deleteCartByProductId(productId);
    }

    @Test
    public void testDeleteCartByUserIs() {
        String userId = "userId";

        when(cartRepository.existsAllByUserId(userId)).thenReturn(true);

        cartAndOrderCreationService.deleteCart(userId);

        verify(cartRepository).deleteAllByUserId(userId);
    }

    @Test
    public void testCreateOrderFromCart() {
        String userId = "userId";

        List<Cart> cartList = createCartList();

        List<CartDTO> cartDTOList = createCartDTOList();

        Integer count = 0;
        BigDecimal price = BigDecimal.ZERO;

        Order order = new Order();
        order.setNumber("number");
        order.setCreatedDate(ZonedDateTime.now());
        order.setStatus(OrderStatus.NEW);
        order.setUserId(userId);

        List<OrderProduct> orderProductList = new ArrayList<>();
        for (CartDTO cartDTO : cartDTOList) {
            OrderProduct orderProduct = new OrderProduct();

            orderProduct.setOrder(order);
            if (cartDTO.getCount() < 1) {
                throw new InvalidDataException("Please check count of items in your cart");
            }
            orderProduct.setCount(cartDTO.getCount());

            orderProductList.add(orderProduct);


            price = price.add(cartDTO.getProductPrice()).multiply(BigDecimal.valueOf(cartDTO.getCount()));
            count += cartDTO.getCount();
        }
        order.setCount(count);
        order.setPrice(price);
        order.setOrderProductList(orderProductList);


        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumber("number");
        orderDTO.setCreatedDate(ZonedDateTime.now());
        orderDTO.setStatus(OrderStatus.NEW);
        orderDTO.setUserId(userId);

        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
        Integer count1 = 0;
        BigDecimal price1 = BigDecimal.ZERO;
        for (CartDTO cartDTO : cartDTOList) {
            OrderProductDTO orderProductDTO = new OrderProductDTO();

            orderProductDTO.setOrderNumber(orderDTO.getNumber());
            orderProductDTO.setProductId(cartDTO.getProductId());
            if (cartDTO.getCount() < 1) {
                throw new InvalidDataException("Please check count of items in your cart");
            }
            orderProductDTO.setCount(cartDTO.getCount());

            orderProductDTOList.add(orderProductDTO);


            price1 = price1.add(cartDTO.getProductPrice()).multiply(BigDecimal.valueOf(cartDTO.getCount()));
            count1 += cartDTO.getCount();
        }
        orderDTO.setCount(count1);
        orderDTO.setPrice(price1);
        orderDTO.setOrderProductDTOList(orderProductDTOList);

        when(cartRepository.existsAllByUserId(userId)).thenReturn(true);
        when(cartRepository.findAllByUserId(userId)).thenReturn(cartList);
        when(cartMapper.toDto(cartList)).thenReturn(cartDTOList);

        OrderDTO result = cartAndOrderCreationService.createOrderFromCart("someAddress", "credit", userId);

        verify(cartRepository, times(2)).existsAllByUserId(userId);
        verify(cartRepository).findAllByUserId(userId);
        verify(cartMapper).toDto(cartList);

        assertEquals(orderDTO.getCount(), result.getCount());
        assertEquals(orderDTO.getUserId(), result.getUserId());
        assertEquals(orderDTO.getPrice(), result.getPrice());
        assertEquals(orderDTO.getOrderProductDTOList().size(), result.getOrderProductDTOList().size());
    }

    @Test
    public void testGetAllCarts_WhenCartIsEmpty_ShouldThrowException() {
        assertThrows(DataNotFoundException.class, () -> cartAndOrderCreationService.getAllCarts("userId"));
    }

    @Test
    public void testAddItemToCart_WhenItemIsAlreadyInCart_ShouldThrowException() {
        String userId = "userId";

        when(productRepository.existsById(any(Long.class))).thenReturn(true);
        when(cartRepository.existsByProductIdAndUserId(any(Long.class), any(String.class))).thenThrow(
                new InvalidDataException("Item is already in cart")
        );

        assertThrows(InvalidDataException.class, () -> cartAndOrderCreationService.addProductToCart(1L, userId));
    }

    @Test
    public void testUpdateCountOfItem_WhenItemIsNotInCart_ShouldThrowException() {
        assertThrows(DataNotFoundException.class, () -> cartAndOrderCreationService.updateCountOfProduct(1L, 2, "userId"));
    }

    @Test
    public void testUpdateCountOfItem_WhenCountIsLessThanOne_ShouldThrowException() {
        Integer count = -1;
        String userId = "userId";

        when(cartRepository.existsByProductIdAndUserId(1L, userId)).thenReturn(true);

        assertThrows(InvalidDataException.class, () -> cartAndOrderCreationService.updateCountOfProduct(1L, count, userId));
    }

    @Test
    public void testDeleteItemFromCart_WhenItemIsNotInCart_ShouldThrowException() {
        assertThrows(DataNotFoundException.class, () -> cartAndOrderCreationService.deleteCartByProductId(1L, "userId"));
    }

    @Test
    public void testCreateOrderFromCart_WhenCartIsEmpty_ShouldThrowException() {
        assertThrows(InvalidDataException.class, () -> cartAndOrderCreationService.createOrderFromCart("someAddress", "credit", "userId"));
    }

    @Test
    public void testCreateOrderFromCart_WhenCountOfItemIsLessThanOne_ShouldThrowException() {
        List<Cart> cartList = createCartList();

        List<CartDTO> cartDTOList = createCartDTOList();

        cartDTOList.get(0).setCount(0);

        when(cartRepository.existsAllByUserId("userId")).thenReturn(true);
        when(cartRepository.findAllByUserId("userId")).thenReturn(cartList);
        when(cartMapper.toDto(cartList)).thenReturn(cartDTOList);

        assertThrows(InvalidDataException.class, () -> cartAndOrderCreationService.createOrderFromCart("someAddress", "credit", "userId"));
    }

    private List<Cart> createCartList() {
        List<Cart> cartList = new ArrayList<>();

        List<Product> productList = createProductList();

        String userId = "userId";

        Cart cart1 = new Cart();
        cart1.setId(1L);
        cart1.setUserId(userId);
        cart1.setProduct(productList.get(0));
        cart1.setCount(1);

        Cart cart2 = new Cart();
        cart2.setId(1L);
        cart2.setUserId(userId);
        cart2.setProduct(productList.get(0));
        cart2.setCount(1);

        cartList.add(cart1);
        cartList.add(cart2);

        return cartList;
    }

    private List<Product> createProductList() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("category1");
        category1.setTitle("title1");
        category1.setPath("path1");
        category1.setProductCount(1L);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("product1");
        product1.setPrice(BigDecimal.valueOf(100));
        product1.setQuantity(10);
        product1.setProductStatus(ProductStatus.ACTIVE);
        product1.setArticle("AAAAA");
        product1.setCategory(category1);
        product1.setDescription("description1");
        product1.setImagePath("imagePath1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("product2");
        product2.setPrice(BigDecimal.valueOf(200));
        product2.setQuantity(20);
        product2.setProductStatus(ProductStatus.ACTIVE);
        product2.setArticle("BBBBB");
        product2.setCategory(category1);
        product2.setDescription("description2");
        product2.setImagePath("imagePath2");

        return List.of(product1, product2);
    }

    private List<ProductDTO> createProductDTOList() {
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName("category1");
        categoryDTO1.setTitle("title1");
        categoryDTO1.setPath("path1");
        categoryDTO1.setProductCount(1L);

        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(1L);
        productDTO1.setName("product1");
        productDTO1.setPrice(BigDecimal.valueOf(100));
        productDTO1.setQuantity(10);
        productDTO1.setProductStatus(ProductStatus.ACTIVE);
        productDTO1.setArticle("AAAAA");
        productDTO1.setCategoryId(categoryDTO1.getId());
        productDTO1.setCategoryName(categoryDTO1.getName());
        productDTO1.setDescription("description1");
        productDTO1.setImagePath("imagePath1");

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setId(2L);
        productDTO2.setName("product2");
        productDTO2.setPrice(BigDecimal.valueOf(200));
        productDTO2.setQuantity(20);
        productDTO2.setProductStatus(ProductStatus.ACTIVE);
        productDTO2.setArticle("BBBBB");
        productDTO2.setCategoryId(categoryDTO1.getId());
        productDTO2.setCategoryName(categoryDTO1.getName());
        productDTO2.setDescription("description2");
        productDTO2.setImagePath("imagePath2");

        return List.of(productDTO1, productDTO2);
    }

    private List<CartDTO> createCartDTOList() {
        List<CartDTO> cartDTOList = new ArrayList<>();

        List<ProductDTO> productDTOList = createProductDTOList();

        String userId = "userId";

        CartDTO cartDTO1 = new CartDTO();
        cartDTO1.setId(1L);
        cartDTO1.setUserId(userId);
        cartDTO1.setProductId(productDTOList.get(0).getId());
        cartDTO1.setProductName(productDTOList.get(0).getName());
        cartDTO1.setProductPrice(productDTOList.get(0).getPrice());
        cartDTO1.setCount(1);

        CartDTO cartDTO2 = new CartDTO();
        cartDTO2.setUserId(userId);
        cartDTO2.setProductId(productDTOList.get(1).getId());
        cartDTO2.setProductName(productDTOList.get(1).getName());
        cartDTO2.setProductPrice(productDTOList.get(1).getPrice());
        cartDTO2.setCount(1);

        cartDTOList.add(cartDTO1);
        cartDTOList.add(cartDTO2);

        return cartDTOList;
    }
}