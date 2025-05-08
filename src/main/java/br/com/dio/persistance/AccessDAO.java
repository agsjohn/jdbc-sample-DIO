package br.com.dio.persistance;

import br.com.dio.persistance.entity.EmployeeEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.SQLException;
import java.sql.Timestamp;

import static java.time.ZoneOffset.UTC;

public class AccessDAO {
    public void insert(final long employeeId, final long moduleID){
        try (
                var cnn = ConnectionUtil.getConnection();
                var ps = cnn.prepareStatement("INSERT INTO accesses (module_id, employee_id) values (?, ?)")
        ){
            ps.setLong(1, moduleID);
            ps.setLong(2, employeeId);
            ps.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
