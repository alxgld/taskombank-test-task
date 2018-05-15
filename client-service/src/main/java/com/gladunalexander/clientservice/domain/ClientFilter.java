package com.gladunalexander.clientservice.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Alexander Gladun
 */

@Getter
@Setter
public class ClientFilter {

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String phoneNumber;
}
