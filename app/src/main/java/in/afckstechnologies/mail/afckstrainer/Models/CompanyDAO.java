package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 5/4/2017.
 */

public class CompanyDAO {
    String id = "";
    String company_name = "";
    String state_id="";
    String gst_no="";
    String address="";
    String state_name="";
    String city_name="";



    public CompanyDAO() {

    }


    public CompanyDAO(String id, String company_name, String state_id, String gst_no, String address, String state_name, String city_name) {
        this.id = id;
        this.company_name = company_name;
        this.state_id = state_id;
        this.gst_no = gst_no;
        this.address = address;
        this.state_name = state_name;
        this.city_name = city_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getGst_no() {
        return gst_no;
    }

    public void setGst_no(String gst_no) {
        this.gst_no = gst_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return company_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompanyDAO) {
            CompanyDAO c = (CompanyDAO) obj;
            if (c.getCompany_name().equals(company_name))
                return true;
        }

        return false;
    }
}
