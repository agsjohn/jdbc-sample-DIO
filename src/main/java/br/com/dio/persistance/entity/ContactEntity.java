package br.com.dio.persistance.entity;

import lombok.Data;

@Data
public class ContactEntity {
    private long id;
    private String description;
    private String type;
    private long employee_id;
}
