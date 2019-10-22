package com.takeIt.service.product;

import com.takeIt.entity.Product;
import org.springframework.data.domain.Page;

import javax.swing.plaf.PanelUI;

public interface ProductService {
    Product store(Product product);

    Page<Product> getAll();

    Page<Product> filter();

    Product update(long id, Product product);

    Product getProduct(long id);

    boolean delete(long id);
}
