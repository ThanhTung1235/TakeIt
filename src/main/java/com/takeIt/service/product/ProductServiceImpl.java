package com.takeIt.service.product;

import com.takeIt.entity.Product;
import com.takeIt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product store(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAll() {
        return null;
    }

    @Override
    public Page<Product> filter() {
        return null;
    }

    //update : ten, do tuoi, mo ta san pham, gioi tinh
    @Override
    public Product update(long id, Product product) {
//        product.setUpdatedAt(Calendar.getInstance().getTimeInMillis());
        Optional<Product> p = productRepository.findById(id);
        if (p.isPresent()) {
            Product productExist = p.get();
//            productExist.setName(product.getName());
////            productExist.setAddress(product.getAddress());
//            productExist.setDescription(product.getDescription());
//            productExist.setAge_range(product.getAge_range());
        }
        return null;
    }

    @Override
    public Product getProduct(long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

}
