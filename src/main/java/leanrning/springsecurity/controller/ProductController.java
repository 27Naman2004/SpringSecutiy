package leanrning.springsecurity.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private record Product(Integer id, String name, double price) {

    }
    List<Product> products = new ArrayList<>(
            List.of(
                    new Product(1, "Iphone", 23.3),
                    new Product(2, "Macbook", 12.34)
            )
    );
    @GetMapping
    public List<Product> getProducts() {
        return products;
    }
    @PostMapping
    public Product getProduct(@RequestBody Product product) {
        products.add(product);
        return product;
    }

}
