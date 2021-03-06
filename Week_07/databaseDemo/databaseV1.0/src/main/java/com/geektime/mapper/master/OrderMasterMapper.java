package com.geektime.mapper.master;

import com.geektime.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 */
public interface OrderMasterMapper {

    Order selectById(@Param("orderId") Integer orderId);

    Integer updateById(@Param("entity") Order order);
}
