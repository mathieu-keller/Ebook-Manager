import { defineConfig } from 'vite';
import solidPlugin from 'vite-plugin-solid';

export default defineConfig({
  plugins: [solidPlugin()],
  build: {
    target: 'esnext',
  },
  server: {
    port: 3000,
    proxy: {
      '^/api/.*': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
});
