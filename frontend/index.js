const express = require("express");
const http = require("http");
const bodyParser = require("body-parser");
const path = require("path"); // Import path module

const app = express();
const port = 3000;

app.use(bodyParser.json()); // Middleware to parse JSON request bodies

// Serve static files from the 'public' directory
app.use(express.static(path.join(__dirname, "public")));

// Start the server
app.listen(port, () => {
  console.log(`Frontend listening at http://localhost:${port}`);
});
