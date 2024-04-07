package org.example.controller;


import org.example.domain.InhousePart;
import org.example.domain.Part;
import org.example.domain.Product;
import org.example.service.PartService;
import org.example.service.ProductService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 *
 *
 *
 */

@Controller
public class MainScreenControllerr {
    // private final PartRepository partRepository;
    // private final ProductRepository productRepository;'

    private PartService partService;
    private ProductService productService;

    private List<Part> theParts;
    private List<Product> theProducts;

 /*   public MainScreenControllerr(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }*/

    public MainScreenControllerr(PartService partService,ProductService productService){
        this.partService=partService;
        this.productService=productService;
    }
    @GetMapping("/mainscreen")
    public String listPartsandProducts(Model theModel, @Param("partkeyword") String partkeyword, @Param("productkeyword") String productkeyword){
        //add to the sprig modelz
        List<Part> partList=partService.listAll(partkeyword);
        if(partList.size() > 5) {
            theModel.addAttribute("parts", partList);
            theModel.addAttribute("partkeyword", partkeyword);
        } else{
            InhousePart newPart = new InhousePart();
            newPart.setName("CPU");
            newPart.setInv(3);
            newPart.setPrice(4.5);
            newPart.setMin(3);
            newPart.setMax(7);
            InhousePart newPart2 = new InhousePart();
            newPart2 .setName("MotherBoard");
            newPart2 .setInv(4);
            newPart2 .setPrice(2.0);
            newPart2.setMin(3);
            newPart2.setMax(8);
            InhousePart newPart3 = new InhousePart();
            newPart3.setName("RAM");
            newPart3.setInv(5);
            newPart3.setPrice(5.5);
            newPart3.setMin(3);
            newPart3.setMax(9);
            InhousePart newPart4 = new InhousePart();
            newPart4.setName("SSD");
            newPart4.setInv(7);
            newPart4.setPrice(6.5);
            newPart4.setMin(3);
            newPart4.setMax(10);
            InhousePart newPart5 = new InhousePart();
            newPart5.setName("PSU");
            newPart5.setInv(6);
            newPart5.setPrice(6.5);
            newPart5.setMin(3);
            newPart5.setMax(11);
            List<Part> newParts = new ArrayList<>();
            newParts.add(newPart);
            newParts.add(newPart2);
            newParts.add(newPart3);
            newParts.add(newPart4);
            newParts.add(newPart5);

            theModel.addAttribute("parts", newParts);
            theModel.addAttribute("partkeyword", partkeyword);


        }
        //    theModel.addAttribute("products",productService.findAll());

        List<Product> productList=productService.listAll(productkeyword);
        if(productList.size() >= 5) {
            theModel.addAttribute("products", productList);
            theModel.addAttribute("productkeyword", productkeyword);
            return "mainscreen";
        } else{
            Product newProduct = new Product();
            newProduct.setName("Fan Cleaning Kit");
            newProduct.setInv(3);
            newProduct.setPrice(4.5);

            Product newProduct2 = new Product();
            newProduct2.setName("Cpu Motherboard Combo");
            newProduct2.setInv(2);
            newProduct2.setPrice(2.0);

            Product newProduct3 = new Product();
            newProduct3.setName("Modular Power Supply Parts");
            newProduct3.setInv(1);
            newProduct3.setPrice(5.5);

            Product newProduct4 = new Product();
            newProduct4.setName("Bench Testing");
            newProduct4.setInv(7);
            newProduct4.setPrice(6.5);

            Product newProduct5 = new Product();
            newProduct5.setName("Diagnostics");
            newProduct5.setInv(6);
            newProduct5.setPrice(6.5);
            List<Product> newProducts = new ArrayList<>();
            newProducts.add(newProduct);
            newProducts.add(newProduct2);
            newProducts.add(newProduct3);
            newProducts.add(newProduct4);
            newProducts.add(newProduct5);
            theModel.addAttribute("products", newProducts);
            theModel.addAttribute("productkeyword", productkeyword);

            return "mainscreen";
        }
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }
//
    @GetMapping("/buyPage")
    public String handleBuyResponse(@RequestParam("productID") Long product){
        if(productService.buyNow(product)){
            return "success";
        } else{
            return "failure";
        }
    }
}

