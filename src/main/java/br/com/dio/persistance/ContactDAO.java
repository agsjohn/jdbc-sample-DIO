package br.com.dio.persistance;

import br.com.dio.persistance.entity.ContactEntity;
import br.com.dio.persistance.entity.EmployeeEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static java.util.TimeZone.LONG;

public class ContactDAO {
    public void insert(final ContactEntity entity){
        try (
                var cnn = ConnectionUtil.getConnection();
                var ps = cnn.prepareStatement("INSERT INTO contacts (description, type, employee_id) values (?, ?, ?)")
        ){
            ps.setString(1, entity.getDescription());
            ps.setString(2, entity.getType());
            ps.setLong(3, entity.getEmployee_id());
            ps.executeUpdate();
            if(ps instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
