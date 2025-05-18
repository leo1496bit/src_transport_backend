package com.src.src_transport.api.model;

import lombok.Data;
import java.util.Date;

@Data
public class DocumentIdentite {
    private String ville;
    private Date date;
    private String numero;
}