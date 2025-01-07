package si.um.feri.vaja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import si.um.feri.vaja.dao.TaskRepository;
import si.um.feri.vaja.vao.Task;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class VajaController {

    @Autowired
    private TaskRepository taskRepository;

    // POST (create) a new task with file upload
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        // Now the Task object will be populated from the JSON request body
        task.setEndDate(LocalDate.parse(task.getEndDate().toString())); // Ensure proper conversion if needed
        task.setPriority(Integer.parseInt(task.getPriority().toString())); // Convert priority to integer if it's a string

        // Save the task to the database
        return taskRepository.save(task);
    }

    // Simple hello endpoint to check if backend is running
    @GetMapping("/hello")
    public String hello() {
        return "Backend";
    }

    // GET all tasks
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // GET a task by ID
    @GetMapping("/tasks/{id}")
    public Optional<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id);
    }

    // PUT (update) a task by ID
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setEndDate(updatedTask.getEndDate());
                    task.setStatus(updatedTask.getStatus());
                    task.setPriority(updatedTask.getPriority()); // Add this line to update the priority
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    // DELETE a task by ID
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

    // GET all tasks with optional filtering
    @GetMapping("/tasks/filter")
    public List<Task> getFilteredTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Integer priority) {

        // Apply filters based on the combinations
        if (title != null && endDate != null && priority != null) {
            return taskRepository.findByTitleContainingIgnoreCaseAndEndDateAndPriority(title, endDate, priority);
        } else if (title != null && endDate != null) {
            return taskRepository.findByTitleContainingIgnoreCaseAndEndDate(title, endDate);
        } else if (title != null && priority != null) {
            return taskRepository.findByTitleContainingIgnoreCaseAndPriority(title, priority);
        } else if (title != null) {
            return taskRepository.findByTitleContainingIgnoreCase(title);
        } else if (endDate != null && priority != null) {
            return taskRepository.findByEndDateAndPriority(endDate, priority);
        } else if (endDate != null) {
            return taskRepository.findByEndDate(endDate);
        } else if (priority != null) {
            return taskRepository.findByPriority(priority);
        } else {
            return taskRepository.findAll();
        }
    }


}
