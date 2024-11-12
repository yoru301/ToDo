package si.um.feri.vaja.vao;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")  // Maps this entity to the 'tasks' table in your database
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates unique IDs for each task
    private Long id;

    @Column(name = "title", nullable = false)  // Maps to 'title' column; cannot be null
    private String title;

    @Column(name = "end_date")  // Maps to 'end_date' column
    private LocalDate endDate;

    @Column(name = "description")  // Maps to 'description' column
    private String description;

    @Column(name = "status")  // Maps to 'status' column
    private String status;

    // Default constructor (required by JPA)
    public Task() {
    }

    // Constructor with parameters (including the new status field)
    public Task(String title, LocalDate endDate, String description, String status) {
        this.title = title;
        this.endDate = endDate;
        this.description = description;
        this.status = status;
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

    // Override toString to include status for easier task detail printing
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}