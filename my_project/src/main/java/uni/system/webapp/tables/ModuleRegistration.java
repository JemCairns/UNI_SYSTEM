package uni.system.webapp.tables;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "module_registration")
public class ModuleRegistration {
    //StudentID
    //ModuleID
    //LetterGrade
    //percent
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @NotBlank
    private String student_ID;
    @NotBlank
    private String module_ID;
    @NotBlank
    private String letterGrade;
    @NotNull
    private double percentage;

    public ModuleRegistration() { super(); }

    public ModuleRegistration(int id,String student_ID, String module_ID, String letterGrade, double percentage) {
        this.id = id;
        this.student_ID = student_ID;
        this.module_ID = module_ID;
        this.letterGrade = letterGrade;
        this.percentage = percentage;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getStudent_ID() { return student_ID; }

    public void setStudent_ID(String student_ID) { this.student_ID = student_ID; }

    public String getModule_ID() { return module_ID; }

    public void setModule_ID(String module_ID) { this.module_ID = module_ID; }

    public String getLetterGrade() { return letterGrade; }

    public void setLetterGrade(String letterGrade) { this.letterGrade = letterGrade; }

    public double getPercentage() { return percentage; }

    public void setPercentage(double percentage) { this.percentage = percentage; }
}
