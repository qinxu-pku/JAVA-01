package com.geektime.mapper.slave;

import com.geektime.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 订单Mapper
 */
public interface OrderSlaveMapper {

    Order selectById(@Param("orderId") Integer orderId);

    Integer updateById(@Param("entity") Order order);
}
