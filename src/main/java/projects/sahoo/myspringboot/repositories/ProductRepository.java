package projects.sahoo.myspringboot.repositories;

import projects.sahoo.myspringboot.models.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Integer id);

    List<Product> findAll();

    Product save(Product product);

    boolean update(Product product);

    boolean delete(Integer id);
}
