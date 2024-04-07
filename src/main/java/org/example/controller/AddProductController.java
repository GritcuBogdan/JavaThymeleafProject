package org.example.controller;


import org.example.domain.Part;
import org.example.domain.Product;
import org.example.service.PartService;
import org.example.service.PartServiceImpl;
import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 *
 */
@Controller
public class AddProductController {
    @Autowired
    private ApplicationContext context;
    private PartService partService;
    private List<Part> theParts;
    private static Product product1;
    private Product product;

    @GetMapping("/showFormAddProduct")
    public String showFormAddPart(Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        product = new Product();
        product1=product;
        theModel.addAttribute("product", product);

        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.findAll()){
            if(!product.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        theModel.addAttribute("assparts",product.getParts());
        return "productForm";
    }

    @PostMapping("/showFormAddProduct")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("product", product);

        if(bindingResult.hasErrors()){
            ProductService productService = context.getBean(ProductServiceImpl.class);
            Product product2 = new Product();
            try {
                product2 = productService.findById( product.getId());
            } catch (Exception e) {
                System.out.println("Error Message " + e.getMessage());
            }
            theModel.addAttribute("parts", partService.findAll());
            List<Part>availParts=new ArrayList<>();
            for(Part p: partService.findAll()){
                if(!product2.getParts().contains(p))availParts.add(p);
            }
            theModel.addAttribute("availparts",availParts);
            theModel.addAttribute("assparts",product2.getParts());
            return "productForm";
        }
        //       theModel.addAttribute("assparts", assparts);
        //       this.product=product;
//        product.getParts().addAll(assparts);
        else {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            if(product.getId()!=0) {
                Product product2 = repo.findById( product.getId());
                PartService partService1 = context.getBean(PartServiceImpl.class);
                if(product.getInv()- product2.getInv()>0) {
                    for (Part p : product2.getParts()) {
                        int inv = p.getInv();
                        p.setInv(inv - (product.getInv() - product2.getInv()));
                        partService1.save(p);
                    }
                }
            }
            else{
                product.setInv(0);
            }
            repo.save(product);
            return "confirmationaddproduct";
        }
    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") Long theId, Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product theProduct = repo.findById(theId);
        product1=theProduct;
        //    this.product=product;
        //set the employ as a model attibute to prepopulate the form
        theModel.addAttribute("product", theProduct);
        theModel.addAttribute("assparts",theProduct.getParts());
        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.findAll()){
            if(!theProduct.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        //send over to our form
        return "productForm";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") Long theId, Model theModel) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product2=productService.findById(theId);
        for(Part part:product2.getParts()){
            part.getProducts().remove(product2);
            partService.save(part);
        }
        product2.getParts().removeAll(product2.getParts());
        productService.save(product2);
        productService.deleteById(Math.toIntExact(theId));

        return "confirmationdeleteproduct";
    }

    public AddProductController(PartService partService) {
        this.partService = partService;
    }
// make the add and remove buttons work

    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int theID, Model theModel){
        //    theModel.addAttribute("product", product);
        //    Product product1=new Product();
        if (product1.getName()==null) {
            return "saveproductscreen";
        }
        else{
            product1.getParts().add(partService.findById(theID));
            partService.findById(theID).getProducts().add(product1);
            ProductService productService = context.getBean(ProductServiceImpl.class);
            productService.save(product1);
            partService.save(partService.findById(theID));
            theModel.addAttribute("product", product1);
            theModel.addAttribute("assparts",product1.getParts());
            List<Part>availParts=new ArrayList<>();
            for(Part p: partService.findAll()){
                if(!product1.getParts().contains(p))availParts.add(p);
            }
            theModel.addAttribute("availparts",availParts);
            return "productForm";}
        //        return "confirmationassocpart";
    }
    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int theID, Model theModel){
        theModel.addAttribute("product", product);
        //  Product product1=new Product();
        product1.getParts().remove(partService.findById(theID));
        partService.findById(theID).getProducts().remove(product1);
        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(product1);
        partService.save(partService.findById(theID));
        theModel.addAttribute("product", product1);
        theModel.addAttribute("assparts",product1.getParts());
        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.findAll()){
            if(!product1.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        return "productForm";
    }
}

