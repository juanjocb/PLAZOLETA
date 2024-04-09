package com.pragma.powerup.infrastructure.out.jpa.entity;

import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "\"user\"")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    @Pattern(regexp = "[0-9]+")
    private String dni;

    @NotNull
    @Size(max = 13)
    @Pattern(regexp = "\\+?[0-9]+")
    private String number;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    private String role;
}
