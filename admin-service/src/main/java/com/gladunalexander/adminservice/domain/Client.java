package com.gladunalexander.adminservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Alexander Gladun
 */

@Getter
@Setter
@ToString
public class Client {

    private int id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String phoneNumber;
}
