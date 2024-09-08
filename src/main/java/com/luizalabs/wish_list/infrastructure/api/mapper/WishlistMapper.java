package com.luizalabs.wish_list.infrastructure.api.mapper;

import com.luizalabs.wish_list.application.dto.WishlistDTO;
import com.luizalabs.wish_list.domain.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WishlistMapper {
    WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);

    WishlistDTO toDTO(Wishlist wishlist);

    Wishlist toEntity(WishlistDTO wishlistDTO);
}
