package com.hashicorp.hellovaultspring.controllers;

import java.util.List;

import com.hashicorp.hellovaultspring.database.ProducsTable;
import com.hashicorp.hellovaultspring.database.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

    @Autowired
    private ProducsTable table;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> products(Model model) {

        List<Product> products = table.findAll();

        if ( products == null || products.size() == 0 ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

}
