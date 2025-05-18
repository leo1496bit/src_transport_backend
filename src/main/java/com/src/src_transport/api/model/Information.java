package com.src.src_transport.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Information {
    private String statut;
    private String siret;
    private String nomEntreprise;
    private List<String> langue;
}
