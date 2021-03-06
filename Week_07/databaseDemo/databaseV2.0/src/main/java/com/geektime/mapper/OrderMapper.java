package com.geektime.mapper;

import com.geektime.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 */
public interface OrderMapper {

    Order selectById(@Param("orderId") Integer orderId);

    Integer updateById(@Param("entity") Order order);
}
