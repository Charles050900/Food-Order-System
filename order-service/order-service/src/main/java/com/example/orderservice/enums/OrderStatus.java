package com.example.orderservice.enums;

public enum OrderStatus {

    PLACED,


    PAYMENT_SUCCESS,
    PAYMENT_FAILED,

    PREPARING,
    READY,

    OUT_FOR_DELIVERY,
    DELIVERED,

    CANCELLED
}