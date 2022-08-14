package mx.edu.utez.persona.model.persona;
import mx.edu.utez.persona.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPersona {
    BeanPersona persona = new BeanPersona();
    Connection conn;
    PreparedStatement pstm;
    CallableStatement cstm;
    ResultSet rs;

    public List<BeanPersona> findAll() {
        List<BeanPersona> personas = new ArrayList<>();
        BeanPersona persona = null;
        try {
            conn = new MySQLConnection().getConnection();
            String query = "SELECT * FROM personas;";
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                persona = new BeanPersona();
                persona.setId(rs.getInt("id"));
                persona.setName(rs.getString("name"));
                persona.setSurname(rs.getString("surname"));
                persona.setCurp(rs.getString("curp"));
                persona.setBirthday(rs.getString("birthday"));
                personas.add(persona);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPersona.class.getName())
                    .log(Level.SEVERE, "Error findAll", e);
        } finally {
            closeConnections();
        }
        return personas;
    }

    public BeanPersona findOne(int id) {
        try {
            conn = new MySQLConnection().getConnection();
            String query = "SELECT * FROM personas WHERE id = ?";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                BeanPersona persona = new BeanPersona();
                persona.setId(rs.getInt("id"));
                persona.setName(rs.getString("name"));
                persona.setSurname(rs.getString("surname"));
                persona.setCurp(rs.getString("curp"));
                persona.setBirthday(rs.getString("birthday"));
                return persona;
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPersona.class.getName())
                    .log(Level.SEVERE, "Error findOne", e);
        } finally {
            closeConnections();
        }
        return null;
    }

    public boolean save(BeanPersona personas) {
        try {
            conn = new MySQLConnection().getConnection();
            String query = ("INSERT INTO personas" +
                    "(name, surname, curp, birthday)" +
                    " VALUES (?,?,?,?)");
            pstm = conn.prepareStatement(query);
            pstm.setString(1, personas.getName());
            pstm.setString(2, personas.getSurname());
            pstm.setString(3, personas.getCurp());
            pstm.setString(4, personas.getBirthday());
            return pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(DaoPersona.class.getName())
                    .log(Level.SEVERE, "Error save", e);
            return false;
        } finally {
            closeConnections();
        }
    }



    public boolean update(BeanPersona personas) {
        try {
            conn = new MySQLConnection().getConnection();
            String query = "UPDATE personas SET name = ?, surname = ?, curp = ?," +
                    "birthday = ? WHERE id = ?";
            pstm = conn.prepareStatement(query);
            pstm.setString(1, personas.getName());
            pstm.setString(2, personas.getSurname());
            pstm.setString(3, personas.getCurp());
            pstm.setString(4,personas.getBirthday());
            pstm.setInt(5,personas.getId());
            return pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(DaoPersona.class.getName())
                    .log(Level.SEVERE, "Error update", e);
            return false;
        } finally {
            closeConnections();
        }
    }

    public boolean delete(Long id) {
        try {
            conn = new MySQLConnection().getConnection();
            String query = "DELETE FROM personas WHERE id = ?";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, id);
            return pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(DaoPersona.class.getName())
                    .log(Level.SEVERE, "Error delete method");
            return false;
        } finally {
            closeConnections();
        }
    }

    public void closeConnections() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (cstm != null) {
                cstm.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
        }
    }

}
