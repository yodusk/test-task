package com.example.backend.dto;

import com.example.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRegisterDto {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String description;

    private String address;

    private List<Role> roles;

}
