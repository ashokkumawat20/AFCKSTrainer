package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 5/4/2017.
 */

public class CityDAO {
    String city_id = "";
    String city_name = "";
    String state_id = "";

    public CityDAO() {

    }


    public CityDAO(String city_id, String city_name, String state_id) {
        this.city_id = city_id;
        this.city_name = city_name;
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    @Override
    public String toString() {
        return city_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CityDAO) {
            CityDAO c = (CityDAO) obj;
            if (c.getCity_name().equals(city_name))
                return true;
        }

        return false;
    }
}
