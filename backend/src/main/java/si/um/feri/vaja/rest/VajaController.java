package si.um.feri.vaja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import si.um.feri.vaja.dao.TaskRepository;
import si.um.feri.vaja.vao.Task;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class VajaController {

    @Autowired
    private TaskRepository taskRepository;

    @Value("${file.upload-dir}")
    private String uploadDir; // Path to the upload directory from application.properties

    // POST (create) a new task with file upload
    @PostMapping("/tasks")
    public Task createTask(@RequestParam("title") String title,
                           @RequestParam("endDate") String endDate,
                           @RequestParam("description") String description,
                           @RequestParam("status") String status,
                           @RequestParam("priority") String priority,
                           @RequestParam(value = "attachment", required = false) MultipartFile file) throws IOException {

        // Create a new Task object and populate it with the received data
        Task task = new Task();
        task.setTitle(title);
        task.setEndDate(LocalDate.parse(endDate));  // Ensure proper conversion to LocalDate
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(Integer.parseInt(priority)); // Convert priority to an integer

        // Handle file upload if provided
        if (file != null && !file.isEmpty()) {
            // Ensure the upload directory exists
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path); // Create directory if it doesn't exist
            }

            // Save the file to the upload directory
            String fileName = file.getOriginalFilename();
            Path filePath = path.resolve(fileName); // Define the file path
            Files.write(filePath, file.getBytes()); // Write the file content to disk
            System.out.println(fileName);
            // Store the file path in the task object
            task.setAttachmentPath(filePath.toString()); // Save the file path in the database
        }

        // Save the task to the database
        return taskRepository.save(task);
    }

    @GetMapping("/tasks/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        // Assuming the files are stored in a directory called 'uploads'
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Resource resource = new FileSystemResource(filePath);
System.out.println(fileName);
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, fileName)
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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

    @GetMapping("/user")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Map.of("error", "User is not authenticated");
        }
        return Map.of(
                "name", principal.getAttribute("name"),
                "email", principal.getAttribute("email"),
                "attributes", principal.getAttributes()
        );
    }

}
