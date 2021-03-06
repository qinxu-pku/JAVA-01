package com.geektime.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Builder
@ToString
@Data
public class Order {

    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 总价
     */
    private String totalPrice;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 配送id
     */
    private String deliveryId;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 是否有效
     */
    private String isEffective;

}
