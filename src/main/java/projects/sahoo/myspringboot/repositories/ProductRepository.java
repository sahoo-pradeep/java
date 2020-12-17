package projects.sahoo.myspringboot.repositories;

import java.util.List;
import java.util.Optional;
import projects.sahoo.myspringboot.models.entities.Product;

public interface ProductRepository {

  Optional<Product> findById(Integer id);

  List<Product> findAll();

  Product save(Product product);

  boolean update(Product product);

  boolean delete(Integer id);
}
