package com.src.src_transport.api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "drivers") // Nom de la collection MongoDB
public class Driver {
    @Id
    private String id;
    private String genre;
    private String prenom;
    private String nom;
    private String tel;
    private String dateNaissance;
    private String email;
    private String experience;
    private String preference;
    private AdressePostal adresse;
    private Information information;
    private Circulation circulation;
    private InformationPermis informationPermis;
    private DocumentIdentite documentIdentite;
    private DocumentProfessionnel documentProfessionnel;
    private InformationBancaire informationBancaire;
}