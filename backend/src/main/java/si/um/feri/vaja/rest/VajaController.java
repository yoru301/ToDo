package si.um.feri.vaja.rest;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import si.um.feri.vaja.dao.TaskRepository;
import si.um.feri.vaja.vao.Task;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000") // Omogoči dostop iz frontend naslova
public class VajaController {

    @Autowired
    private TaskRepository taskRepository;

    @Value("${file.upload-dir}")
    private String uploadDir; // Path to the upload directory from application.properties

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    // Endpoint to handle OAuth2 login callback (redirect from Microsoft)
    @GetMapping("/login/oauth2/code/microsoft")
    public String handleLoginCallback(@RequestParam("code") String code,
                                      @AuthenticationPrincipal OAuth2AuthenticationToken authentication,
                                      Model model) {
        // Get the OAuth2User from the authentication token
        OAuth2User user = authentication.getPrincipal();  // Get the user's information (e.g., name)

        // Load the OAuth2AuthorizedClient using the client registration id (e.g., "microsoft") and the name of the user (e.g., "user")
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                "microsoft", authentication.getName());

        // Get the OAuth2 access token (if needed)
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

        // Add user info to the model
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("accessToken", accessToken.getTokenValue());

        // Return a view (you can customize the view here)
        return "home"; // For example, a home page that shows the user info
    }

    // Login page (you can customize it)
    @GetMapping("/login")
    public String login() {
        return "login"; // Returns login page (or you can redirect to Microsoft's OAuth2 login page)
    }

    // Logout
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Invalidiraj trenutno sejo uporabnika
        request.getSession().invalidate();

        // Invalidiraj OAuth2 piškotke (po potrebi)
        Cookie oauthCookie = new Cookie("oauth_token", null);
        oauthCookie.setMaxAge(0);  // Takoj izniči piškotek
        oauthCookie.setPath("/");   // Poti naj bo /, da piškotek izbrišeš povsod
        response.addCookie(oauthCookie);

        // Preusmeri uporabnika na Microsoftov logout URL
        String logoutUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/logout?post_logout_redirect_uri=http://localhost:8888/api/v1/oauth2/authorization/microsoft";
        response.sendRedirect(logoutUrl);  // Preusmeri uporabnika na Microsoftov logout URL
    }





    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> getUser(Authentication authentication) {
        // Preveri, če je uporabnik prijavljen
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Uporabnik ni prijavljen
        }

        // Pridobi ime uporabnika
        String userName = authentication.getName();

        // Lahko tudi vrneš več podatkov, kot so email itd.
        Map<String, String> response = new HashMap<>();
        response.put("name", userName); // Lahko dodaš tudi druge podatke
        return ResponseEntity.ok(response);
    }

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
    @CrossOrigin(origins = "http://localhost:3000")  // Omogoči CORS za DELETE metodo
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
