package com.ncc.service.order;

import com.ncc.contants.Constant;
import com.ncc.mapstruct.dto.order.OrderDetailDto;
import com.ncc.mapstruct.dto.order.OrderItemDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.OrderDetail;
import com.ncc.model.OrderItem;
import com.ncc.model.User;
import com.ncc.repository.OrderDetailRepository;
import com.ncc.repository.OrderItemRepository;
import com.ncc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderItemRepository orderItemRepository;
    private MapstructMapper mapstructMapper;
    private OrderDetailRepository orderDetailRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderItemRepository orderItemRepository,
                            MapstructMapper mapstructMapper,
                            OrderDetailRepository orderDetailRepository,
                            UserRepository userRepository) {
        this.orderItemRepository = orderItemRepository;
        this.mapstructMapper = mapstructMapper;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderDetailDto> getOrderDetails(int userId, int statusId) {
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailByUserAndOrderStatus(userId, statusId);
        return orderDetails.stream().map(mapstructMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailDto> getOrderDetails(int statusId) {
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailByUserAndOrderStatus(statusId);
        return orderDetails.stream().map(mapstructMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDto save(OrderDetailDto orderDetailDto) {
        return null;
    }

    @Override
    public OrderItemDto save(OrderItemDto orderItemDto) {
        return null;
    }

    @Transactional
    @Override
    public void save(int userId) {
        User user = new User();
        user.setId(userId);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCreatedAt(new Date());
        orderDetail.setUser(user);
        orderDetail = orderDetailRepository.save(orderDetail);

        orderDetailRepository.saveOrderStatus(orderDetail.getId(), Constant.IN_CART_STATUS_ID);
    }

    @Override
    public OrderItemDto merge(OrderItemDto orderItemDto) {
        OrderItem orderItem = mapstructMapper.toEntity(orderItemDto);
        return mapstructMapper.toDto(orderItemRepository.save(orderItem));
    }

    @Override
    public OrderDetailDto merge(OrderDetailDto orderDetailDto) {
        return null;
    }

    @Override
    public void remove(int orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
