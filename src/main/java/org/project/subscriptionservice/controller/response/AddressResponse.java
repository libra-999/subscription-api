package org.project.subscriptionservice.controller.response;

import lombok.Data;

@Data
public class AddressResponse {

    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
