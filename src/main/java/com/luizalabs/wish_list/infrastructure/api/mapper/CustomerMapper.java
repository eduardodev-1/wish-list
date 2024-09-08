package com.luizalabs.wish_list.infrastructure.api.mapper;

import com.luizalabs.wish_list.application.dto.ProductDTO;
import com.luizalabs.wish_list.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO ProductDTO);
}
