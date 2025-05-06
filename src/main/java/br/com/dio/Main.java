package br.com.dio;

import br.com.dio.persistance.EmployeeAuditDAO;
import br.com.dio.persistance.EmployeeDAO;
import br.com.dio.persistance.entity.EmployeeEntity;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Main {

    private final static EmployeeDAO employeeDAO = new EmployeeDAO();
    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();

    public static void main(String[] args) {
        var flyway = Flyway.configure().
                dataSource("jdbc:mysql://localhost/jdbc-sample", "root", "")
                .load();
        flyway.migrate();

        var insert = new EmployeeEntity();
        insert.setName("Andre");
        insert.setSalary(new BigDecimal("2800"));
        insert.setBirthday(OffsetDateTime.now().minusYears(18));
        System.out.println(insert);
        employeeDAO.insert(insert);
        System.out.println(insert);

//        employeeDAO.findAll().forEach(System.out::println);
//        System.out.println(employeeDAO.findById(1));

        var update = new EmployeeEntity();
        update.setId(insert.getId());
        update.setName("Gabriel");
        update.setSalary(new BigDecimal("5500"));
        update.setBirthday(OffsetDateTime.now().minusYears(18).minusDays(31));
        employeeDAO.update(update);

        employeeDAO.delete(insert.getId());

        employeeAuditDAO.findAll().forEach(System.out::println);
    }
}
