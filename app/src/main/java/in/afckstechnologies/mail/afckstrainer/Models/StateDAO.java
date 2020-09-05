package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 5/4/2017.
 */

public class StateDAO {
    String state_id = "";
    String state_name = "";
    String country_id = "";

    public StateDAO() {

    }


    public StateDAO(String state_id, String state_name, String country_id) {
        this.state_id = state_id;
        this.state_name = state_name;
        this.country_id = country_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    @Override
    public String toString() {
        return state_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StateDAO) {
            StateDAO c = (StateDAO) obj;
            if (c.getState_name().equals(state_name))
                return true;
        }

        return false;
    }
}
