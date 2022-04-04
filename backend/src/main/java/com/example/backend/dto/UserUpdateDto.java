package com.example.backend.dto;

import com.example.backend.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class UserUpdateDto {

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String description;

    private String address;

    private List<Role> roles;
}
