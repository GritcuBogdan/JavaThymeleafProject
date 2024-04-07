package org.example.controller;

import org.example.domain.Product;
import org.example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BuyController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/buyPage")
    public String buyProduct(@RequestParam("productId") Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        // Check if the product exists in the database
        if (!optionalProduct.isPresent()) {
            // Handle product not found scenario (e.g., redirect to error page)
            return "redirect:/purchaseError?message=Product+not+found";
        }

        Product product = optionalProduct.get();

        if (optionalProduct.isPresent()) {
            if (product.getInv() > 0) {
                product.setInv(product.getInv() - 1);
                productRepository.save(product);
                return "success";
            } else {
                return "failure";
            }
        } else {
            return "failure";
        }
    }

    @GetMapping("/success")
    public String displayPurchaseSuccess() {
        return "success";
    }

    @GetMapping("/failure")
    public String displayPurchaseError() {
        return "success";
    }
}
