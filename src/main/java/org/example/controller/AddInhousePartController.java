package org.example.controller;

import org.example.domain.InhousePart;
import org.example.domain.Part;
import org.example.domain.Product;
import org.example.service.InhousePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AddInhousePartController {

    private final InhousePartService inhousePartService;

    @Autowired
    public AddInhousePartController(InhousePartService inhousePartService) {
        this.inhousePartService = inhousePartService;
    }

    @GetMapping("/showFormAddInPart")
    public String showFormAddInhousePart(Model theModel) {
        theModel.addAttribute("inhousepart", new InhousePart());
        return "InhousePartForm";
    }

    @PostMapping("/showFormAddInPart")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part,
                             BindingResult theBindingResult,
                             Model theModel) {
        if (theBindingResult.hasErrors() || !part.isInvValid()) {
            if (!part.isInvValid()) {
                theBindingResult.rejectValue("inv", "inventory.invalid", "Inventory must be between Min and Max.");
            }
            return "InhousePartForm";
        } else {
            InhousePart existingPart = inhousePartService.findById(Math.toIntExact(part.getId()));
            if (existingPart != null) {
                part.setProducts(existingPart.getProducts());
            }
            inhousePartService.save(part);
            return "redirect:/confirmation";
        }
    }


    private boolean checkAssociatedPartInventory(InhousePart part) {
        for (Product product : part.getProducts()) {
            for (Part associatedPart : product.getParts()) {
                if (associatedPart.getInv() - 1 < associatedPart.getMin()) {
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage() {
        return "confirmation";
    }

    @GetMapping("/")
    public String mainPage() {
        return "mainScreen";
    }
}
