package com.example.backend.entities;

import com.example.backend.enums.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User entity used for authentication
 * */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "employee_employee_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String description;

    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

}

