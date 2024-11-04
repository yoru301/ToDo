let tasksData = [];

function pridobiPodatke() {
  fetch("http://localhost:8888/api/v1/tasks")
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

function renderTaskList(tasks) {
  const taskList = document.getElementById("myUL");
  taskList.innerHTML = "";

  tasks.forEach((task) => {
    const listItem = document.createElement("li");
    listItem.className = "list-group-item d-flex justify-content-between align-items-center";

    const taskContent = document.createElement("div");
    taskContent.className = "task-content";

    const titleDiv = document.createElement("div");
    titleDiv.className = "task-title";
    titleDiv.textContent = `Title: ${task.title}`;

    const descriptionDiv = document.createElement("div");
    descriptionDiv.className = "task-description";
    descriptionDiv.textContent = `|Description: ${task.description}`;

    const buttonsDiv = document.createElement("div");

    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Delete";
    deleteButton.className = "btn btn-danger btn-sm";
    deleteButton.onclick = () => deleteTask(task.id);

    const editButton = document.createElement("button");
    editButton.textContent = "Edit";
    editButton.className = "btn btn-secondary btn-sm ms-2";
    editButton.onclick = () => openEditForm(task);

    buttonsDiv.appendChild(editButton);
    buttonsDiv.appendChild(deleteButton);
    taskContent.appendChild(titleDiv);
    taskContent.appendChild(descriptionDiv);
    listItem.appendChild(taskContent);
    listItem.appendChild(buttonsDiv);
    taskList.appendChild(listItem);
  });
}

document.addEventListener("DOMContentLoaded", () => {
  pridobiPodatke();

  const searchInput = document.getElementById("search");
  searchInput.addEventListener("input", function () {
    const searchQuery = this.value.toLowerCase();
    const filteredTasks = tasksData.filter(
      (task) =>
        task.title.toLowerCase().includes(searchQuery) ||
        task.description.toLowerCase().includes(searchQuery)
    );
    renderTaskList(filteredTasks);
  });
});

function createTask() {
  const title = document.getElementById("taskTitle").value;
  const endDate = document.getElementById("taskEndDate").value;
  const description = document.getElementById("taskDescription").value;

  if (!title || !endDate || !description) {
    alert("Please fill in all fields.");
    return;
  }

  const newTask = {
    title: title,
    endDate: endDate,
    description: description,
  };

  fetch("http://localhost:8888/api/v1/tasks", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(newTask),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      console.log("Task created:", data);
      pridobiPodatke();
    })
    .catch((error) => {
      console.error("Error creating task:", error);
    });
}

function deleteTask(taskId) {
  fetch(`http://localhost:8888/api/v1/tasks/${taskId}`, {
    method: "DELETE",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      pridobiPodatke();
    })
    .catch((error) => {
      console.error("Error deleting task:", error);
    });
}

let currentTaskId = null;

function openEditForm(task) {
  currentTaskId = task.id;
  document.getElementById("editTaskTitle").value = task.title;
  document.getElementById("editTaskEndDate").value = task.endDate;
  document.getElementById("editTaskDescription").value = task.description;

  const editModal = new bootstrap.Modal(document.getElementById("editModal"));
  editModal.show();
}

function closeEditForm() {
  const editModal = bootstrap.Modal.getInstance(document.getElementById("editModal"));
  editModal.hide();
}

function updateTask() {
  const title = document.getElementById("editTaskTitle").value;
  const endDate = document.getElementById("editTaskEndDate").value;
  const description = document.getElementById("editTaskDescription").value;

  if (!title || !endDate || !description) {
    alert("Please fill in all fields.");
    return;
  }

  // Create a task object
  const updatedTask = {
    title: title,
    endDate: endDate,
    description: description,
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
      pridobiPodatke();
    })
    .catch((error) => {
      console.error("Error updating task:", error);
    });
}

document.addEventListener("DOMContentLoaded", () => {
  pridobiPodatke();
});
