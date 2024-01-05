package com.store.service;

import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.productDTOs.ProductCreateDTO;
import com.store.dto.productDTOs.ProductDTO;
import com.store.dto.productDTOs.ProductUpdateDTO;
import com.store.entity.Category;
import com.store.entity.Product;
import com.store.enums.ProductStatus;
import com.store.exception.DataNotFoundException;
import com.store.exception.InvalidDataException;
import com.store.mapper.ProductMapper;
import com.store.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetAllProducts() {
        List<Product> productList = createProductList();
        List<ProductDTO> expectedProductDTOList = createProductDTOList();

        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository
                .findProductsByProductStatusIsNotOrderByProductStatus(ProductStatus.DELETED, Pageable.unpaged()))
                .thenReturn(productPage);

        Page<ProductDTO> result = productService.getAllProducts(Pageable.unpaged());

        verify(productRepository)
                .findProductsByProductStatusIsNotOrderByProductStatus(ProductStatus.DELETED, Pageable.unpaged());

        assertEquals(expectedProductDTOList.size(), result.getContent().size());

        assertEquals(expectedProductDTOList.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(expectedProductDTOList.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(expectedProductDTOList.get(0).getArticle(), result.getContent().get(0).getArticle());
        assertEquals(expectedProductDTOList.get(0).getPrice(), result.getContent().get(0).getPrice());
        assertEquals(expectedProductDTOList.get(0).getCategoryId(), result.getContent().get(0).getCategoryId());
        assertEquals(expectedProductDTOList.get(0).getCategoryName(), result.getContent().get(0).getCategoryName());
        assertEquals(expectedProductDTOList.get(0).getQuantity(), result.getContent().get(0).getQuantity());
        assertEquals(expectedProductDTOList.get(0).getProductStatus(), result.getContent().get(0).getProductStatus());
        assertEquals(expectedProductDTOList.get(0).getDescription(), result.getContent().get(0).getDescription());
        assertEquals(expectedProductDTOList.get(0).getImagePath(), result.getContent().get(0).getImagePath());


        assertEquals(expectedProductDTOList.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(expectedProductDTOList.get(1).getName(), result.getContent().get(1).getName());
        assertEquals(expectedProductDTOList.get(1).getArticle(), result.getContent().get(1).getArticle());
        assertEquals(expectedProductDTOList.get(1).getPrice(), result.getContent().get(1).getPrice());
        assertEquals(expectedProductDTOList.get(1).getCategoryId(), result.getContent().get(1).getCategoryId());
        assertEquals(expectedProductDTOList.get(1).getCategoryName(), result.getContent().get(1).getCategoryName());
        assertEquals(expectedProductDTOList.get(1).getQuantity(), result.getContent().get(1).getQuantity());
        assertEquals(expectedProductDTOList.get(1).getProductStatus(), result.getContent().get(1).getProductStatus());
        assertEquals(expectedProductDTOList.get(1).getDescription(), result.getContent().get(1).getDescription());
        assertEquals(expectedProductDTOList.get(1).getImagePath(), result.getContent().get(1).getImagePath());
    }

    @Test
    public void testGetOneProductById() {
        Product product = createProductList().get(0);
        ProductDTO expectedProductDTO = createProductDTOList().get(0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(expectedProductDTO);

        ProductDTO result = productService.getOneProductById(1L);

        verify(productRepository).findById(1L);
        verify(productMapper).toDto(product);

        assertEquals(expectedProductDTO.getId(), result.getId());
        assertEquals(expectedProductDTO.getName(), result.getName());
        assertEquals(expectedProductDTO.getArticle(), result.getArticle());
        assertEquals(expectedProductDTO.getPrice(), result.getPrice());
        assertEquals(expectedProductDTO.getCategoryId(), result.getCategoryId());
        assertEquals(expectedProductDTO.getCategoryName(), result.getCategoryName());
        assertEquals(expectedProductDTO.getDescription(), result.getDescription());
        assertEquals(expectedProductDTO.getImagePath(), result.getImagePath());
        assertEquals(expectedProductDTO.getProductStatus(), result.getProductStatus());
        assertEquals(expectedProductDTO.getQuantity(), result.getQuantity());
    }

    @Test
    public void testGetProductByCategoryId() {
        List<Product> productList = createProductList();
        List<ProductDTO> expectedProductDTOList = createProductDTOList();

        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findProductsByCategoryIdAndProductStatusIsNotOrderByProductStatus(1L,
                ProductStatus.DELETED,
                Pageable.unpaged()))
                .thenReturn(productPage);

        Page<ProductDTO> result = productService.getProductsByCategoryId(1L, Pageable.unpaged());

        verify(productRepository)
                .findProductsByCategoryIdAndProductStatusIsNotOrderByProductStatus(1L,
                        ProductStatus.DELETED,
                        Pageable.unpaged());

        assertEquals(expectedProductDTOList.size(), result.getContent().size());

        assertEquals(expectedProductDTOList.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(expectedProductDTOList.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(expectedProductDTOList.get(0).getArticle(), result.getContent().get(0).getArticle());
        assertEquals(expectedProductDTOList.get(0).getPrice(), result.getContent().get(0).getPrice());
        assertEquals(expectedProductDTOList.get(0).getCategoryId(), result.getContent().get(0).getCategoryId());
        assertEquals(expectedProductDTOList.get(0).getCategoryName(), result.getContent().get(0).getCategoryName());
        assertEquals(expectedProductDTOList.get(0).getQuantity(), result.getContent().get(0).getQuantity());
        assertEquals(expectedProductDTOList.get(0).getProductStatus(), result.getContent().get(0).getProductStatus());
        assertEquals(expectedProductDTOList.get(0).getDescription(), result.getContent().get(0).getDescription());
        assertEquals(expectedProductDTOList.get(0).getImagePath(), result.getContent().get(0).getImagePath());


        assertEquals(expectedProductDTOList.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(expectedProductDTOList.get(1).getName(), result.getContent().get(1).getName());
        assertEquals(expectedProductDTOList.get(1).getArticle(), result.getContent().get(1).getArticle());
        assertEquals(expectedProductDTOList.get(1).getPrice(), result.getContent().get(1).getPrice());
        assertEquals(expectedProductDTOList.get(1).getCategoryId(), result.getContent().get(1).getCategoryId());
        assertEquals(expectedProductDTOList.get(1).getCategoryName(), result.getContent().get(1).getCategoryName());
        assertEquals(expectedProductDTOList.get(1).getQuantity(), result.getContent().get(1).getQuantity());
        assertEquals(expectedProductDTOList.get(1).getProductStatus(), result.getContent().get(1).getProductStatus());
        assertEquals(expectedProductDTOList.get(1).getDescription(), result.getContent().get(1).getDescription());
        assertEquals(expectedProductDTOList.get(1).getImagePath(), result.getContent().get(1).getImagePath());

    }

    @Test
    public void testCreateProduct() {
        Product product = createProductList().get(0);

        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName(product.getName());
        productCreateDTO.setPrice(product.getPrice());
        productCreateDTO.setQuantity(product.getQuantity());
        productCreateDTO.setArticle(product.getArticle());
        productCreateDTO.setCategoryId(product.getCategory().getId());
        productCreateDTO.setDescription(product.getDescription());
        productCreateDTO.setImagePath(product.getImagePath());

        ProductDTO expectedProductDTO = createProductDTOList().get(0);

        when(productRepository.existsByCategoryId(product.getCategory().getId())).thenReturn(true);
        when(productMapper.toEntity(productCreateDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(expectedProductDTO);

        ProductDTO result = productService.addProduct(productCreateDTO);

        verify(productRepository).existsByCategoryId(product.getCategory().getId());
        verify(productMapper).toEntity(productCreateDTO);
        verify(productRepository).save(product);
        verify(productMapper).toDto(product);

        assertEquals(expectedProductDTO.getName(), result.getName());

        assertEquals(expectedProductDTO.getId(), result.getId());
        assertEquals(expectedProductDTO.getName(), result.getName());
        assertEquals(expectedProductDTO.getArticle(), result.getArticle());
        assertEquals(expectedProductDTO.getPrice(), result.getPrice());
        assertEquals(expectedProductDTO.getCategoryId(), result.getCategoryId());
        assertEquals(expectedProductDTO.getCategoryName(), result.getCategoryName());
        assertEquals(expectedProductDTO.getDescription(), result.getDescription());
        assertEquals(expectedProductDTO.getImagePath(), result.getImagePath());
        assertEquals(expectedProductDTO.getProductStatus(), result.getProductStatus());
        assertEquals(expectedProductDTO.getQuantity(), result.getQuantity());
    }

    @Test
    public void testUpdateProduct() {
        Product product = createProductList().get(0);
        product.setName("newProduct");

        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
        productUpdateDTO.setId(product.getId());
        productUpdateDTO.setName("newProduct");
        productUpdateDTO.setPrice(product.getPrice());
        productUpdateDTO.setQuantity(product.getQuantity());
        productUpdateDTO.setArticle(product.getArticle());
        productUpdateDTO.setCategoryId(product.getCategory().getId());
        productUpdateDTO.setDescription(product.getDescription());
        productUpdateDTO.setImagePath(product.getImagePath());

        ProductDTO expectedProductDTO = createProductDTOList().get(0);
        expectedProductDTO.setName("newProduct");


        when(productRepository.existsById(product.getId())).thenReturn(true);
        when(productMapper.toEntity(productUpdateDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(expectedProductDTO);

        ProductDTO result = productService.updateProduct(productUpdateDTO);

        verify(productRepository).existsById(product.getId());
        verify(productMapper).toEntity(productUpdateDTO);
        verify(productRepository).save(product);
        verify(productMapper).toDto(product);

        assertEquals(expectedProductDTO.getId(), result.getId());
        assertEquals(expectedProductDTO.getName(), result.getName());
        assertEquals(expectedProductDTO.getArticle(), result.getArticle());
        assertEquals(expectedProductDTO.getPrice(), result.getPrice());
        assertEquals(expectedProductDTO.getCategoryId(), result.getCategoryId());
        assertEquals(expectedProductDTO.getCategoryName(), result.getCategoryName());
        assertEquals(expectedProductDTO.getDescription(), result.getDescription());
        assertEquals(expectedProductDTO.getImagePath(), result.getImagePath());
        assertEquals(expectedProductDTO.getProductStatus(), result.getProductStatus());
        assertEquals(expectedProductDTO.getQuantity(), result.getQuantity());
    }

    @Test
    public void testDeleteProduct() {
        Product product = createProductList().get(0);
        product.setProductStatus(ProductStatus.DELETED);

        ProductDTO productDTO = createProductDTOList().get(0);
        productDTO.setProductStatus(ProductStatus.DELETED);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDTO);

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        productService.deleteProduct(product.getId());

        verify(productRepository).save(product);
    }

    @Test
    public void testSearchProduct() {
        List<Product> productList = createProductList();
        List<ProductDTO> expectedProductDTOList = createProductDTOList();

        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findProductByNameContainsIgnoreCase("product", Pageable.unpaged()))
                .thenReturn(productPage);
        when(productMapper.toDto(productPage)).thenAnswer(invocationOnMock -> {
            Product product = invocationOnMock.getArgument(0);
            Long productId = product.getId();
            return expectedProductDTOList.stream()
                    .filter(dto -> dto.getId().equals(productId))
                    .findFirst()
                    .orElse(null);
        });

        Page<ProductDTO> result = productService.searchProducts("product", Pageable.unpaged());

        verify(productRepository).findProductByNameContainsIgnoreCase("product", Pageable.unpaged());

        assertEquals(expectedProductDTOList.size(), result.getContent().size());

        assertEquals(expectedProductDTOList.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(expectedProductDTOList.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(expectedProductDTOList.get(0).getArticle(), result.getContent().get(0).getArticle());
        assertEquals(expectedProductDTOList.get(0).getPrice(), result.getContent().get(0).getPrice());
        assertEquals(expectedProductDTOList.get(0).getCategoryId(), result.getContent().get(0).getCategoryId());
        assertEquals(expectedProductDTOList.get(0).getCategoryName(), result.getContent().get(0).getCategoryName());
        assertEquals(expectedProductDTOList.get(0).getQuantity(), result.getContent().get(0).getQuantity());
        assertEquals(expectedProductDTOList.get(0).getProductStatus(), result.getContent().get(0).getProductStatus());
        assertEquals(expectedProductDTOList.get(0).getDescription(), result.getContent().get(0).getDescription());
        assertEquals(expectedProductDTOList.get(0).getImagePath(), result.getContent().get(0).getImagePath());


        assertEquals(expectedProductDTOList.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(expectedProductDTOList.get(1).getName(), result.getContent().get(1).getName());
        assertEquals(expectedProductDTOList.get(1).getArticle(), result.getContent().get(1).getArticle());
        assertEquals(expectedProductDTOList.get(1).getPrice(), result.getContent().get(1).getPrice());
        assertEquals(expectedProductDTOList.get(1).getCategoryId(), result.getContent().get(1).getCategoryId());
        assertEquals(expectedProductDTOList.get(1).getCategoryName(), result.getContent().get(1).getCategoryName());
        assertEquals(expectedProductDTOList.get(1).getQuantity(), result.getContent().get(1).getQuantity());
        assertEquals(expectedProductDTOList.get(1).getProductStatus(), result.getContent().get(1).getProductStatus());
        assertEquals(expectedProductDTOList.get(1).getDescription(), result.getContent().get(1).getDescription());
        assertEquals(expectedProductDTOList.get(1).getImagePath(), result.getContent().get(1).getImagePath());
    }

    @Test
    public void testGetOneProductById_WhenProductDoesNotExists_ShouldThrowException() {
        when(productRepository.findById(1L)).thenThrow(new DataNotFoundException("There is no product with id 1"));

        assertThrows(DataNotFoundException.class, () -> productService.getOneProductById(1L));
    }

    @Test
    public void testCreateProduct_WhenCategoryDoesNotExists_ShouldThrowException() {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setCategoryId(1L);

        when(productRepository.existsByCategoryId(1L)).thenThrow(
                new DataNotFoundException("There is no category with id " + productCreateDTO.getCategoryId()));

        assertThrows(DataNotFoundException.class, () -> productService.addProduct(productCreateDTO));
    }

    @Test
    public void testCreateProduct_WhenProductAlreadyExists_ShouldThrowException() {
        Product product = createProductList().get(0);

        ProductCreateDTO productCreateDTO = new ProductCreateDTO();

        productCreateDTO.setName("product1");
        productCreateDTO.setPrice(BigDecimal.valueOf(100));
        productCreateDTO.setQuantity(10);
        productCreateDTO.setProductStatus(ProductStatus.ACTIVE);
        productCreateDTO.setArticle("AAAAA");
        productCreateDTO.setCategoryId(1L);
        productCreateDTO.setDescription("description1");
        productCreateDTO.setImagePath("imagePath1");

        when(productRepository.existsByCategoryId(1L)).thenReturn(true);
        when(productMapper.toEntity(productCreateDTO)).thenReturn(product);
        when(productRepository.save(product)).thenThrow(new InvalidDataException("Please, check for duplicate entries"));

        assertThrows(InvalidDataException.class, () -> productService.addProduct(productCreateDTO));
    }

    @Test
    public void testSearchProduct_WhenResultIsEmpty_ShouldReturnEmptyList() {
        when(productRepository.findProductByNameContainsIgnoreCase("product", Pageable.unpaged()))
                .thenReturn(Page.empty());

        Page<ProductDTO> result = productService.searchProducts("product", Pageable.unpaged());

        assertEquals(result, Page.empty());
    }

    @Test
    public void testUpdateProduct_WhenProductDoesNotExists_ShouldThrowException() {
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
        productUpdateDTO.setId(1L);

        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> productService.updateProduct(productUpdateDTO));
    }

    @Test
    public void testUpdateProduct_WhenProductAlreadyExists_ShouldThrowException() {
        Product product = createProductList().get(0);

        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
        productUpdateDTO.setId(1L);
        productUpdateDTO.setName("product1");
        productUpdateDTO.setPrice(BigDecimal.valueOf(100));
        productUpdateDTO.setQuantity(10);
        productUpdateDTO.setProductStatus(ProductStatus.ACTIVE);
        productUpdateDTO.setArticle("AAAAA");
        productUpdateDTO.setCategoryId(1L);
        productUpdateDTO.setDescription("description1");
        productUpdateDTO.setImagePath("imagePath1");


        when(productRepository.existsById(1L)).thenReturn(true);
        when(productMapper.toEntity(productUpdateDTO)).thenReturn(product);
        when(productRepository.save(product)).thenThrow(new InvalidDataException("Please, check for duplicate entries"));

        assertThrows(InvalidDataException.class, () -> productService.updateProduct(productUpdateDTO));
    }

    @Test
    public void testDeleteProduct_WhenProductDoesNotExists_ShouldThrowException() {
        when(productRepository.findById(1L)).thenThrow(new DataNotFoundException("There is no product with id 1"));

        assertThrows(DataNotFoundException.class, () -> productService.deleteProduct(1L));
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
}