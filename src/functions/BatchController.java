/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package functions;
import functions.Student;
import java.sql.*;
import java.io.FileNotFoundException;
import dbControllers.DBConnection;
import dbControllers.DBConnection;
import gui.


/**
 *
 * @author pc
 */
public class BatchController {
    public static  boolean addNewBatch(Batch batch) throws ClassNotFoundException, SQLException{
        String SQL="Insert into batches values(?,?,?,?)"; //Step 1
        Connection connection = DBConnection.getInstance().getConnetion();
        PreparedStatement stm = connection.prepareStatement(SQL); //Step 4
        stm.setObject(1,student.getId());
        stm.setObject(2,student.getName());
        stm.setObject(3,student.getPrfMarks());
        stm.setObject(4,student.getDbmsMarks());
        return stm.executeUpdate()>0;
    }

    public static Student searchBatch(String id) throws FileNotFoundException, ClassNotFoundException, SQLException {
        String SQL="Select * From batches where id='"+id+"'";
        Connection connection = DBConnection.getInstance().getConnetion();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        if(rst.next()){
           return new Student(rst.getString("id"),rst.getString("name"),rst.getInt("prfMarks"),rst.getInt("dbmsMarks"));
        }
        return null;
    }
    public static boolean deleteBatch(String id)throws SQLException,ClassNotFoundException{
        /*DBConnection dbc=DBConnection.getInstance();
        Connection connection=dbc.getConnection();
        Statement stm = connection.createStatement();
        stm.executeUpdate("Delete From Student where id='"+id+"'");*/
        return DBConnection.getInstance().getConnetion().createStatement().executeUpdate("Delete From batches where id='"+id+"'")>0;
    }
    
     public static boolean checkBatchStatus(int batchNum) {
         BatchController.getAllBatch();
        for (int i = 0; i < batchArray.length; i++) {
            if (batchArray[i] != null && batchArray[i].getBatch() == batchNum) { 
                return batchArray[i].getBatchStatus() == ENROLLMENTOPEN;
            }
        }
        return false;
    }

    public static boolean updateBatch(Student student) throws ClassNotFoundException, SQLException {
        String SQL="Update batches Set name=?, prfMarks=?, dbmsMarks=? where id=?";
        Connection connection = DBConnection.getInstance().getConnetion();
        PreparedStatement stm = connection.prepareStatement(SQL); //Step 4
        stm.setObject(1,student.getName());
        stm.setObject(2,student.getPrfMarks());
        stm.setObject(3,student.getDbmsMarks());
        stm.setObject(4,student.getId());
        return stm.executeUpdate()>0;       
    }

    public static Student[] getAllBatch() throws ClassNotFoundException, SQLException {
        ResultSet rst = DBConnection.getInstance().getConnetion().createStatement().executeQuery("Select * From batches");
        Student[] BatchArray=new Student[0];
        while(rst.next()){
            Student[] tempStudentArray=new Student[BatchArray.length+1];
            for (int i = 0; i < BatchArray.length; i++) {
                tempStudentArray[i]=BatchArray[i];
            }
            BatchArray=tempStudentArray;
            Student student=new Student(rst.getString("id"),rst.getString("name"),rst.getInt("prfMarks"),rst.getInt("dbmsMarks"));
            BatchArray[BatchArray.length-1]=student;
        }
        return BatchArray;
    }
}
