package br.com.dio;

import br.com.dio.persistance.ContactDAO;
import br.com.dio.persistance.EmployeeAuditDAO;
import br.com.dio.persistance.EmployeeDAO;
import br.com.dio.persistance.ModuleDAO;
import br.com.dio.persistance.entity.ContactEntity;
import br.com.dio.persistance.entity.EmployeeEntity;
import br.com.dio.persistance.entity.ModuleEntity;
import net.datafaker.Faker;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Stream;

import static java.time.ZoneOffset.UTC;

public class Main {

    private final static EmployeeDAO employeeDAO = new EmployeeDAO();
    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();
    private final static ContactDAO contactDAO = new ContactDAO();
    private final static ModuleDAO moduleDAO = new ModuleDAO();
    private final static Faker faker = new Faker(Locale.of("pt", "BR"));

    public static void main(String[] args) {
        var flyway = Flyway.configure().
                dataSource("jdbc:mysql://localhost/jdbc-sample", "root", "")
                .load();
        flyway.migrate();

//        var insert = new EmployeeEntity();
//        insert.setName("Andre");
//        insert.setSalary(new BigDecimal("2800"));
//        insert.setBirthday(OffsetDateTime.now().minusYears(18));
//        System.out.println(insert);
//        employeeDAO.insert(insert);
//        System.out.println(insert);

//        employeeDAO.findAll().forEach(System.out::println);
//        System.out.println(employeeDAO.findById(1));

//        var update = new EmployeeEntity();
//        update.setId(insert.getId());
//        update.setName("Gabriel");
//        update.setSalary(new BigDecimal("5500"));
//        update.setBirthday(OffsetDateTime.now().minusYears(18).minusDays(31));
//        employeeDAO.update(update);
//
//        employeeDAO.delete(insert.getId());
//
//        employeeAuditDAO.findAll().forEach(System.out::println);

//        var entities = Stream.generate(() ->{
//            var employee = new EmployeeEntity();
//            employee.setName(faker.name().fullName());
//            employee.setSalary(new BigDecimal(faker.number().digits(4)));
//            employee.setBirthday(OffsetDateTime.of(LocalDate.now().minusYears(faker.number().numberBetween(40, 20)), LocalTime.MIN, UTC));
//            return employee;
//        }).limit(10000)
//                .toList();
//        employeeDAO.insert(entities);

//        var employee = new EmployeeEntity();
//        employee.setName("João");
//        employee.setSalary(new BigDecimal("3200"));
//        employee.setBirthday(OffsetDateTime.now().minusYears(25));
//        System.out.println(employee);
//        employeeDAO.insert(employee);
//        System.out.println(employee);

//        var contact = new ContactEntity();
//        contact.setDescription("miguel@miguel.com");
//        contact.setType("email");
//        contact.setEmployee(employee);
//        contactDAO.insert(contact);

//        System.out.println(employeeDAO.findById(1));

//        var employee = new EmployeeEntity();
//        employee.setName("João");
//        employee.setSalary(new BigDecimal("3200"));
//        employee.setBirthday(OffsetDateTime.now().minusYears(25));
//        System.out.println(employee);
//        employeeDAO.insert(employee);
//        System.out.println(employee);
//
//        var contact1 = new ContactEntity();
//        contact1.setDescription("miguel@miguel.com");
//        contact1.setType("email");
//        contact1.setEmployee_id(employee.getId());
//        contactDAO.insert(contact1);
//
//        var contact2 = new ContactEntity();
//        contact2.setDescription("331568492050");
//        contact2.setType("celular");
//        contact2.setEmployee_id(employee.getId());
//        contactDAO.insert(contact2);

//        System.out.println(employeeDAO.findById(2));
//        employeeDAO.findAll().forEach(System.out::println);

//        var entities = Stream.generate(() ->{
//            var employee = new EmployeeEntity();
//            employee.setName(faker.name().fullName());
//            employee.setSalary(new BigDecimal(faker.number().digits(4)));
//            employee.setBirthday(OffsetDateTime.of(LocalDate.now().minusYears(faker.number().numberBetween(40, 20)), LocalTime.MIN, UTC));
//            employee.setModules(new ArrayList<>());
//            var moduleAmount = faker.number().numberBetween(1, 4);
//            for (int i = 0; i < moduleAmount; i++) {
//                var module = new ModuleEntity();
//                module.setId(i + 1);
//                employee.getModules().add(module);
//            }
//            return employee;
//        }).limit(3)
//                .toList();
//        entities.forEach(employeeDAO::insert);

        moduleDAO.findAll().forEach(System.out::println);
    }
}
