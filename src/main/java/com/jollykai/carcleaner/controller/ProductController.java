package com.jollykai.carcleaner.controller;

import com.jollykai.carcleaner.entity.Product;
import com.jollykai.carcleaner.repository.ProductRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
@RestController
@RequestMapping()
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ApiOperation(value = "Show list of all existed products")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved")
    })
    @GetMapping("products")
    public Iterable<Product> showProductList() {
        return productRepository.findAll();
    }

    @ApiOperation(value = "Uses name and price to add new product", notes = "Available only for authenticated manager")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product successfully added")
    })
    @PostMapping("product")
    public ResponseEntity<?> addProduct(
            @RequestParam
            @ApiParam(name = "name", value = "Product name", example = "Lux washing", required = true) String name,
            @RequestParam
            @ApiParam(name = "price", value = "Product price", example = "1000", required = true) int price) {
        productRepository.save(new Product(name, price));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete product by Id", notes = "Available only for authenticated manager")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product successfully deleted"),
            @ApiResponse(code = 204, message = "Product with this id not exist")
    })
    @DeleteMapping("product")
    public ResponseEntity<?> deleteProduct(
            @RequestParam @ApiParam(name = "id", value = "Product id", example = "1", required = true) Long id) {
        if (!productRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
