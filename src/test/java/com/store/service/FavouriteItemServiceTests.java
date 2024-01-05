package com.store.service;

import com.store.dto.FavouriteItemDTO;
import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.productDTOs.ProductDTO;
import com.store.entity.Category;
import com.store.entity.FavouriteItem;
import com.store.entity.Product;
import com.store.enums.ProductStatus;
import com.store.exception.DataNotFoundException;
import com.store.exception.InvalidDataException;
import com.store.mapper.FavouriteItemMapper;
import com.store.repository.FavouriteItemRepository;
import com.store.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FavouriteItemServiceTests {

    @Mock
    private FavouriteItemRepository favouriteItemRepository;

    @Mock
    private FavouriteItemMapper favouriteItemMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FavouriteItemService favouriteItemService;

    @Test
    public void testGetFavouriteItemsByUserId() {
        List<FavouriteItem> favouriteItemList = createFavouriteItemList();
        List<FavouriteItemDTO> expectedFavouriteItemDTOList = createFavouriteItemDTOList();

        when(favouriteItemRepository.findAllByUserId("userId")).thenReturn(favouriteItemList);
        when(favouriteItemMapper.toDto(favouriteItemList)).thenReturn(expectedFavouriteItemDTOList);

        List<FavouriteItemDTO> result = favouriteItemService.getFavouriteItemsByUserId("userId");

        verify(favouriteItemRepository).findAllByUserId("userId");
        verify(favouriteItemMapper).toDto(favouriteItemList);

        assertEquals(expectedFavouriteItemDTOList.size(), result.size());

        assertEquals(expectedFavouriteItemDTOList.get(0).getId(), result.get(0).getId());
        assertEquals(expectedFavouriteItemDTOList.get(0).getProductId(), result.get(0).getProductId());
        assertEquals(expectedFavouriteItemDTOList.get(0).getProductName(), result.get(0).getProductName());
        assertEquals(expectedFavouriteItemDTOList.get(0).getUserId(), result.get(0).getUserId());

        assertEquals(expectedFavouriteItemDTOList.get(1).getId(), result.get(1).getId());
        assertEquals(expectedFavouriteItemDTOList.get(1).getProductId(), result.get(1).getProductId());
        assertEquals(expectedFavouriteItemDTOList.get(1).getProductName(), result.get(1).getProductName());
        assertEquals(expectedFavouriteItemDTOList.get(1).getUserId(), result.get(1).getUserId());
    }

    @Test
    public void testAddItemToFavouriteItem() {
        FavouriteItem favouriteItem = createFavouriteItemList().get(0);
        Long productIdToAdd = 1L;
        FavouriteItemDTO expectedFavouriteItemDTO = createFavouriteItemDTOList().get(0);

        when(productRepository.existsById(productIdToAdd)).thenReturn(true);
        when(favouriteItemRepository.existsByProductIdAndUserId(productIdToAdd, favouriteItem.getUserId())).thenReturn(false);
        when(favouriteItemRepository.save(any())).thenReturn(favouriteItem);
        when(favouriteItemMapper.toDto(favouriteItem)).thenReturn(expectedFavouriteItemDTO);

        FavouriteItemDTO result = favouriteItemService.addProductToFavourites(favouriteItem.getUserId(), productIdToAdd);

        verify(productRepository).existsById(productIdToAdd);
        verify(favouriteItemRepository).existsByProductIdAndUserId(productIdToAdd, favouriteItem.getUserId());
        verify(favouriteItemRepository).save(any());
        verify(favouriteItemMapper).toDto(favouriteItem);

        assertEquals(expectedFavouriteItemDTO.getProductId(), result.getProductId());
        assertEquals(expectedFavouriteItemDTO.getProductName(), result.getProductName());
        assertEquals(expectedFavouriteItemDTO.getId(), result.getId());
        assertEquals(expectedFavouriteItemDTO.getUserId(), result.getUserId());
    }

    @Test
    public void testDeleteFromFavouriteItem() {
        FavouriteItem favouriteItem = createFavouriteItemList().get(0);
        Long productIdToDelete = 1L;

        when(favouriteItemRepository.existsByProductIdAndUserId(productIdToDelete, favouriteItem.getUserId())).thenReturn(true);

        favouriteItemService.deleteFromFavouriteItem("userId", productIdToDelete);

        verify(favouriteItemRepository).deleteFavouriteItemByProductIdAndUserId(productIdToDelete, favouriteItem.getUserId());
    }

    @Test
    public void testAddItemToFavouriteItem_WhenProductDoesNotExist_ShouldThrowException() {
        Long productIdToAdd = 1L;
        when(productRepository.existsById(productIdToAdd)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> favouriteItemService.addProductToFavourites("userId", productIdToAdd));
    }

    @Test
    public void testAddItemToFavouriteItem_WhenProductIsAlreadyInFavouriteItem_ShouldThrowException() {
        FavouriteItem favouriteItem = createFavouriteItemList().get(0);
        Long productIdToAdd = 1L;

        when(productRepository.existsById(productIdToAdd)).thenReturn(true);
        when(favouriteItemRepository.existsByProductIdAndUserId(productIdToAdd, favouriteItem.getUserId())).thenReturn(true);

        assertThrows(InvalidDataException.class, () -> favouriteItemService.addProductToFavourites("userId", productIdToAdd));
    }

    @Test
    public void testDeleteFavouriteItem_WhenFavouriteItemDoesNotExist_ShouldThrowException() {
        Long productIdToDelete = 1L;

        when(favouriteItemRepository.existsByProductIdAndUserId(productIdToDelete, "userId")).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> favouriteItemService.deleteFromFavouriteItem("userId", productIdToDelete));
    }
        private List<FavouriteItem> createFavouriteItemList () {
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

            FavouriteItem favouriteItem1 = new FavouriteItem();
            favouriteItem1.setId(1L);
            favouriteItem1.setProduct(product1);
            favouriteItem1.setUserId("userId");

            FavouriteItem favouriteItem2 = new FavouriteItem();
            favouriteItem1.setId(2L);
            favouriteItem1.setProduct(product2);
            favouriteItem1.setUserId("userId");

            return List.of(favouriteItem1, favouriteItem2);
        }

        private List<FavouriteItemDTO> createFavouriteItemDTOList () {
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

            FavouriteItemDTO favouriteItemDTO1 = new FavouriteItemDTO();
            favouriteItemDTO1.setId(1L);
            favouriteItemDTO1.setProductId(productDTO1.getId());
            favouriteItemDTO1.setProductName(productDTO1.getName());
            favouriteItemDTO1.setUserId("userId");

            FavouriteItemDTO favouriteItemDTO2 = new FavouriteItemDTO();
            favouriteItemDTO1.setId(2L);
            favouriteItemDTO1.setProductId(productDTO2.getId());
            favouriteItemDTO1.setProductName(productDTO2.getName());
            favouriteItemDTO1.setUserId("userId");

            return List.of(favouriteItemDTO1, favouriteItemDTO2);
        }
    }