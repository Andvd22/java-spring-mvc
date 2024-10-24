package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product handelSaveProduct(Product product) {
        Product anric = this.productRepository.save(product);
        return anric;
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }
}
