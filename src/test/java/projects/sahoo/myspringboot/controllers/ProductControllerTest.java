package projects.sahoo.myspringboot.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import projects.sahoo.myspringboot.models.entities.Product;
import projects.sahoo.myspringboot.services.api.ProductService;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    private static final String PRODUCT_NAME = "Product Name";

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /product/1 - Found")
    void testGetProductByIdFound() throws Exception{
        // Setup Mocked Service
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 1);
        Mockito.doReturn(Optional.of(mockProduct)).when(productService).findById(1);

        // Execute the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{1}", 1))

                // Validate the response code and content type
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the headers
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/product/1"))

                // Validate returned fields
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(PRODUCT_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version", Matchers.is(1)));
    }

    @Test
    @DisplayName("GET /product/1 - Not Found")
    void testGetProductByIdNotFound() throws Exception{
        // Setup Mocked Service
        Mockito.doReturn(Optional.empty()).when(productService).findById(1);

        // Execute the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{1}", 1))

                // Validate that we get 404 Not Found response
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /product - Success")
    void testCreateProductSuccess() throws Exception{
        // Setup Mocked Service
        Product postProduct = new Product(PRODUCT_NAME, 10);
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 1);
        Mockito.doReturn(mockProduct).when(productService).save(Mockito.any());

        // Execute the POST request
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Helper.asJsonString(postProduct)))

                // Validate the response code and content type
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the headers
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/product/1"))

                // Validate returned fields
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Product Name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version", Matchers.is(1)));
    }

    @Test
    @DisplayName("PUT /product/1 - Success")
    void testUpdateProductSuccess() throws Exception{
        // Setup Mocked Service
        Product putProduct = new Product(PRODUCT_NAME, 10);
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 1);
        Mockito.doReturn(Optional.of(mockProduct)).when(productService).findById(1);
        Mockito.doReturn(true).when(productService).update(Mockito.any());

        // Execute the PUT request
        mockMvc.perform(MockMvcRequestBuilders
                .put("/product/{id}", 1)
                .header(HttpHeaders.IF_MATCH, 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Helper.asJsonString(putProduct)))

                // Validate the response code and content type
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the headers
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ETAG, "\"2\""))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/product/1"))

                // Validate returned fields
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Product Name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version", Matchers.is(2)));
    }

    @Test
    @DisplayName("PUT /product/1 - Version Mismatch")
    void testUpdateProductVersionMismatch() throws Exception{
        // Setup Mocked Service
        Product putProduct = new Product(PRODUCT_NAME, 10);
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 2);
        Mockito.doReturn(Optional.of(mockProduct)).when(productService).findById(1);

        // Execute the PUT request
        mockMvc.perform(MockMvcRequestBuilders
                .put("/product/{id}", 1)
                .header(HttpHeaders.IF_MATCH, 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Helper.asJsonString(putProduct)))

                // Validate the response code and content type
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("PUT /product/1 - Not Found")
    void testUpdateProductNotFound() throws Exception{
        // Setup Mocked Service
        Product putProduct = new Product(PRODUCT_NAME, 10);
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 2);
        Mockito.doReturn(Optional.empty()).when(productService).findById(1);

        // Execute the PUT request
        mockMvc.perform(MockMvcRequestBuilders
                .put("/product/{id}", 1)
                .header(HttpHeaders.IF_MATCH, 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Helper.asJsonString(putProduct)))

                // Validate the response code and content type
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /product/1 - Success")
    void testProductDeleteSuccess() throws Exception {
        // Setup Mock Services
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 1);
        Mockito.doReturn(Optional.of(mockProduct)).when(productService).findById(1);
        Mockito.doReturn(true).when(productService).delete(Mockito.any());

        // Execute DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("DELETE /product/1 - Not Found")
    void testProductDeleteNotFound() throws Exception {
        // Setup Mock Service
        Mockito.doReturn(Optional.empty()).when(productService).findById(1);

        // Execute DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /product/1 - Failure")
    void testProductDeleteFailure() throws Exception {
        // Setup Mock Services
        Product mockProduct = new Product(1, PRODUCT_NAME, 10, 1);
        Mockito.doReturn(Optional.of(mockProduct)).when(productService).findById(1);
        Mockito.doReturn(false).when(productService).delete(Mockito.any());

        // Execute DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}