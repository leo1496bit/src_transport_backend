package com.src.src_transport.api.model;

import lombok.Data;

@Data
public class AdressePostal {
    private String address;
    private String ville;
    private String codePostal;
    private String complement;
}