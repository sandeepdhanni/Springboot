package com.ml_service.service;

import com.ml_service.dto.MLProductsdto;
import com.ml_service.entity.MlProductsEntity;
import com.ml_service.repository.MlProductsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MLproductsServiceImpl implements MLproductservice{

    private final MlProductsRepository mlProductsRepository;

    public MLproductsServiceImpl(MlProductsRepository mlProductsRepository) {
        this.mlProductsRepository = mlProductsRepository;
    }

    @Override
    public String addProduct(MLProductsdto productDto, MultipartFile productImage) throws IOException {
       try {
           MlProductsEntity products = new MlProductsEntity();
           products.setProductName(productDto.getProductName());
           products.setPrice(productDto.getPrice());
           products.setQuantity(productDto.getQuantity());
           products.setProduct_image(productImage.getBytes());
           mlProductsRepository.save(products);
           return "product saved successfully";
       }catch (IOException e){
           throw new IOException("Error saving in product",e);
       }
    }

    @Override
    public List<MLProductsdto> getAllProducts() {
        List<MlProductsEntity> products = mlProductsRepository.findAll();
        return products.stream()
                .map(product -> new MLProductsdto(
                        product.getProduct_id(),
                        product.getProductName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getProduct_image()
                ))
                .collect(Collectors.toList());
    }
}
