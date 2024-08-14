package com.backend.webproject.service;

import com.backend.webproject.model.Products;
import com.backend.webproject.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepo repo;

    public List<Products> getProducts() {
        System.out.println(repo.findAll());
        return repo.findAll();
    }

    public Products getProductsById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Products addProduct(Products product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }

    public Products updateProduct(int id, Products prod, MultipartFile imageFile) throws IOException {
        prod.setImageDate(imageFile.getBytes());
        prod.setImageType(imageFile.getContentType());
        prod.setImageName(imageFile.getOriginalFilename());
        return repo.save(prod);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Products> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
