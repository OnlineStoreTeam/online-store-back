package com.store.restcontroller;

import com.store.dto.cartDTOs.CartDTO;
import com.store.dto.orderDTOs.OrderDTO;
import com.store.service.CartAndOrderCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@CrossOrigin(origins = "https://onlinestoreteam.github.io/products")
public class CartController {

    private final CartAndOrderCreationService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartByProductId(@PathVariable Long productId, Principal principal,
                                   String optionalUserIdIfNotAuthenticated) {
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }
        cartService.deleteCartByProductId(productId, id);
    }


    @PostMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrderFromCart(String shippingAddress, String paymentType,
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