package uni.system.login_stuff;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "student")
public class Student {
    @Id
    private String ID;
    @NotBlank
    private String password;
    @NotBlank
    private String first_name;
    @NotBlank
    private String last_name;
    @NotBlank
    private String address;
    @NotBlank
    private String phone_number;
    @NotBlank
    private String email;
    @NotBlank
    private String gender;
    @NotNull
    private int fees_paid;
    @NotNull
    private int fees_due;

    public Student() {
        super();
    }

    public Student(String ID, String password, String first_name, String last_name, String address,
                   String phone_number, String email, String gender, int fees_due, int fees_paid) {
        this.ID = ID;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.gender = gender;
        this.fees_paid = fees_paid;
        this.fees_due = fees_due;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_numer() {
        return phone_number;
    }

    public void setPhone_numer(String phone_numer) {
        this.phone_number = phone_numer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFees_paid() {
        return fees_paid;
    }

    public void setFees_paid(int fees_paid) {
        this.fees_paid = fees_paid;
    }

    public int getFees_due() {
        return fees_due;
    }

    public void setFees_due(int fees_due) {
        this.fees_due = fees_due;
    }
}
