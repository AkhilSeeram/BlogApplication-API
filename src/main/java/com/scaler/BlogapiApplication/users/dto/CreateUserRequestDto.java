package com.scaler.BlogapiApplication.users.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserRequestDto {
    @NonNull
    String username;
    @NonNull
    String email;
    @NonNull
    String password;
}
