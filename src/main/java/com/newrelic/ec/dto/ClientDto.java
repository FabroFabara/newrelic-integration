package com.newrelic.ec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClientDto {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
}
