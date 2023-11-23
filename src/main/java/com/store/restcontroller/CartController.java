package com.store.restcontroller;

import com.store.dto.cartDTOs.CartDTO;
import com.store.dto.orderDTOs.OrderDTO;
import com.store.service.CartAndOrderCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartAndOrderCreationService cartService;

    @PostMapping
    public CartDTO addItemToCart(Long productId, Principal principal) {
        return cartService.addProductToCart(productId, principal.getName());
    }

    @GetMapping
    public List<CartDTO> getAllCarts(Principal principal) {
        return cartService.getAllCarts(principal.getName());
    }

    @PutMapping
    public CartDTO updateCountOfItem(Principal principal, Long itemId, Integer count){
        return cartService.updateCountOfProduct(itemId, count, principal.getName());
    }

    @DeleteMapping("/{itemId}")
    public void deleteCartByItemId(@PathVariable Long itemId, Principal principal) {
        cartService.deleteCartByItemId(itemId, principal.getName());
    }


    @PostMapping("/create-order")
    public OrderDTO createOrderFromCart(@RequestParam String shippingAddress, @RequestParam String paymentType, Principal principal) {
        return cartService.createOrderFromCart(shippingAddress, paymentType, principal.getName());
    }
}