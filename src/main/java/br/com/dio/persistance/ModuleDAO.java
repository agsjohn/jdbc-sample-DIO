package br.com.dio.persistance;

import br.com.dio.persistance.entity.ContactEntity;
import br.com.dio.persistance.entity.EmployeeEntity;
import br.com.dio.persistance.entity.ModuleEntity;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.ZoneOffset.UTC;

public class ModuleDAO {
    public List<ModuleEntity> findAll(){
        List<ModuleEntity> modules = new ArrayList<>();
        var sql ="SELECT m.id module_id," +
                "m.name module_name, " +
                "e.id employee_id, " +
                "e.name employee_name," +
                "e.salary employee_salary, " +
                "e.birthday employee_birthday " +
                "FROM modules m " +
                "INNER JOIN accesses a " +
                "ON a.module_id = m.id " +
                "INNER JOIN employees e " +
                "ON e.id = a.employee_id " +
                "ORDER BY m.id";
        try (var cnn = ConnectionUtil.getConnection();
             var ps = cnn.prepareStatement(sql);
        ){
            ps.executeQuery();
            var rs = ps.getResultSet();
            var hasNext = rs.next();
            while (hasNext){
                ModuleEntity module = new ModuleEntity();
                module.setId(rs.getLong("module_id"));
                module.setName(rs.getString("module_name"));
                module.setEmployees(new ArrayList<>());
                do{
                    var employee = new EmployeeEntity();
                    employee.setId(rs.getLong("employee_id"));
                    employee.setName(rs.getString("employee_name"));
                    employee.setSalary(rs.getBigDecimal("employee_salary"));
                    var birthdayInstant = rs.getTimestamp("employee_birthday").toInstant();
                    employee.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
                    module.getEmployees().add(employee);
                    hasNext = rs.next();
                }while(hasNext && module.getId() == rs.getLong("module_id"));
                modules.add(module);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return modules;
    }
}
