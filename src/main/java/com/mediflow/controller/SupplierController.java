package com.mediflow.controller;

import com.mediflow.model.*;
import com.mediflow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired private SupplierService supplierService;

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("suppliers", supplierService.searchSuppliers(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("suppliers", supplierService.getAllSuppliers());
        }
        return "suppliers";
    }

    @GetMapping("/add")
    public String addForm() { return "supplier-form"; }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam String phone,
                      @RequestParam String email, @RequestParam String address,
                      @RequestParam String type,
                      @RequestParam(required = false) String extra1,
                      @RequestParam(required = false) String extra2,
                      @RequestParam(required = false) String extra3,
                      RedirectAttributes ra) {
        supplierService.addSupplier(name, phone, email, address, type, extra1, extra2, extra3);
        ra.addFlashAttribute("success", "Supplier added!");
        return "redirect:/suppliers";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        Supplier s = supplierService.findById(id);
        if (s == null) return "redirect:/suppliers";
        model.addAttribute("supplier", s);
        model.addAttribute("editing", true);
        model.addAttribute("isLocal", s instanceof LocalSupplier);
        return "supplier-form";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        supplierService.deleteSupplier(id);
        ra.addFlashAttribute("success", "Supplier deleted!");
        return "redirect:/suppliers";
    }
}
