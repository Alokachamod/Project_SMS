package functions;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author pc
 */
public class Student {
    private String id;
    private String name;
    private int prfMarks;
    private int dbmsMarks;

    public Student() {
    }

    public Student(String id, String name, int prfMarks, int dbmsMarks) {
        this.id = id;
        this.name = name;
        this.prfMarks = prfMarks;
        this.dbmsMarks = dbmsMarks;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the prfMarks
     */
    public int getPrfMarks() {
        return prfMarks;
    }

    /**
     * @param prfMarks the prfMarks to set
     */
    public void setPrfMarks(int prfMarks) {
        this.prfMarks = prfMarks;
    }

    /**
     * @return the dbmsMarks
     */
    public int getDbmsMarks() {
        return dbmsMarks;
    }

    /**
     * @param dbmsMarks the dbmsMarks to set
     */
    public void setDbmsMarks(int dbmsMarks) {
        this.dbmsMarks = dbmsMarks;
    }

    @Override
    public String toString() {
        return id+","+name+","+prfMarks+","+dbmsMarks;
                
    }
    
            
}
