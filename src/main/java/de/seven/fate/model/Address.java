package de.seven.fate.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {

    private String name;
    private String email;
}
