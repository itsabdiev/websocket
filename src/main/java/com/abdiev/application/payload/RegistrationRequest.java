package com.abdiev.application.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String profile;
}
