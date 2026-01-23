package com.orders.error.model;

import java.sql.Timestamp;

public record ErrorResponse(int code, Object message, Object Description, Timestamp timestamp) {
}
