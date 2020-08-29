package com.thinkenterprise.domain.employee;

import javax.persistence.Entity;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Entity
public class Pilot extends Employee {

    String certificateNumber;

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber=certificateNumber;
    }

    public String getCertificateNumber() {
        return this.certificateNumber;
    }

}