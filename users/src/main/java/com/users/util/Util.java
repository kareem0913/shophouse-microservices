package com.users.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@UtilityClass
@Slf4j
public class Util {
    public static Timestamp currentTimestamp() {
        return Timestamp.from(Instant.now());
    }

    public static Set<String> mapUserAuthorityToString(Collection<? extends GrantedAuthority> authorities){
        return authorities.stream()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toSet());
    }
}
