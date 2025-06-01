package backend.Service.impl;

import backend.Service.ProductService;
import backend.entity.Product;
import backend.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> getProducts() {
        return productMapper.getProducts();
    }
}
