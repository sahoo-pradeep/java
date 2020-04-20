package projects.sahoo.myspringboot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.sahoo.myspringboot.models.entities.Product;
import projects.sahoo.myspringboot.repositories.ProductRepository;
import projects.sahoo.myspringboot.services.api.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        product.setVersion(1);
        return productRepository.save(product);
    }

    public boolean update(Product product) {
        return productRepository.update(product);
    }

    public boolean delete(Integer id) {
        return productRepository.delete(id);
    }
}
