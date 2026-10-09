package functions;

public class Batch {
    private String batchName;
    private int batchStatus;

    // Default Constructor
    public Batch() {
    }

    // Parameterized Constructor
    public Batch(String batchName, int batchStatus) {
        this.batchName = batchName;
        this.batchStatus = batchStatus;
    }

    // Getters and Setters
    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(int batchStatus) {
        this.batchStatus = batchStatus;
    }
}
