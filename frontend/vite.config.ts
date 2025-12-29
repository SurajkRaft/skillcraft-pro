import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // Forward all requests starting with /api to your Spring Boot backend
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});
