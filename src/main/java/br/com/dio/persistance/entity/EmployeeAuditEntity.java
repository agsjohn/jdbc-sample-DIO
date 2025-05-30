package br.com.dio.persistance.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record EmployeeAuditEntity(
    long employeeId,
    String name,
    String oldName,
    BigDecimal salary,
    BigDecimal oldSalary,
    OffsetDateTime birthday,
    OffsetDateTime oldBirthday,
    OperationEnum operation
) {

}
