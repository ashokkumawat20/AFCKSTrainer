package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok Kumawat on 10/26/2017.
 */

public class ReconciliationDAO {
    String id="";
    String user_id="";
    String MobileNo="";
    String BatchNo="";
    String Fees="";
    String FeeMode="";
    String PaymentStatus="";
    String DateTimeOfEntry="";
    String ReceivedBy="";
    String Student_Name="";
    private boolean isSelected;
    public ReconciliationDAO()
    {}

    public ReconciliationDAO(String id, String user_id, String mobileNo, String batchNo, String fees, String feeMode, String paymentStatus, String dateTimeOfEntry, String receivedBy, String student_Name, boolean isSelected) {
        this.id = id;
        this.user_id = user_id;
        MobileNo = mobileNo;
        BatchNo = batchNo;
        Fees = fees;
        FeeMode = feeMode;
        PaymentStatus = paymentStatus;
        DateTimeOfEntry = dateTimeOfEntry;
        ReceivedBy = receivedBy;
        Student_Name = student_Name;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getFees() {
        return Fees;
    }

    public void setFees(String fees) {
        Fees = fees;
    }

    public String getFeeMode() {
        return FeeMode;
    }

    public void setFeeMode(String feeMode) {
        FeeMode = feeMode;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getDateTimeOfEntry() {
        return DateTimeOfEntry;
    }

    public void setDateTimeOfEntry(String dateTimeOfEntry) {
        DateTimeOfEntry = dateTimeOfEntry;
    }

    public String getReceivedBy() {
        return ReceivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        ReceivedBy = receivedBy;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
