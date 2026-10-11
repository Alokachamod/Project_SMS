/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package functions;

import java.sql.*;
import java.io.FileNotFoundException;
import dbControllers.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pc
 */
public class StudentController {

    public static boolean addNeStudent(Student student) throws ClassNotFoundException, SQLException {
        String SQL = "INSERT INTO Students (id, name, nic, prf_marks, dbms_marks) VALUES (?, ?, ?, ?, ?)"; //Step 1
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement(SQL); //Step 4
        stm.setObject(1, student.getId());
        stm.setObject(2, student.getName());
        stm.setObject(3, student.getNic());
        stm.setObject(4, student.getPrfMarks());
        stm.setObject(5, student.getDbmsMarks());
        return stm.executeUpdate() > 0;
    }

    public static Student searchStudent(String id) throws FileNotFoundException, ClassNotFoundException, SQLException {
        String SQL = "Select * From Students where id='" + id + "'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        if (rst.next()) {
            return new Student(rst.getString("id"), rst.getString("name"), rst.getString("nic"), rst.getInt("prf_marks"), rst.getInt("dbms_marks"));
        }
        return null;
    }

    public static boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        /*DBConnection dbc=DBConnection.getInstance();
        Connection connection=dbc.getConnection();
        Statement stm = connection.createStatement();
        stm.executeUpdate("Delete From Student where id='"+id+"'");*/
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Student where id='" + id + "'") > 0;
    }

    public static boolean updateStudent(Student student) throws ClassNotFoundException, SQLException {
        String SQL = "Update Students Set name=?, prfMarks=?, dbmsMarks=? where id=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement(SQL); //Step 4
        stm.setObject(1, student.getName());
        stm.setObject(2, student.getPrfMarks());
        stm.setObject(3, student.getDbmsMarks());
        stm.setObject(4, student.getId());
        return stm.executeUpdate() > 0;
    }

    public static Student[] getAllStudent() throws ClassNotFoundException, SQLException {
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * From Student");
        Student[] studentArray = new Student[0];
        while (rst.next()) {
            Student[] tempStudentArray = new Student[studentArray.length + 1];
            for (int i = 0; i < studentArray.length; i++) {
                tempStudentArray[i] = studentArray[i];
            }
            studentArray = tempStudentArray;
            Student student = new Student(rst.getString("id"), rst.getString("name"), rst.getString("nic"), rst.getInt("prfMarks"), rst.getInt("dbmsMarks"));
            studentArray[studentArray.length - 1] = student;
        }
        return studentArray;

    }

    public static String generateStuId(int y, String mode) throws SQLException, ClassNotFoundException {
        boolean isPhysical = mode != null && mode.trim().equalsIgnoreCase("PHYSICAL");
        String currentPrefix = (isPhysical ? "PR26" : "OR26") + y;

        // Matches any student ID that contains the batch number y before the 3-digit counter
        String sql = "SELECT id FROM students WHERE id LIKE ? OR id LIKE ? ORDER BY RIGHT(id, 3) DESC LIMIT 1";

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, "PR26" + y + "%");
            pstm.setString(2, "OR26" + y + "%");

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String lastId = rs.getString("id");
                    // Extract last 3 digits regardless of whether it was PR26 or OR26
                    String lastNumStr = lastId.substring(lastId.length() - 3);
                    int nextCount = Integer.parseInt(lastNumStr) + 1;
                    return currentPrefix + String.format("%03d", nextCount);
                }
            }
        }

        // Default if no student has registered in this batch yet
        return currentPrefix + "001";
    }

    public static boolean checkNic(String nic) throws SQLException, ClassNotFoundException {
        // Check if any record exists with the provided NIC
        String sql = "SELECT 1 FROM students WHERE nic = ? LIMIT 1";

        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, nic.trim());

        ResultSet rs = pstm.executeQuery();

        // Returns true if a match is found, false otherwise
        return rs.next();
    }

}
