package projects.sahoo.myspringboot.services.impl;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import projects.sahoo.myspringboot.models.entities.Product;
import projects.sahoo.myspringboot.repositories.ProductRepository;
import projects.sahoo.myspringboot.services.api.ProductService;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {
    private static final String PRODUCT_NAME = "Product Name";

    // Service that we want to test
    @Autowired
    private ProductService productService;

    // Mock version of productRepository
    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("Test findById Success")
    void testFindProductByIdSuccess(){
        // Mock setup
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 1);
        Mockito.doReturn(Optional.of(mockProduct)).when(productRepository).findById(1);

        // Execute the service call
        Optional<Product> returnedProduct = productService.findById(1);

        // Assert the response
        Assertions.assertTrue(returnedProduct.isPresent(), "Product was not found");
        Assertions.assertSame(mockProduct, returnedProduct.get(), "Products should be same");
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindProductByIdNotFound(){
        // Mock setup
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 1);
        Mockito.doReturn(Optional.empty()).when(productRepository).findById(1);

        // Execute the service call
        Optional<Product> returnedProduct = productService.findById(1);

        // Assert the response
        Assertions.assertFalse(returnedProduct.isPresent(), "Product was found, when it shouldn't");
    }

    @Test
    @DisplayName("Test findAll Success")
    void testFindAllSuccess(){
        // Mock Setup
        Product product1 = new Product(1, PRODUCT_NAME, 10, 1);
        Product product2 = new Product(2, PRODUCT_NAME, 10, 1);
        Mockito.doReturn(Lists.newArrayList(product1, product2)).when(productRepository).findAll();

        // Execute the service call
        List<Product> returnedProductList = productService.findAll();

        // Assert the response
        Assertions.assertEquals(2, returnedProductList.size(), "findAll should return 2 products");
    }

    @Test
    //@DisplayName("Test save Success")
    void testSaveSuccess() {
        // Mock setup
        Product mockProduct = new Product(1, PRODUCT_NAME, 10);
        Mockito.doReturn(mockProduct).when(productRepository).save(Mockito.any());

        // Execute the service call
        Product returnedProduct = productService.save(mockProduct);

        // Assert the response
        Assertions.assertNotNull(returnedProduct, "The saved product shouldn't be null");
        Assertions.assertEquals(1, returnedProduct.getVersion(), "Version for a new product should be 1");
    }
}