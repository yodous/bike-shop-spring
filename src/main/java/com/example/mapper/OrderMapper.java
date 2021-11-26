package com.example.mapper;

import com.example.dto.OrderRequest;
import com.example.exception.ProductNotFoundException;
import com.example.model.OrderDetails;
import com.example.model.OrderItem;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.OrderDetailsRepository;
import com.example.repository.ProductRepository;
import com.example.service.AuthService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public OrderItem mapOrderRequestToOrderItem(OrderRequest request) {
        User currentUser = authService.getCurrentUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));
        OrderDetails orderDetails = orderDetailsRepository.save(new OrderDetails(
                currentUser, product.getPrice() * request.getQuantity()));

        return new OrderItem(orderDetails, product);
    }

}
