package com.src.src_transport.api.model;

import lombok.Data;

@Data
public class InformationBancaire {
    private String iban;
    private String bic;
    private String titulaire;
}