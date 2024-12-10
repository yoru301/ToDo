package si.um.feri.vaja.vao;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "attachment_path") // New field to store the path
    private String attachmentPath;

    public Task() {

    }

    // Getters and setters for new field
    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
    // Getters and Setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Task(String title, LocalDate endDate, String description, String status, Integer priority, String attachmentPath) {
        this.title = title;
        this.endDate = endDate;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.attachmentPath = attachmentPath; // Initialize the attachmentPath
    }



    // Override toString to include status for easier task detail printing

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", attachmentPath='" + attachmentPath + '\'' +
                '}';
    }
}