package com.luizalabs.wish_list.infrastructure.api.mapper;

import com.luizalabs.wish_list.application.dto.CustomerDTO;
import com.luizalabs.wish_list.domain.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer product);

    Customer toEntity(CustomerDTO CustomerDTO);
}
