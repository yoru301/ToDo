let tasksData = [];

function pridobiPodatke(filters = {}) {
  // Osnovni URL za pridobivanje nalog
  let url = "http://localhost:8888/api/v1/tasks/filter";

  // Dodajanje filtrov v URL
  if (Object.keys(filters).length > 0) {
      const queryParams = new URLSearchParams(filters).toString();
      url += `?${queryParams}`;
  }

  // Pošiljanje zahteve na strežnik
  fetch(url, {
      method: "GET",
      credentials: "include",
      headers: {
          "Content-Type": "application/json",
      },
  })
      .then((response) => {
          if (!response.ok) {
              throw new Error(`Napaka pri komunikaciji s strežnikom: ${response.status} ${response.statusText}`);
          }
          return response.json();
      })
      .then((data) => {
          console.log("Prejeti podatki:", data);
          // Posodobite prikaz nalog na strani
          renderTaskList(data); // Predpostavljam, da imate funkcijo za prikaz nalog
      })
      .catch((error) => {
          console.error("Error fetching tasks:", error);
      });
}

// Render task list
function renderTaskList(tasks) {
  const taskList = document.getElementById("myUL");
  taskList.innerHTML = ""; // Clear the list before re-rendering tasks

  if (tasks.length === 0) {
    const noTasksMessage = document.createElement("p");
    noTasksMessage.textContent = "Trenutno ni nalog.";
    noTasksMessage.className = "text-center text-muted";
    taskList.appendChild(noTasksMessage);
    return;
  }

  tasks.forEach((task) => {
    const listItem = document.createElement("li");
    listItem.className = "list-group-item d-flex flex-column p-3";

    const taskContent = document.createElement("div");
    taskContent.className = "task-content bg-white p-3 rounded";

    // Title
    const titleDiv = document.createElement("div");
    titleDiv.className = "task-title fw-bold mb-2";
    titleDiv.style.fontSize = "1.25rem";
    titleDiv.textContent = task.title || "Brez naslova";

    // Description
    const descriptionDiv = document.createElement("div");
    descriptionDiv.className = "task-description mb-2";
    descriptionDiv.textContent = `Opis: ${task.description || "Brez opisa"}`;

    // Status
    const statusDiv = document.createElement("div");
    statusDiv.className = "task-status mb-2";
    statusDiv.textContent = `Status: ${task.status || "Neznan status"}`;

    // End Date and Priority Row
    const endDatePriorityDiv = document.createElement("div");
    endDatePriorityDiv.className = "d-flex justify-content-between mb-2";

    // End Date
    const endDateDiv = document.createElement("div");
    endDateDiv.className = "task-end-date";
    endDateDiv.textContent = `Rok Naloge: ${task.endDate || "Ni določen"}`;

    // Priority
    const priorityDiv = document.createElement("div");
    priorityDiv.className = "task-priority";
    priorityDiv.textContent = `Prioriteta: ${task.priority || "Ni določena"}`;

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
      const fileName = task.attachmentPath.split("/").pop();
      const downloadUrl = `http://localhost:8888/api/v1/tasks/download/${fileName}`;

      const attachmentLink = document.createElement("a");
      attachmentLink.href = downloadUrl;
      attachmentLink.textContent = "Prenesi Priponko";
      attachmentLink.target = "_blank";
      attachmentDiv.appendChild(attachmentLink);
    } else {
      attachmentDiv.textContent = "Ni Priponke";
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

  // Handle logout button
  document.getElementById("logoutButton").addEventListener("click", () => {
    // Preusmeri uporabnika na Microsoftovo URL za odjavo
    window.location.href = "https://login.microsoftonline.com/common/oauth2/v2.0/logout?post_logout_redirect_uri=http://localhost:3000/";
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

  // Odtukaj odstranite "mode: 'no-cors'" in omogočite CORS na strežniku
  fetch("http://localhost:8888/api/v1/tasks", {
    method: "POST",
    body: formData, // Send the FormData with file and task data
    credentials: "include", // Include cookies for authentication if needed
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json(); // Parse JSON response from server
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



document.getElementById("logoutButton").addEventListener("click", () => {
  // Odjava uporabnika preko backend API-ja
  fetch("http://localhost:8888/logout", {
    method: "GET",
    credentials: "include"  // Poskrbi, da so piškotki vključeni
  })
  .then(() => {
    document.getElementById("user-name").textContent = "Uporabnik ni prijavljen.";
    document.getElementById("loginButton").style.display = "inline-block";  // Pokaži gumb za prijavo
    document.getElementById("logoutButton").style.display = "none";  // Skrij gumb za odjavo
    // Po uspešni odjavi bo uporabnik preusmerjen nazaj na prijavno stran
    window.location.href = "http://localhost:8888/api/v1/oauth2/authorization/microsoft";
    
  })
  .catch((error) => {
    console.error("Napaka pri odjavi:", error);
  });
});



document.addEventListener("DOMContentLoaded", function() {
  fetch("http://localhost:8888/api/v1/user", {
    method: "GET",
    credentials: "include", // Preveri sejo uporabnika (piškotke)
  })
  .then(response => response.json())
  .then(data => {
    if (data.name) {
      document.getElementById("user-name").textContent = `Pozdravljeni, ${data.name}`;
      document.getElementById("loginButton").style.display = "none";  // Skrij gumb za prijavo
    } else {
      document.getElementById("user-name").textContent = "Uporabnik ni prijavljen.";
      document.getElementById("loginButton").style.display = "inline-block";  // Pokaži gumb za prijavo
      document.getElementById("logoutButton").style.display = "none";  // Skrij gumb za odjavo
    }
  })
  .catch((error) => {
    console.error("Napaka pri pridobivanju podatkov o uporabniku:", error);
  });
});

