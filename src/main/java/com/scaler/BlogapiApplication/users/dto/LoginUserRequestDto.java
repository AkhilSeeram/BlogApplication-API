package com.scaler.BlogapiApplication.users.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginUserRequestDto {
    @NonNull
    String username;
    @NonNull
    String password;
}
