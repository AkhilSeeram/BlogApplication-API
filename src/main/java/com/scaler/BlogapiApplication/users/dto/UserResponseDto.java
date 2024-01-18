package com.scaler.BlogapiApplication.users.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UserResponseDto {
    @NonNull
    String username;
    @NonNull
    String email;
    String bio;

    String token;
}
