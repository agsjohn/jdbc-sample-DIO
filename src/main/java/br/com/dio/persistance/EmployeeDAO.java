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

public class EmployeeDAO {
    public void insert(final EmployeeEntity entity){
        try (
            var cnn = ConnectionUtil.getConnection();
            var ps = cnn.prepareStatement("INSERT INTO employees (name, salary, birthday) values (?, ?, ?)")
        ){
            ps.setString(1, entity.getName());
            ps.setBigDecimal(2, entity.getSalary());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(UTC).toLocalDateTime()));
            ps.executeUpdate();
            if(ps instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void insertWithProcedure(final EmployeeEntity entity){
        try (
                var cnn = ConnectionUtil.getConnection();
                var ps = cnn.prepareCall("call prc_insert_employee(?, ?, ?, ?)")
        ){
            ps.registerOutParameter(1, LONG);
            ps.setString(2, entity.getName());
            ps.setBigDecimal(3, entity.getSalary());
            ps.setTimestamp(4, Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(UTC).toLocalDateTime()));
            ps.execute();
            entity.setId(ps.getLong(1));
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void insert(final List<EmployeeEntity> entities){
        try (var cnn = ConnectionUtil.getConnection()){
            var sql = "INSERT INTO employees (name, salary, birthday) values (?, ?, ?)";
            try(var ps = cnn.prepareStatement(sql)){
                cnn.setAutoCommit(false);
                for(int i=0; i < entities.size(); i++){
                    ps.setString(1, entities.get(i).getName());
                    ps.setBigDecimal(2, entities.get(i).getSalary());
                    var timeStamp = Timestamp.valueOf(entities.get(i).getBirthday().atZoneSameInstant(UTC).toLocalDateTime());
                    ps.setTimestamp(3, timeStamp);
                    ps.addBatch();
                    if(i % 1000 == 0 || i == entities.size() - 1) ps.executeBatch();
                }
                cnn.commit();
            }catch (SQLException ex){
                cnn.rollback();
                ex.printStackTrace();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void update(final EmployeeEntity entity){
        try (
            var cnn = ConnectionUtil.getConnection();
            var ps = cnn.prepareStatement("UPDATE employees set name = ?, salary = ?, birthday = ? WHERE id = ?");
        ){
            ps.setString(1, entity.getName());
            ps.setBigDecimal(2, entity.getSalary());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getBirthday().atZoneSameInstant(UTC).toLocalDateTime()));
            ps.setLong(4, entity.getId());
            ps.executeUpdate();
            if(ps instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void delete(final long id){
        try (
            var cnn = ConnectionUtil.getConnection();
            var ps = cnn.prepareStatement("DELETE FROM employees WHERE id = ?");
        ){
            ps.setLong(1, id);
            ps.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public List<EmployeeEntity> findAll(){
        List<EmployeeEntity> entities = new ArrayList<>();
        try (var cnn = ConnectionUtil.getConnection();
             var st = cnn.createStatement();
        ){
            st.executeQuery("SELECT * FROM employees ORDER BY name");
            var rs = st.getResultSet();
            while(rs.next()){
                var entity = new EmployeeEntity();
                entity.setId(rs.getLong("id"));
                entity.setName(rs.getString("name"));
                entity.setSalary(rs.getBigDecimal("salary"));
                var birthdayInstant = rs.getTimestamp("birthday").toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
                entities.add(entity);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return entities;
    }

    public EmployeeEntity findById(final long id){
        var entity = new EmployeeEntity();
        var sql = "SELECT * FROM employees e INNER JOIN contacts c ON c.employee_id = e.id WHERE e.id = ?";
        try (
            var cnn = ConnectionUtil.getConnection();
            var ps = cnn.prepareStatement(sql);
        ){
            ps.setLong(1, id);
            var rs = ps.executeQuery();
            while(rs.next()) {
                entity.setId(id);
                entity.setName(rs.getString("name"));
                entity.setSalary(rs.getBigDecimal("salary"));
                var birthdayInstant = rs.getTimestamp("birthday").toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
                entity.setContact(new ContactEntity());
                entity.getContact().setId(rs.getLong("c.id"));
                entity.getContact().setDescription(rs.getString("description"));
                entity.getContact().setType(rs.getString("type"));
                entity.getContact().setEmployee_id(id);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return entity;
    }
}
