package org.example.repositories;


import org.example.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 *
 *
 *
 *
 */
public interface ProductRepository extends CrudRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public List<Product> search(String keyword);

    @Query("SELECT p FROM Product p WHERE p.id = :theId")
    public Product findOneById(Long theId);

}
