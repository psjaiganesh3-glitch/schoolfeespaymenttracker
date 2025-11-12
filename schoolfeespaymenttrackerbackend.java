package com.school.util;
import java.sql.*;

public class DBManager {
    private static final String URL = "jdbc:sqlite:school.db";

    static {
        try { Class.forName("org.sqlite.JDBC"); }
        catch (Exception e) { e.printStackTrace(); }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void insertStudent(int id, String name, String grade, double total, double paid) {
        String sql = "INSERT OR REPLACE INTO students(id,name,grade,totalFees,feesPaid) VALUES(?,?,?,?,?)";
        try(Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, grade);
            ps.setDouble(4, total);
            ps.setDouble(5, paid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateFeesPaid(int id, double paid) {
        String sql = "UPDATE students SET feesPaid=? WHERE id=?";
        try(Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, paid);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
