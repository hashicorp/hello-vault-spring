/*
 * Copyright (c) HashiCorp, Inc.
 * SPDX-License-Identifier: MPL-2.0
 */

package com.hashicorp.hellovaultspring.controllers;

import java.util.List;

import com.hashicorp.hellovaultspring.database.ProductsTable;
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
    private ProductsTable table;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> products() {

        List<Product> products = table.findAll();

        if ( products == null || products.size() == 0 ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
