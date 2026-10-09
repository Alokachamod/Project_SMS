package functions;

public class Batch {
    private int batchName;
    private int batchStatus;

    public Batch() {
    }

    public Batch(int batchName, int batchStatus) {
        this.batchName = batchName;
        this.batchStatus = batchStatus;
    }

    public int getBatchName() {
        return batchName;
    }

    public void setBatchName(int batchName) {
        this.batchName = batchName;
    }

    public int getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(int batchStatus) {
        this.batchStatus = batchStatus;
    }
}