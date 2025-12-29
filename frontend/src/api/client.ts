import axios from "axios";

// This client will automatically prefix requests with /api
// thanks to the proxy in vite.config.ts
const apiClient = axios.create({
  baseURL: "/api", // This gets forwarded to http://localhost:8080/api
  headers: {
    "Content-Type": "application/json",
  },
});

export default apiClient;
