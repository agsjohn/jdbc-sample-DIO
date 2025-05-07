package br.com.dio.persistance.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class EmployeeEntity {
    private long id;
    private String name;
    private BigDecimal salary;
    private OffsetDateTime birthday;
    private ContactEntity contact;
}
