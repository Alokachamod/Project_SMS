package functions;

import dbControllers.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BatchController {

    public static final int ENROLLMENT_OPEN = 1;
    public static final int ENROLLMENT_CLOSED = 0;

    /**
     * Replaces addBatch: Inserts a new batch into the database.
     */
    public static boolean addBatch(Batch newBatch) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO batches (batchName, batchStatus) VALUES (?, ?)";
        Connection connection = DBConnection.getInstance().getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, newBatch.getBatchName());
            stm.setInt(2, newBatch.getBatchStatus());
            return stm.executeUpdate() > 0;
        }
    }

    /**
     * Searches a batch by number and returns the Batch object (or null if not found).
     */
    public static Batch searchBatch(int batchNum) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM batches WHERE batchName = ?";
        Connection connection = DBConnection.getInstance().getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, batchNum);
            try (ResultSet rst = stm.executeQuery()) {
                if (rst.next()) {
                    return new Batch(
                        rst.getInt("batchName"),
                        rst.getInt("batchStatus")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Replaces isBatchExists: Checks if a batch exists in the database.
     */
    public static boolean isBatchExists(int batchNum) throws ClassNotFoundException, SQLException {
        return searchBatch(batchNum) != null;
    }

    /**
     * Replaces checkBatchStatus: Checks if the batch enrollment is currently open.
     */
    public static boolean checkBatchStatus(int batchNum) throws ClassNotFoundException, SQLException {
        Batch batch = searchBatch(batchNum);
        return batch != null && batch.getBatchStatus() == ENROLLMENT_OPEN;
    }

    /**
     * Replaces enrollmentStatus: Returns the status description string for a given batch number.
     */
    public static String getEnrollmentStatus(int batchNum) throws ClassNotFoundException, SQLException {
        Batch batch = searchBatch(batchNum);
        if (batch != null) {
            if (batch.getBatchStatus() == ENROLLMENT_OPEN) {
                return "ENROLLMENT OPEN";
            } else if (batch.getBatchStatus() == ENROLLMENT_CLOSED) {
                return "ENROLLMENT CLOSED";
            }
        }
        return "Unknown Status";
    }

    /**
     * Replaces getAllBatches: Retrieves all batches from the database.
     */
    public static Batch[] getAllBatches() throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM batches";
        Connection connection = DBConnection.getInstance().getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rst = stm.executeQuery()) {

            ArrayList<Batch> batchList = new ArrayList<>();
            while (rst.next()) {
                batchList.add(new Batch(
                    rst.getInt("batchName"),
                    rst.getInt("batchStatus")
                ));
            }
            return batchList.toArray(new Batch[0]);
        }
    }

    /**
     * Updates an existing batch status in the database.
     */
    public static boolean updateBatchStatus(int batchNum, int newStatus) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE batches SET batchStatus = ? WHERE batchName = ?";
        Connection connection = DBConnection.getInstance().getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, newStatus);
            stm.setInt(2, batchNum);
            return stm.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a batch from the database.
     */
    public static boolean deleteBatch(int batchNum) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM batches WHERE batchName = ?";
        Connection connection = DBConnection.getInstance().getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, batchNum);
            return stm.executeUpdate() > 0;
        }
    }
}