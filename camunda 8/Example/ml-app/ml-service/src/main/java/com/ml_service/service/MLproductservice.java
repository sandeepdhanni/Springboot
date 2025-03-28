package com.ml_service.service;

import com.ml_service.dto.MLProductsdto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MLproductservice {

    String addProduct(MLProductsdto productDto, MultipartFile productImage) throws IOException;

    List<MLProductsdto> getAllProducts();
}
