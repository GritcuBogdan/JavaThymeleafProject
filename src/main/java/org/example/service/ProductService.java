package org.example.service;



import org.example.domain.Product;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface ProductService {
    public List<Product> findAll();
    public Product findById(Long theId);
    public void save (Product theProduct);
    public void deleteById(int theId);
    public List<Product> listAll(String keyword);
    public boolean buyNow(Long theId);

}

