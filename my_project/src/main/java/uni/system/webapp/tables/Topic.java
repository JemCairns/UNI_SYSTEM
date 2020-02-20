package uni.system.webapp.tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "topic")
public class Topic implements Serializable {
    @Id
    private int ID;
    @NotBlank
    private String module_ID;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    public Topic() {
        super();
    }

    public Topic(String module_ID, String title, String description) {
        this.module_ID = module_ID;
        this.title = title;
        this.description = description;
    }

    public String getModule_ID() {
        return module_ID;
    }

    public void setModule_ID(String module_ID) {
        this.module_ID = module_ID;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
