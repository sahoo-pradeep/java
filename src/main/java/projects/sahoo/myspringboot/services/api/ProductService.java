package projects.sahoo.myspringboot.services.api;

import projects.sahoo.myspringboot.models.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    /**
     * Return the product with specified ID
     *
     * @param id ID of product to retrieve
     * @return The requested product if found
     */
    Optional<Product> findById(Integer id);

    /**
     * Return all products in database
     *
     * @return w
     */
    List<Product> findAll();

    /**
     * Saves the specified product to database
     *
     * @param product The product to save in database
     * @return Saved product
     */
    Product save(Product product);

    /**
     * Update the specified product to database
     *
     * @param product The product to update
     * @return Returns true if update succeeded, else false
     */
    boolean update(Product product);

    /**
     * Deletes a product with specified ID
     *
     * @param id ID of product to delete
     * @return Returns true if product is deleted successfully, else false
     */
    boolean delete(Integer id);
}
