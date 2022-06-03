package team1.spring.training.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "upload_file")
public class UploadFile {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    @Column(name = "id")
    private long id;

    @JsonProperty("timestamp")
    @Column(name = "timestamp")
    private long timestamp;

    @JsonProperty("location")
    @Column(name = "location")
    private String location;

    @JsonProperty("fileName")
    @Column(name = "file_name")
    private String fileName;

    @JsonProperty("user")
    @Column(name = "user")
    private String user;

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFileName() {
        return fileName;
    }

    public String getLocation() {
        return location;
    }

    public String getUser() {
        return user;
    }

    public UploadFile(long timestamp, String location, String fileName) {
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.location = location;
    }

    public UploadFile(long id, long timestamp, String location, String fileName) {
        this.id = id;
        this.timestamp = timestamp;
        this.location = location;
        this.fileName = fileName;
    }

    public UploadFile(long timestamp, String location, String fileName, String user) {
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.location = location;
        this.user = user;
    }

    public UploadFile() {
    }
}
