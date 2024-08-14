package com.backend.webproject.controller;


import com.backend.webproject.model.Products;
import com.backend.webproject.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductsController {

    @Autowired
    ProductsService service;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getProducts(){
        List<Products> products=service.getProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Products> getProductsByIds(@PathVariable  int id){
        Products prod=service.getProductsById(id);
        if(prod!=null){
            return new ResponseEntity<>(prod,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestPart Products product , @RequestPart MultipartFile imageFile){
        try{
            Products prod=service.addProduct(product,imageFile);
            return new ResponseEntity<>(prod,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}/image")
    public ResponseEntity<byte[]> getImageProductById(@PathVariable int id){
        Products product = service.getProductsById(id);
        byte[] imageFile=product.getImageDate();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Products product,
                                                @RequestPart MultipartFile imageFile){
        Products product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
        if(product1 != null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Products product=service.getProductsById(id);
        if(product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product Not Found!",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Products>> searchProducts(@RequestParam String keyword){
        List<Products> products = service.searchProducts(keyword);
        System.out.println("searching with " + keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}