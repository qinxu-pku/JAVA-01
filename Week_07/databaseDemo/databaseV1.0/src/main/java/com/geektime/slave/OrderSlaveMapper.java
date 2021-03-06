package com.geektime.slave;

import com.geektime.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 订单Mapper
 *
 * @author lihongjian
 * @since 2021/3/6
 */
public interface OrderSlaveMapper {

    Order selectById(@Param("orderId") Integer orderId);

    Integer updateById(@Param("entity") Order orderDO);
}
