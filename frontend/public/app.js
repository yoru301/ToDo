let tasksData = [];

// Fetch tasks data from the backend
function pridobiPodatke(filters = {}) {
  const url = new URL("http://localhost:8888/api/v1/tasks/filter");

  // If filters exist, add them to the URL
  if (filters.title) url.searchParams.append("title", filters.title);
  if (filters.endDate) url.searchParams.append("endDate", filters.endDate);
  if (filters.priority) url.searchParams.append("priority", filters.priority);

  // Fetch tasks from the backend with possible filters
  fetch(url)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      tasksData = data;
      renderTaskList(data);
    })
    .catch((error) => {
      console.error("Error fetching tasks:", error);
    });
}

// Render task list
function renderTaskList(tasks) {
  const taskList = document.getElementById("myUL");
  taskList.innerHTML = ""; // Clear the list before re-rendering tasks

  tasks.forEach((task) => {
    const listItem = document.createElement("li");
    listItem.className = "list-group-item d-flex flex-column p-3";

    const taskContent = document.createElement("div");
    taskContent.className = "task-content bg-white p-3 rounded";

    // Title
    const titleDiv = document.createElement("div");
    titleDiv.className = "task-title fw-bold mb-2";
    titleDiv.style.fontSize = "1.25rem";
    titleDiv.textContent = `${task.title}`;

    // Description
    const descriptionDiv = document.createElement("div");
    descriptionDiv.className = "task-description mb-2";
    descriptionDiv.textContent = `Opis: ${task.description}`;

    // Status
    const statusDiv = document.createElement("div");
    statusDiv.className = "task-status mb-2";
    statusDiv.textContent = `Status: ${task.status}`;

    // End Date and Priority Row
    const endDatePriorityDiv = document.createElement("div");
    endDatePriorityDiv.className = "d-flex justify-content-between mb-2";

    // End Date
    const endDateDiv = document.createElement("div");
    endDateDiv.className = "task-end-date";
    endDateDiv.textContent = `Rok Naloge: ${task.endDate}`;

    // Priority
    const priorityDiv = document.createElement("div");
    priorityDiv.className = "task-priority";
    priorityDiv.textContent = `Prioriteta: ${task.priority}`;

    endDatePriorityDiv.appendChild(endDateDiv);
    endDatePriorityDiv.appendChild(priorityDiv);

    const buttonsDiv = document.createElement("div");
    buttonsDiv.className = "mt-2 d-flex justify-content-end";

    // Delete button
    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Delete";
    deleteButton.className = "btn btn-danger btn-sm";
    deleteButton.onclick = () => deleteTask(task.id);

    // Edit button
    const editButton = document.createElement("button");
    editButton.textContent = "Edit";
    editButton.className = "btn btn-secondary btn-sm ms-2";
    editButton.onclick = () => openEditForm(task);

    buttonsDiv.appendChild(editButton);
    buttonsDiv.appendChild(deleteButton);
    taskContent.appendChild(titleDiv);
    taskContent.appendChild(descriptionDiv);
    taskContent.appendChild(statusDiv);
    taskContent.appendChild(endDatePriorityDiv);

    // Attachments
    const attachmentDiv = document.createElement("div");
    attachmentDiv.className = "task-attachment mb-2";

    if (task.attachmentPath) {
      // Extract the file name from the attachmentPath (assuming it's something like '/uploads/myeurope.jpg')
      const fileName = task.attachmentPath.split("/").pop();

      // Construct the correct URL for downloading the file
      const downloadUrl = `http://localhost:8888/api/v1/tasks/download/${fileName}`; // Backend URL for downloading

      const attachmentLink = document.createElement("a");
      attachmentLink.href = downloadUrl; // Correct link to the file on the backend
      attachmentLink.textContent = "Download Attachment";
      attachmentLink.target = "_blank"; // Open in new tab
      attachmentDiv.appendChild(attachmentLink);
    } else {
      attachmentDiv.textContent = "No attachment";
    }

    taskContent.appendChild(attachmentDiv);
    listItem.appendChild(taskContent);
    listItem.appendChild(buttonsDiv);
    taskList.appendChild(listItem);
  });
}

// Initialize on DOM load
document.addEventListener("DOMContentLoaded", () => {
  pridobiPodatke(); // Fetch and display tasks

  // Filter tasks on search
  const searchInput = document.getElementById("search");
  searchInput.addEventListener("input", function () {
    const searchQuery = this.value.toLowerCase();
    const filteredTasks = tasksData.filter(
      (task) =>
        task.title.toLowerCase().includes(searchQuery) ||
        task.description.toLowerCase().includes(searchQuery) ||
        (task.status && task.status.toLowerCase().includes(searchQuery))
    );
    renderTaskList(filteredTasks);
  });
  document.getElementById("filterEndDate").addEventListener("change", applyFilters);
  document.getElementById("filterPriority").addEventListener("change", applyFilters);

  // Clear filters button
  document.getElementById("clearFiltersButton").addEventListener("click", () => {
    document.getElementById("filterEndDate").value = "";
    document.getElementById("filterPriority").value = "";
    document.getElementById("search").value = "";
    pridobiPodatke(); // Fetch and display tasks without filters
  });
});

// Create a new task
function createTask() {
  const title = document.getElementById("taskTitle").value;
  const endDate = new Date(document.getElementById("taskEndDate").value)
    .toISOString()
    .split("T")[0];
  const description = document.getElementById("taskDescription").value;
  const status = document.getElementById("taskStatus").value;
  const priority = document.getElementById("taskPriority").value; // Capture priority

  const attachment = document.getElementById("taskAttachment").files[0]; // Get the file

  if (!title || !endDate || !description || !status || !priority) {
    alert("Please fill in all fields.");
    return;
  }

  const formData = new FormData();
  formData.append("title", title);
  formData.append("endDate", endDate);
  formData.append("description", description);
  formData.append("status", status);
  formData.append("priority", priority);

  // If a file is selected, append it to FormData
  if (attachment) {
    formData.append("attachment", attachment);
  }

  // Log the form data to debug
  for (let [key, value] of formData.entries()) {
    console.log(key, value);
  }

  fetch("http://localhost:8888/api/v1/tasks", {
    method: "POST",
    body: formData, // Send the FormData with file and task data
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      console.log("Task created:", data);
      // Clear input fields
      document.getElementById("taskTitle").value = "";
      document.getElementById("taskEndDate").value = "";
      document.getElementById("taskDescription").value = "";
      document.getElementById("taskStatus").value = "";
      document.getElementById("taskPriority").value = "";
      document.getElementById("taskAttachment").value = ""; // Clear file input
      pridobiPodatke(); // Refresh task list after creating a task
    })
    .catch((error) => {
      console.error("Error creating task:", error);
    });
}

// Delete a task
function deleteTask(taskId) {
  fetch(`http://localhost:8888/api/v1/tasks/${taskId}`, {
    method: "DELETE",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      pridobiPodatke(); // Refresh task list after deleting a task
    })
    .catch((error) => {
      console.error("Error deleting task:", error);
    });
}

let currentTaskId = null;

// Open the edit form with existing task details
function openEditForm(task) {
  currentTaskId = task.id;
  document.getElementById("editTaskTitle").value = task.title;
  document.getElementById("editTaskEndDate").value = task.endDate;
  document.getElementById("editTaskDescription").value = task.description;
  document.getElementById("editTaskStatus").value = task.status;
  document.getElementById("editTaskPriority").value = task.priority; // Set priority

  const editModal = new bootstrap.Modal(document.getElementById("editModal"));
  editModal.show();
}

// Close the edit form
function closeEditForm() {
  const editModal = bootstrap.Modal.getInstance(document.getElementById("editModal"));
  editModal.hide();
}

// Update an existing task
function updateTask() {
  const title = document.getElementById("editTaskTitle").value;
  const endDate = document.getElementById("editTaskEndDate").value;
  const description = document.getElementById("editTaskDescription").value;
  const status = document.getElementById("editTaskStatus").value;
  const priority = document.getElementById("editTaskPriority").value; // Capture priority

  if (!title || !endDate || !description || !status || !priority) {
    alert("Please fill in all fields.");
    return;
  }

  const updatedTask = {
    title: title,
    endDate: endDate,
    description: description,
    status: status,
    priority: priority, // Include priority
  };

  fetch(`http://localhost:8888/api/v1/tasks/${currentTaskId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(updatedTask),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      closeEditForm();
      pridobiPodatke(); // Refresh task list after updating
    })
    .catch((error) => {
      console.error("Error updating task:", error);
    });
}

// Apply filters
function applyFilters() {
  const endDate = document.getElementById("filterEndDate").value;
  const priority = document.getElementById("filterPriority").value;
  const title = document.getElementById("search").value;

  const filters = {};
  if (title) filters.title = title;
  if (endDate) filters.endDate = endDate;
  if (priority) filters.priority = priority;

  pridobiPodatke(filters);
}

document.addEventListener("DOMContentLoaded", () => {
  pridobiPodatke(); // Fetch and display tasks
});

// Inicializacija Google API knjižnice
function startApp() {
  gapi.load("auth2", function () {
    gapi.auth2
      .init({
        client_id: "ID",
      })
      .then(function () {
        // Prikaz gumba za prijavo
        document.getElementById("google-signin-btn").style.display = "block";
      });
  });
}

// Funkcija za prijavo
function signIn() {
  var auth2 = gapi.auth2.getAuthInstance();
  auth2.signIn().then(function (googleUser) {
    var id_token = googleUser.getAuthResponse().id_token;
    // Tukaj pošlji id_token na backend za nadaljnjo obdelavo
    console.log(id_token);
    // Primer pošiljanja na backend
    fetch(`http://localhost:8888/api/v1/auth/google/callback`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        token: id_token,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Logged in successfully:", data);
        // Nadaljnje obdelovanje po prijavi
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  });
}

// Povezava gumba za prijavo
document.getElementById("google-signin-btn").addEventListener("click", signIn);

// Inicializacija aplikacije, ko je dokument pripravljen
window.onload = function () {
  startApp();
};
