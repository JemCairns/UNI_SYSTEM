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
    private int topic_ID;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    public Topic() {
        super();
    }

    public Topic(int topic_ID, String title, String description) {
        this.topic_ID = topic_ID;
        this.title = title;
        this.description = description;
    }

    public int getTopic_ID() { return topic_ID; }

    public void setTopic_ID(int topic_ID) {this.topic_ID = topic_ID;}

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
