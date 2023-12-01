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
    public CartDTO addItemToCart(Long productId, Principal principal, String optionalUserIdIfNotAuthenticated) {
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }

        return cartService.addProductToCart(productId, id);
    }

    @GetMapping
    public List<CartDTO> getAllCarts(Principal principal, String optionalUserIdIfNotAuthenticated) {
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }
        return cartService.getAllCarts(id);
    }

    @PutMapping
    public CartDTO updateCountOfItem(Principal principal, Long itemId,
                                     Integer count, String optionalUserIdIfNotAuthenticated){
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }

        return cartService.updateCountOfProduct(itemId, count, id);
    }

    @DeleteMapping("/{itemId}")
    public void deleteCartByItemId(@PathVariable Long itemId, Principal principal,
                                   String optionalUserIdIfNotAuthenticated) {
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }
        cartService.deleteCartByItemId(itemId, id);
    }


    @PostMapping("/create-order")
    public OrderDTO createOrderFromCart(@RequestParam String shippingAddress, @RequestParam String paymentType,
                                        Principal principal,  String optionalUserIdIfNotAuthenticated) {
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }
        return cartService.createOrderFromCart(shippingAddress, paymentType, id);
    }
}