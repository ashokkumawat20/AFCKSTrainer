package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by admin on 3/7/2017.
 */

public class RequestChangeNameDAO {
    String id = "";
    String desc = "";

    private boolean selected;

    public RequestChangeNameDAO() {

    }

    public RequestChangeNameDAO(String id, String desc) {
        this.id = id;
        this.desc = desc;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return desc;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RequestChangeNameDAO) {
            RequestChangeNameDAO c = (RequestChangeNameDAO) obj;
            if (c.getDesc().equals(desc) && c.getId() == id) return true;
        }

        return false;
    }
}
