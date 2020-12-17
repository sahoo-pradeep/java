package projects.sahoo.myspringboot.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import projects.sahoo.myspringboot.models.entities.Product;
import projects.sahoo.myspringboot.services.api.ProductService;

/**
 * The type Product controller.
 */
@RestController
public class ProductController {

  private static final Logger log = LoggerFactory.getLogger(ProductController.class);
  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Returns the product with the specified ID.
   *
   * @param id The ID of product to retrieve
   * @return The product with specified ID
   */
  @GetMapping("/product/{id}")
  public ResponseEntity<?> getProduct(@PathVariable Integer id) {
    return productService.findById(id).map(product -> {
      try {
        return ResponseEntity
            .ok()
            .eTag(Integer.toString(product.getVersion()))
            .location(new URI("/product/" + product.getId()))
            .body(product);
      } catch (URISyntaxException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }).orElse(ResponseEntity.notFound().build());
  }

  /**
   * Returns all products in database
   *
   * @return All products in database
   */
  @GetMapping("/products")
  public Iterable<Product> getProducts() {
    return productService.findAll();
  }

  /**
   * Creates a new Product
   *
   * @param product The product to create
   * @return Product created
   */
  @PostMapping("/product")
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    log.info("Creating product with name = {}, quantity = {}",
        product.getName(), product.getQuantity());
    Product newProduct = productService.save(product);
    try {
      return ResponseEntity
          .created(new URI("/product/" + newProduct.getId()))
          .eTag(Integer.toString(newProduct.getVersion()))
          .body(newProduct);
    } catch (URISyntaxException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Updates the fields in the specified product with the specified ID
   *
   * @param product The product field values to update
   * @param id      The ID of product to update
   * @param ifMatch The eTag version of the product
   * @return A ResponseBody that contains the updated Product or one of the error: Conflict if
   * product version doesn't match with eTag Not Found if the product with ID is not found in
   * database Internal Server Error if there is problem creating location URI
   */
  @PutMapping("/product/{id}")
  public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Integer id,
      @RequestHeader("If-Match") Integer ifMatch) {
    log.info("Updating product with id: {}, name: {}, quantity: {}",
        id, product.getName(), product.getQuantity());

    // Get the existing product
    Optional<Product> existingProduct = productService.findById(id);
    return existingProduct.map(p -> {
      // Compare eTag
      log.info("Product with ID {} has a version of {}. Update is for version {}.",
          id, p.getVersion(), ifMatch);
      if (!p.getVersion().equals(ifMatch)) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      }

      p.setName(product.getName());
      p.setQuantity(product.getQuantity());
      p.setVersion(p.getVersion() + 1);

      log.info("Updating product with ID: {} -> name = {}, quantity = {}, version = " + "{}",
          id, p.getName(), p.getQuantity(), p.getVersion());

      try {
        // Update the product and return an ok response
        if (productService.update(p)) {
          return ResponseEntity
              .ok()
              .location(new URI("/product/" + p.getId()))
              .eTag(Integer.toString(p.getVersion()))
              .body(p);
        } else {
          return ResponseEntity.notFound().build();
        }
      } catch (URISyntaxException e) {
        // An error occurred trying to create the location URI, return an error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }

    }).orElse(ResponseEntity.notFound().build());
  }

  /**
   * Delete the product with specified ID.
   *
   * @param id ID of the product to delete
   * @return A ResponseBody with one of the following status codes: 200 OK if the delete is
   * successful 404 Not Found if a product with specified ID is not found 500 Internal Server Error
   * if an error occurs during deletion
   */
  @DeleteMapping("/product/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
    Optional<Product> existingProduct = productService.findById(id);

    return existingProduct.map(p -> {
      if (productService.delete(id)) {
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }).orElse(ResponseEntity.notFound().build());
  }
}
