package com.cart.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;

@UtilityClass
@Slf4j
public class Util {
    public static Timestamp currentTimestamp() {
        return Timestamp.from(Instant.now());
    }
}
