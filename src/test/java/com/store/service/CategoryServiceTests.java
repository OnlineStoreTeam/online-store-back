package com.store.service;

import com.store.dto.categoryDTOs.CategoryCreateDTO;
import com.store.dto.categoryDTOs.CategoryDTO;
import com.store.dto.categoryDTOs.CategoryUpdateDTO;
import com.store.entity.Category;
import com.store.exception.DataNotFoundException;
import com.store.exception.InvalidDataException;
import com.store.mapper.CategoryMapper;
import com.store.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testGetAllCategories() {
        List<Category> categoryList = createCategoryList();

        List<CategoryDTO> expectedCategoryDTOList = createCategoryDTOList();

        Page<Category> categoryPage = new PageImpl<>(categoryList);

        when(categoryRepository.findAll(PageRequest.of(0, 10))).thenReturn(categoryPage);
        when(categoryMapper.toDto(any(Category.class))).thenAnswer(invocationOnMock -> {
            Category category = invocationOnMock.getArgument(0);
            Long id = category.getId();
            return expectedCategoryDTOList.stream()
                    .filter(dto -> dto.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        });

        Page<CategoryDTO> result = categoryService.getAllCategories(PageRequest.of(0, 10));

        verify(categoryRepository).findAll(PageRequest.of(0, 10));
        verify(categoryMapper, times(2)).toDto(any(Category.class));

        assertEquals(expectedCategoryDTOList.size(), result.getContent().size());

        assertEquals(expectedCategoryDTOList.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(expectedCategoryDTOList.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(expectedCategoryDTOList.get(0).getTitle(), result.getContent().get(0).getTitle());
        assertEquals(expectedCategoryDTOList.get(0).getPath(), result.getContent().get(0).getPath());
        assertEquals(expectedCategoryDTOList.get(0).getProductCount(), result.getContent().get(0).getProductCount());

        assertEquals(expectedCategoryDTOList.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(expectedCategoryDTOList.get(1).getName(), result.getContent().get(1).getName());
        assertEquals(expectedCategoryDTOList.get(1).getTitle(), result.getContent().get(1).getTitle());
        assertEquals(expectedCategoryDTOList.get(1).getPath(), result.getContent().get(1).getPath());
        assertEquals(expectedCategoryDTOList.get(1).getProductCount(), result.getContent().get(1).getProductCount());
    }

    @Test
    public void testGetOneCategoryById() {
        Category category = createCategoryList().get(0);
        CategoryDTO expectedCategoryDTO = createCategoryDTOList().get(0);

        when(categoryRepository.findById(category.getId())).thenReturn(java.util.Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expectedCategoryDTO);

        CategoryDTO result = categoryService.getCategoryById(category.getId());

        verify(categoryRepository).findById(category.getId());
        verify(categoryMapper).toDto(category);

        assertEquals(expectedCategoryDTO.getId(), result.getId());
        assertEquals(expectedCategoryDTO.getName(), result.getName());
        assertEquals(expectedCategoryDTO.getTitle(), result.getTitle());
        assertEquals(expectedCategoryDTO.getPath(), result.getPath());
        assertEquals(expectedCategoryDTO.getProductCount(), result.getProductCount());
    }

    @Test
    public void testCreateCategory() {
        Category category = createCategoryList().get(0);
        CategoryDTO expectedCategoryDTO = createCategoryDTOList().get(0);

        CategoryCreateDTO categoryCreateDTO = new CategoryCreateDTO();
        categoryCreateDTO.setName(category.getName());
        categoryCreateDTO.setPath(category.getPath());
        categoryCreateDTO.setTitle(category.getTitle());

        when(categoryMapper.toEntity(categoryCreateDTO)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expectedCategoryDTO);

        CategoryDTO result = categoryService.createCategory(categoryCreateDTO);

        verify(categoryMapper).toEntity(categoryCreateDTO);
        verify(categoryRepository).save(any(Category.class));
        verify(categoryMapper).toDto(category);

        assertEquals(expectedCategoryDTO.getId(), result.getId());
        assertEquals(expectedCategoryDTO.getName(), result.getName());
        assertEquals(expectedCategoryDTO.getTitle(), result.getTitle());
        assertEquals(expectedCategoryDTO.getProductCount(), result.getProductCount());
        assertEquals(expectedCategoryDTO.getPath(), result.getPath());
    }

    @Test
    public void testUpdateCategory() {
        Category category = createCategoryList().get(0);
        CategoryDTO expectedCategoryDTO = createCategoryDTOList().get(0);
        expectedCategoryDTO.setName("newName");

        CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO();
        categoryUpdateDTO.setId(category.getId());
        categoryUpdateDTO.setName("newName");
        categoryUpdateDTO.setPath(category.getPath());
        categoryUpdateDTO.setTitle(category.getTitle());

        when(categoryRepository.existsById(categoryUpdateDTO.getId())).thenReturn(true);
        when(categoryMapper.toEntity(categoryUpdateDTO)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expectedCategoryDTO);

        CategoryDTO result = categoryService.updateCategory(categoryUpdateDTO);

        verify(categoryRepository).existsById(categoryUpdateDTO.getId());
        verify(categoryMapper).toEntity(categoryUpdateDTO);
        verify(categoryRepository).save(any(Category.class));
        verify(categoryMapper).toDto(category);

        assertEquals(expectedCategoryDTO.getId(), result.getId());
        assertEquals(expectedCategoryDTO.getName(), result.getName());
        assertEquals(expectedCategoryDTO.getPath(), result.getPath());
        assertEquals(expectedCategoryDTO.getTitle(), result.getTitle());
        assertEquals(expectedCategoryDTO.getProductCount(), result.getProductCount());
    }

    @Test
    public void testDeleteCategory() {
        Category category = createCategoryList().get(0);

        when(categoryRepository.existsById(category.getId())).thenReturn(true);
        categoryService.deleteCategory(category.getId());

        verify(categoryRepository).deleteById(category.getId());
    }

    @Test
    public void testGetCategory_WhenCategoryDoesNotExists_ShouldThrowException() {
        Long categoryId = 3L;

        when(categoryRepository.findById(categoryId)).thenThrow(
                new DataNotFoundException("There is no category with id " + categoryId));

        assertThrows(DataNotFoundException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @Test
    public void testCreateCategory_WhenCategoryAlreadyExists_ShouldThrowException() {
        Category category = createCategoryList().get(0);

        CategoryCreateDTO categoryCreateDTO = new CategoryCreateDTO();
        categoryCreateDTO.setName(category.getName());
        categoryCreateDTO.setPath(category.getPath());
        categoryCreateDTO.setTitle(category.getTitle());

        when(categoryMapper.toEntity(categoryCreateDTO)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenThrow(
                new InvalidDataException("Please, check for duplicate entries"));

        assertThrows(InvalidDataException.class, () -> categoryService.createCategory(categoryCreateDTO));
    }

    @Test
    public void testUpdateCategory_WhenCategoryDoesNotExists_ShouldThrowException() {
        Category category = createCategoryList().get(0);

        CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO();
        categoryUpdateDTO.setId(category.getId());
        categoryUpdateDTO.setName(category.getName());
        categoryUpdateDTO.setPath(category.getPath());
        categoryUpdateDTO.setTitle(category.getTitle());

        when(categoryRepository.existsById(categoryUpdateDTO.getId())).thenThrow(new DataNotFoundException("There is no category with id" + category.getId()));

        assertThrows(DataNotFoundException.class, () -> categoryService.updateCategory(categoryUpdateDTO));
    }

    @Test
    public void testUpdateCategory_WhenCategoryAlreadyExists_ShouldThrowException() {
        Category category = createCategoryList().get(0);

        CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO();
        categoryUpdateDTO.setId(category.getId());
        categoryUpdateDTO.setName(category.getName());
        categoryUpdateDTO.setPath(category.getPath());
        categoryUpdateDTO.setTitle(category.getTitle());

        when(categoryRepository.existsById(categoryUpdateDTO.getId())).thenReturn(true);
        when(categoryMapper.toEntity(categoryUpdateDTO)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenThrow(
                new InvalidDataException("Please, check for duplicate entries"));

        assertThrows(InvalidDataException.class, () -> categoryService.updateCategory(categoryUpdateDTO));
    }

    @Test
    public void testDeleteCategory_WhenCategoryDoesNotExists_ShouldThrowException() {
        Long categoryId = 3L;

        when(categoryRepository.existsById(categoryId)).thenThrow(
                new DataNotFoundException("There is no category with id " + categoryId));

        assertThrows(DataNotFoundException.class, () -> categoryService.deleteCategory(categoryId));
    }

    @Test
    public void testDeleteCategory_WhenItemsPresentInCategory_ShouldThrowException() {
        Category category = createCategoryList().get(0);

        when(categoryRepository.existsById(category.getId())).thenReturn(true);
        when(categoryRepository.countItemsByCategory(category.getId())).thenReturn(1L);

        assertThrows(DataNotFoundException.class, () -> categoryService.deleteCategory(category.getId()));
    }

    private List<Category> createCategoryList() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("category1");
        category1.setTitle("title1");
        category1.setPath("path1");
        category1.setProductCount(1L);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("category2");
        category2.setTitle("title2");
        category2.setPath("path2");
        category2.setProductCount(2L);

        return List.of(category1, category2);
    }

    private List<CategoryDTO> createCategoryDTOList() {
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName("category1");
        categoryDTO1.setTitle("title1");
        categoryDTO1.setPath("path1");
        categoryDTO1.setProductCount(1L);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("category2");
        categoryDTO2.setTitle("title2");
        categoryDTO2.setPath("path2");
        categoryDTO2.setProductCount(2L);

        return List.of(categoryDTO1, categoryDTO2);
    }
}