package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 5/4/2017.
 */

public class DepartmentDAO {
    String id = "";
    String dept_name = "";

    public DepartmentDAO() {

    }

    public DepartmentDAO(String id, String dept_name) {
        this.id = id;
        this.dept_name = dept_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    @Override
    public String toString() {
        return dept_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DepartmentDAO) {
            DepartmentDAO c = (DepartmentDAO) obj;
            if (c.getDept_name().equals(dept_name))
                return true;
        }

        return false;
    }
}
