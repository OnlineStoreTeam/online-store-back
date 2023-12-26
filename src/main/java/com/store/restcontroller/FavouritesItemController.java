package com.store.restcontroller;

import com.store.dto.FavouriteItemDTO;
import com.store.service.FavouriteItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favourites")
public class FavouritesItemController {
    private final FavouriteItemService favouriteItemService;

    @GetMapping
    public List<FavouriteItemDTO> getAllFavouriteItemsByUserId(Principal principal,
                                                               String optionalUserIdIfNotAuthenticated) {
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }
        return favouriteItemService.getFavouriteItemsByUserId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavouriteItemDTO addItemToFavouriteItems(Principal principal, String optionalUserIdIfNotAuthenticated,
                                                    Long productId) {
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }
        return favouriteItemService.addItemToFavouriteItem(id, productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemFromFavouriteItem(Principal principal, String optionalUserIdIfNotAuthenticated,
                                            @PathVariable Long productId){
        String id;

        if(principal == null) {
            id = optionalUserIdIfNotAuthenticated;
        } else {
            id = principal.getName();
        }
        favouriteItemService.deleteFromFavouriteItem(id, productId);
    }
}