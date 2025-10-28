import 'bootstrap/dist/css/bootstrap.min.css';
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import LoginForm from "./components/LoginForm.tsx";

const router = createBrowserRouter([{
    path:'/',
    element:<LoginForm />,
    errorElement: <div>404 not found</div>
},
    ]);

const queryClient = new QueryClient()

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <QueryClientProvider client={queryClient}>
    <RouterProvider router={router} />
      </QueryClientProvider>
  </StrictMode>,
)
