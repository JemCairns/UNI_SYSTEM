package uni.system.webapp.tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "student")
public class Student {
    @Id
    private String ID;
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
    private double fees_paid;
    @NotNull
    private double fees_due;
    @NotNull
    private Date date_of_birth;
    @NotNull
    private String stage;

    public Student() {
        super();
    }

    public Student(String ID, String first_name, String last_name, String address,
                   String phone_number, String email, String gender, double fees_due, double fees_paid,
                   Date date_of_birth, String stage) {
        this.ID = ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.gender = gender;
        this.fees_paid = fees_paid;
        this.fees_due = fees_due;
        this.date_of_birth = date_of_birth;
        this.stage = stage;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public double getFees_paid() {
        return fees_paid;
    }

    public void setFees_paid(double fees_paid) {
        this.fees_paid = fees_paid;
    }

    public double getFees_due() {
        return fees_due;
    }

    public void setFees_due(double fees_due) {
        this.fees_due = fees_due;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
