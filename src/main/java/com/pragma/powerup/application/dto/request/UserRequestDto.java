package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.domain.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;
import java.time.LocalDate;

@Setter
@Getter
public class UserRequestDto {
    private String name;
    private String lastname;
    private String dni;
    private String number;
    private LocalDate birthDate;
    private String email;
    private String password;
}