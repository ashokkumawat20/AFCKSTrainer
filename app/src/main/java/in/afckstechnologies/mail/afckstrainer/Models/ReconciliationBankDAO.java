package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok Kumawat on 10/26/2017.
 */

public class ReconciliationBankDAO {
    String unique_id="";
    String t_date="";
    String narration="";
    String bank_id="";
    String amount="";
    String status="";

    public ReconciliationBankDAO()
    {}

    public ReconciliationBankDAO(String unique_id, String t_date, String narration, String bank_id, String amount, String status) {
        this.unique_id = unique_id;
        this.t_date = t_date;
        this.narration = narration;
        this.bank_id = bank_id;
        this.amount = amount;
        this.status = status;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
