package uni.system.webapp.tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "topic_registration")
public class TopicRegistration implements Serializable {
    @Id
    private int reg_ID;
    @NotBlank
    private String module_ID;
    @NotBlank
    private int topic_ID;

    public TopicRegistration() {
        super();
    }

    public TopicRegistration(int reg_ID, String module_ID, int topic_ID) {
        this.reg_ID = reg_ID;
        this.module_ID = module_ID;
        this.topic_ID = topic_ID;
    }

    public int getReg_ID() {
        return reg_ID;
    }

    public void setReg_ID(int reg_ID) {
        this.reg_ID = reg_ID;
    }

    public String getModule_ID() {
        return module_ID;
    }

    public void setModule_ID(String module_ID) {
        this.module_ID = module_ID;
    }

    public int getTopic_ID() {
        return topic_ID;
    }

    public void setTopic_ID(int topic_ID) {
        this.topic_ID = topic_ID;
    }
}
