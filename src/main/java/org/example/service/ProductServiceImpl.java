package org.example.service;

import org.example.domain.Product;
import org.example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(Long theId) {
        Optional<Product> result = productRepository.findById((long) theId);
        return result.orElseThrow(() -> new RuntimeException("Did not find product with id: " + theId));
    }

    @Override
    public void save(Product theProduct) {
        productRepository.save(theProduct);
    }

    @Override
    public void deleteById(int theId) {
        productRepository.deleteById((long) theId);
    }

    @Override
    public List<Product> listAll(String keyword) {
        if (keyword != null) {
            return productRepository.search(keyword);
        }
        return (List<Product>)productRepository.findAll();
    }

    @Transactional
    @Override
    public boolean buyNow(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int currentInventory = product.getInv();
            if (currentInventory > 0) {
                product.setInv(currentInventory - 1);
                productRepository.save(product);
                return true;
            }
        }
        return false; // Product not found or inventory is already 0
    }
}
