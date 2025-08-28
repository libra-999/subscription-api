package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PaymentResponse {

    private String email;
    private String username;
    private String reference;
    private double price;
    private String currency;
    private String paymentMethod;
    private AddressResponse address;
    private String paymentDate;

}
