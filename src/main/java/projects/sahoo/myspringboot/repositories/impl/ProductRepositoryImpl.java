package projects.sahoo.myspringboot.repositories.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import projects.sahoo.myspringboot.models.entities.Product;
import projects.sahoo.myspringboot.repositories.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private static final Logger log = LoggerFactory.getLogger(ProductRepositoryImpl.class);

  private final JdbcTemplate jdbcTemplate;
  private final SimpleJdbcInsert simpleJdbcInsert;

  public ProductRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
    this.jdbcTemplate = jdbcTemplate;

    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
        .withTableName("products")
        .usingGeneratedKeyColumns("id");
  }

  @Override
  public Optional<Product> findById(Integer id) {
    try {
      Product product = jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?",
          new Object[]{id},
          (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setQuantity(rs.getInt("quantity"));
            p.setVersion(rs.getInt("version"));
            return p;
          });
      return Optional.of(product);
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Product> findAll() {
    return jdbcTemplate.query("SELECT * FROM products",
        (rs, rowNum) -> {
          Product p = new Product();
          p.setId(rs.getInt("id"));
          p.setName(rs.getString("name"));
          p.setQuantity(rs.getInt("quantity"));
          p.setVersion(rs.getInt("version"));
          return p;
        });
  }

  @Override
  public Product save(Product product) {
    // Build the product parameters
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("name", product.getName());
    paramMap.put("quantity", product.getQuantity());
    paramMap.put("version", product.getVersion());

    // Execute the query and get the generated key
    Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
    log.info("Insert product into database. Generated key is: {}", newId);

    // Update the product id with new key
    product.setId(newId.intValue());

    //return the complete product
    return product;
  }

  @Override
  public boolean update(Product product) {
    int result = jdbcTemplate
        .update("UPDATE products SET name = ?, quantity = ?, version = ? WHERE id = ?",
            product.getName(),
            product.getQuantity(),
            product.getVersion(),
            product.getId());

    return result == 1;
  }

  @Override
  public boolean delete(Integer id) {
    int result = jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    return result == 1;
  }
}
