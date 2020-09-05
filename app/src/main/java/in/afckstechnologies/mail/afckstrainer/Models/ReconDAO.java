package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by admin on 3/7/2017.
 */

public class ReconDAO {
    String id = "";
    String bank_name = "";

    private boolean selected;

    public ReconDAO() {

    }

    public ReconDAO(String id, String bank_name) {
        this.id = id;
        this.bank_name = bank_name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    @Override
    public String toString() {
        return bank_name;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReconDAO) {
            ReconDAO c = (ReconDAO) obj;
            if (c.getBank_name().equals(bank_name) && c.getId() == id) return true;
        }

        return false;
    }
}
