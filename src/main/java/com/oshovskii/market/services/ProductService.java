package com.oshovskii.market.services;

import com.oshovskii.market.dto.ProductDto;
import com.oshovskii.market.model.Product;
import com.oshovskii.market.repositories.ProductRepository;
import com.oshovskii.market.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MappingUtils mappingUtils;


    public ProductDto findProductById(Long id) {
        return mappingUtils.mapToProductDto( //в метод mapToProductDto
                productRepository.findById(id) //поместили результат поиска по id
                        .orElse(new Product()) //если ни чего не нашли, то вернем пустой entity(product)
        );
    }

//    public List<Product> findAll() {
//        return productRepository.findAll();
//    }

    public List<ProductDto> findAll() {
      //  PageImpl
        return productRepository.findAll().stream().map(mappingUtils::mapToProductDto).collect(Collectors.toList());
    }

    public Page<ProductDto> findAll(int page) {
        Page<Product> originalPage = productRepository.findAll(PageRequest.of(page - 1, 5));
        return new PageImpl<>(originalPage.getContent().stream().map(mappingUtils::mapToProductDto).collect(Collectors.toList()), originalPage.getPageable(), originalPage.getTotalElements());

    }

    public void deleteById(Long id) { productRepository.deleteById(id);}

    public ProductDto saveOrUpdate(ProductDto productDto) {
        return mappingUtils.mapToProductDto(productRepository.save(mappingUtils.mapToProductEntity(productDto)));
    }
}
