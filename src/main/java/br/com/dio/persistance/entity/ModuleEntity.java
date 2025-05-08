package br.com.dio.persistance.entity;

import lombok.Data;

import java.util.List;

@Data
public class ModuleEntity {
    private long id;
    private String name;
    private List<EmployeeEntity> employees;
}
