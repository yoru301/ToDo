const express = require("express");
const http = require("http");
const bodyParser = require("body-parser");
const path = require("path"); // Import path module

const app = express();
const port = 3000;

app.use(bodyParser.json()); // Middleware to parse JSON request bodies

// Serve static files from the 'public' directory
app.use(express.static(path.join(__dirname, "public")));

// Redirect to backend's Microsoft login
app.get("/login/microsoft", (req, res) => {
  res.redirect("http://localhost:8888/api/v1/oauth2/authorization/microsoft");
});

// Proxy endpoint to fetch user info from backend
app.get("/user", (req, res) => {
  fetch("http://localhost:8888/api/v1/user", {
    method: "GET",
    credentials: "include", // Ensure cookies are forwarded
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((data) => res.json(data))
    .catch((error) => {
      console.error("Error fetching user info:", error);
      res.status(500).send("Failed to fetch user info");
    });
});

// Start the server
app.listen(port, () => {
  console.log(`Frontend listening at http://localhost:${port}`);
});
