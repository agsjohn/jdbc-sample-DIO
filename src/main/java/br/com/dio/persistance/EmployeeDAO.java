package br.com.dio.persistance;

import br.com.dio.persistance.entity.EmployeeEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;

public class EmployeeDAO {
    public void insert(final EmployeeEntity entity){
        try (var cnn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO employees (name, salary, birthday) values (?, ?, ?)";
            var ps = cnn.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setBigDecimal(2, entity.getSalary());
            ps.setTimestamp(3, formatOffsetDateTime(entity.getBirthday()));
            ps.executeUpdate();
//            System.out.printf("Foram afetados %s registros na base de dados\n", st.getUpdateCount());
            if(ps instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void update(final EmployeeEntity entity){
        try (var cnn = ConnectionUtil.getConnection()){
            String sql = "UPDATE employees set name = ?, salary = ?, birthday = ? WHERE id = ?";
            var ps = cnn.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setBigDecimal(2, entity.getSalary());
            ps.setTimestamp(3, formatOffsetDateTime(entity.getBirthday()));
            ps.setLong(4, entity.getId());
            ps.executeUpdate();
//            System.out.printf("Foram afetados %s registros na base de dados\n", st.getUpdateCount());
            if(ps instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void delete(final long id){
        try (var cnn = ConnectionUtil.getConnection()){
            String sql = "DELETE FROM employees WHERE id = ?";
            var ps = cnn.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
//            System.out.printf("Foram afetados %s registros na base de dados\n", st.getUpdateCount());
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
        try (var cnn = ConnectionUtil.getConnection()){
            var ps = cnn.prepareStatement("SELECT * FROM employees WHERE id = ?");
            ps.setLong(1, id);
            var rs = ps.executeQuery();
            while(rs.next()) {
                entity.setId(id);
                entity.setName(rs.getString("name"));
                entity.setSalary(rs.getBigDecimal("salary"));
                var birthdayInstant = rs.getTimestamp("birthday").toInstant();
                entity.setBirthday(OffsetDateTime.ofInstant(birthdayInstant, UTC));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return entity;
    }

    private java.sql.Timestamp formatOffsetDateTime(final OffsetDateTime dateTime) {
        var utcDatetime = dateTime.withOffsetSameInstant(ZoneOffset.UTC);
        return java.sql.Timestamp.from(utcDatetime.toInstant());
    }
}
