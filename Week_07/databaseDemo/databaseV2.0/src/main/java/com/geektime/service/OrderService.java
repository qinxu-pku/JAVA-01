package com.geektime.service;

import com.geektime.entity.Order;
import com.geektime.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 测试读写分离
     * @param order
     * @return
     */
    @Transactional
    public Order updateOrder(Order order) {
       orderMapper.updateById(order);
       return orderMapper.selectById(order.getId());
    }

    /**
     * 测试读写分离
     * @param orderId
     * @return
     */
    public Order selectOrder(Integer orderId) {
        return orderMapper.selectById(orderId);
    }

    /**
     * 多个从库
     * @param orderId
     * @return
     */
    public Order selectOrder2(Integer orderId) {
        return orderMapper.selectById(orderId);
    }

    /**
     * 动态路由多个从库
     * @param orderId
     * @return
     */
    public Order selectOrderRandomDS(Integer orderId) {
        return orderMapper.selectById(orderId);
    }

}
