package functions;

import dbControllers.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller class for managing Batch operations.
 */
public class BatchController {

    public static final int ENROLLMENT_OPEN = 1;
    public static final int ENROLLMENT_CLOSED = 0;

    /**
     * Adds a new batch record into the database.
     */
    public static boolean addNewBatch(Batch batch) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO batches (batchName, batchStatus) VALUES (?, ?)";
        Connection connection = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, batch.getBatchName());
            stm.setInt(2, batch.getBatchStatus());
            return stm.executeUpdate() > 0;
        }
    }

    /**
     * Searches a batch by its name/id.
     */
    public static Batch searchBatch(String batchName) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM batches WHERE batchName = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, batchName);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return new Batch(
                    rst.getString("batchName"),
                    rst.getInt("batchStatus")
                );
            }
        }
        return null;
    }

    /**
     * Deletes a batch by name/id.
     */
    public static boolean deleteBatch(String batchName) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM batches WHERE batchName = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, batchName);
            return stm.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing batch status by its name.
     */
    public static boolean updateBatch(Batch batch) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE batches SET batchStatus = ? WHERE batchName = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, batch.getBatchStatus());
            stm.setString(2, batch.getBatchName());
            return stm.executeUpdate() > 0;
        }
    }

    /**
     * Checks if enrollment is open for a given batch.
     */
    public static boolean checkBatchStatus(String batchName) throws ClassNotFoundException, SQLException {
        Batch batch = searchBatch(batchName);
        return batch != null && batch.getBatchStatus() == ENROLLMENT_OPEN;
    }

    /**
     * Retrieves all batches as an array.
     */
    public static Batch[] getAllBatch() throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM batches";
        Connection connection = DBConnection.getInstance().getConnection();
        
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rst = stm.executeQuery()) {
            
            ArrayList<Batch> batchList = new ArrayList<>();
            while (rst.next()) {
                batchList.add(new Batch(
                    rst.getString("batchName"),
                    rst.getInt("batchStatus")
                ));
            }
            return batchList.toArray(new Batch[0]);
        }
    }
}