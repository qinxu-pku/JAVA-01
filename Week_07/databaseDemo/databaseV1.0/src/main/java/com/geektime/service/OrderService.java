package com.geektime.service;

import com.geektime.entity.Order;
import com.geektime.mapper.master.OrderMasterMapper;
import com.geektime.slave.OrderSlaveMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lihongjian
 * @since 2021/3/6
 */
@Service
public class OrderService {

    @Resource
    private OrderMasterMapper orderMasterMapper;

    @Resource
    private OrderSlaveMapper orderSlaveMapper;

    @Transactional
    public Order updateOrder(Order order) {
       orderMasterMapper.updateById(order);

       return orderMasterMapper.selectById(order.getId());
    }

    public Order selectOrder(Integer orderId) {
        return orderSlaveMapper.selectById(orderId);
    }

}
