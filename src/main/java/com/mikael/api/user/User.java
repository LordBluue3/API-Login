package com.mikael.api.user;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class User {

    private final String email, password;
}
