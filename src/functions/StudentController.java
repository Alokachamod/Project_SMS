/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package functions;
import java.sql.*;
import java.io.FileNotFoundException;
import dbControllers.DBConnection;


/**
 *
 * @author pc
 */
public class StudentController {
    public static  boolean addNeStudent(Student student) throws ClassNotFoundException, SQLException{
        String SQL="Insert into Student values(?,?,?,?)"; //Step 1
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement(SQL); //Step 4
        stm.setObject(1,student.getId());
        stm.setObject(2,student.getName());
        stm.setObject(3,student.getPrfMarks());
        stm.setObject(4,student.getDbmsMarks());
        return stm.executeUpdate()>0;
    }

    public static Student searchStudent(String id) throws FileNotFoundException, ClassNotFoundException, SQLException {
        String SQL="Select * From Student where id='"+id+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        if(rst.next()){
           return new Student(rst.getString("id"),rst.getString("name"),rst.getInt("prfMarks"),rst.getInt("dbmsMarks"));
        }
        return null;
    }
    public static boolean deleteStudent(String id)throws SQLException,ClassNotFoundException{
        /*DBConnection dbc=DBConnection.getInstance();
        Connection connection=dbc.getConnection();
        Statement stm = connection.createStatement();
        stm.executeUpdate("Delete From Student where id='"+id+"'");*/
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Student where id='"+id+"'")>0;
    }

    public static boolean updateStudent(Student student) throws ClassNotFoundException, SQLException {
        String SQL="Update Student Set name=?, prfMarks=?, dbmsMarks=? where id=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement(SQL); //Step 4
        stm.setObject(1,student.getName());
        stm.setObject(2,student.getPrfMarks());
        stm.setObject(3,student.getDbmsMarks());
        stm.setObject(4,student.getId());
        return stm.executeUpdate()>0;       
    }

   public static String getNewStudentId() throws ClassNotFoundException, SQLException {
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select id from Student order by id desc limit 1");
        if(rst.next()){
            int newIdNo=Integer.parseInt(rst.getString("id").substring(1));
            return String.format("S%04d", newIdNo+1);
        }
        return "S0001";
    }

    public static Student[] getAllStudent() throws ClassNotFoundException, SQLException {
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * From Student");
        Student[] studentArray=new Student[0];
        while(rst.next()){
            Student[] tempStudentArray=new Student[studentArray.length+1];
            for (int i = 0; i < studentArray.length; i++) {
                tempStudentArray[i]=studentArray[i];
            }
            studentArray=tempStudentArray;
            Student student=new Student(rst.getString("id"),rst.getString("name"),rst.getInt("prfMarks"),rst.getInt("dbmsMarks"));
            studentArray[studentArray.length-1]=student;
        }
        return studentArray;
    }
}
