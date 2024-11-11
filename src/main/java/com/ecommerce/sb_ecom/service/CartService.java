package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.dto.CartDTO;
import com.ecommerce.sb_ecom.model.Cart;
import org.springframework.stereotype.Service;

public interface CartService {

    public CartDTO addProductToCart(Long productId, Integer quantity);

    Cart createCart(Long productId, Integer quantity);
}
