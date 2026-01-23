package com.orders.service.admin;

import com.orders.model.dto.order.OrderResponse;
import com.orders.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminOrderService {

    Page<OrderResponse> getAllOrders(Pageable pageable);

    OrderResponse getOrder(Long orderId);

    void changeOrderStatus(Long orderId, OrderStatus orderStatus);

}
