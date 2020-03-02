package uni.system.webapp.tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "module")
public class Module implements Serializable {
    @Id
    private String ID;
    @NotBlank
    private String name;
    @NotBlank
    private String coordinator_ID;
    @NotNull
    private int curr_num_students;
    @NotNull
    private int max_num_students;
    @NotBlank
    private String status;
    @NotBlank
    private String description;

    public Module() {
        super();
    }

    public Module(String ID, String name, String coordinator_ID, int curr_num_students, int max_num_students, String status, String description) {
        this.ID = ID;
        this.name = name;
        this.coordinator_ID = coordinator_ID;
        this.curr_num_students = curr_num_students;
        this.max_num_students = max_num_students;
        this.status = status;
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinator_ID() {
        return coordinator_ID;
    }

    public void setCoordinator_ID(String coordinator_ID) {
        this.coordinator_ID = coordinator_ID;
    }

    public int getCurr_num_students() { return this.curr_num_students; }

    public void setCurr_num_students(int curr_num_students) { this.curr_num_students = curr_num_students; }

    public int getMax_num_students() {
        return max_num_students;
    }

    public void setMax_num_students(int max_num_students) {
        this.max_num_students = max_num_students;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
