package br.com.dio.persistance;

import br.com.dio.persistance.entity.EmployeeAuditEntity;
import br.com.dio.persistance.entity.OperationEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.isNull;

public class EmployeeAuditDAO {
    public List<EmployeeAuditEntity> findAll(){
        List<EmployeeAuditEntity> entities = new ArrayList<>();
        try (var cnn = ConnectionUtil.getConnection();
             var st = cnn.createStatement();
        ){
            st.executeQuery("SELECT * FROM view_employee_audit");
            var rs = st.getResultSet();
            while(rs.next()){
                entities.add(new EmployeeAuditEntity(
                        rs.getLong("employee_id"),
                        rs.getString("name"),
                        rs.getString("old_name"),
                        rs.getBigDecimal("salary"),
                        rs.getBigDecimal("old_salary"),
                        getDateTimeOrNull(rs, "birthday"),
                        getDateTimeOrNull(rs, "old_birthday"),
                        OperationEnum.getByDbOperation(rs.getString("operation"))
                ));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return entities;
    }

    public OffsetDateTime getDateTimeOrNull (final ResultSet rs, final String field) throws  SQLException{
        return isNull(rs.getTimestamp(field)) ? null :
                OffsetDateTime.ofInstant(rs.getTimestamp(field).toInstant(), UTC);
    }

}
