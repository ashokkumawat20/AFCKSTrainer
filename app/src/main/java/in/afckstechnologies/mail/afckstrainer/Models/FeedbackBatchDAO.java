package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok Kumawat on 7/27/2017.
 */

public class FeedbackBatchDAO {
    String BatchID="";
    String faculty_id="";

   public FeedbackBatchDAO()
    {

    }

    public FeedbackBatchDAO(String batchID, String faculty_id) {
        BatchID = batchID;
        this.faculty_id = faculty_id;

    }

    public String getBatchID() {
        return BatchID;
    }

    public void setBatchID(String batchID) {
        BatchID = batchID;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }



    @Override
    public String toString() {
        return BatchID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FeedbackBatchDAO) {
            FeedbackBatchDAO c = (FeedbackBatchDAO) obj;
            if (c.getBatchID().equals(BatchID))
                return true;
        }

        return false;
    }
}
