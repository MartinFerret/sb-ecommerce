package com.ecommerce.sb_ecom.service.order;

import com.ecommerce.sb_ecom.dto.OrderDTO;
import com.ecommerce.sb_ecom.dto.OrderItemDTO;
import com.ecommerce.sb_ecom.exceptions.ApiException;
import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.*;
import com.ecommerce.sb_ecom.repository.*;
import com.ecommerce.sb_ecom.service.cart.CartService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public OrderServiceImpl(CartRepository cartRepository, AddressRepository addressRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository, PaymentRepository paymentRepository, CartService cartService, ModelMapper modelMapper, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {
        Cart cart = cartRepository.findCartByEmail(emailId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "id", emailId);
        }

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        Order order = new Order();
        order.setEmail(emailId);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order accepted");
        order.setAddress(address);

        Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
        payment.setOrder(order);
        payment = paymentRepository.save(payment);
        order.setPayment(payment);
        Order savedOrder = orderRepository.save(order);

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new ApiException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
            orderItems.add(orderItem);
        }

        orderItems = orderItemRepository.saveAll(orderItems);
        cart.getCartItems().forEach(cartItem -> {
            int quantity = cartItem.getQuantity();
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
            cartService.deleteProductFromCart(cart.getCartId(), cartItem.getProduct().getProductId());
        });
        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        orderItems.forEach(orderItem -> { orderDTO.getOrderItems().add(modelMapper.map(orderItem, OrderItemDTO.class));});
        orderDTO.setAddressId(addressId);

        return orderDTO;
    }
}
