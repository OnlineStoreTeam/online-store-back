package com.store.service;

import com.store.dto.FavouriteItemDTO;
import com.store.exception.DataNotFoundException;
import com.store.mapper.FavouriteItemMapper;
import com.store.repository.FavouriteItemRepository;
import com.store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavouriteItemService {
    private final FavouriteItemRepository favouriteItemRepository;
    private final ProductRepository productRepository;
    private final FavouriteItemMapper favouriteItemMapper;

    public List<FavouriteItemDTO> getFavouriteItemsByUserId(String userId) {
        return favouriteItemMapper.toDto(favouriteItemRepository.findAllByUserId(userId));
    }

    public FavouriteItemDTO addItemToFavouriteItem(String userId, Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new DataNotFoundException("There is no product with id " + productId);
        }
        FavouriteItemDTO favouriteItemDTO = new FavouriteItemDTO();
        favouriteItemDTO.setProductId(productId);
        favouriteItemDTO.setUserId(userId);
        return favouriteItemMapper.toDto(favouriteItemRepository.save(favouriteItemMapper.toEntity(favouriteItemDTO)));
    }

    public void deleteFromFavouriteItem(String userId, Long productId) {
        if (!favouriteItemRepository.existsByIdAndUserId(productId, userId)) {
            throw new DataNotFoundException("There is no favourites product with id " + productId
                    + " for user with userId " + userId);
        }
        favouriteItemRepository.deleteFavouriteItemByProductIdAndUserId(productId, userId);
    }
}