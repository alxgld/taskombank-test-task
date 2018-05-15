package com.gladunalexander.clientservice.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Alexander Gladun
 */

@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String middleName;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotNull
    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(unique = true)
    private String phoneNumber;
}
