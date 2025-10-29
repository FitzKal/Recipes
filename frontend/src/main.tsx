import 'bootstrap/dist/css/bootstrap.min.css';
import './styles/main.css';

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import LoginForm from "./components/LoginForm.tsx";
import RegisterForm from "./components/RegisterForm.tsx";
import App from "./App.tsx";


const router = createBrowserRouter([{
    path:'/',
    element:<LoginForm />,
    errorElement: <div>404 not found</div>
},
    {
        path:"/register",
        element:<RegisterForm />
    },
    {
      path: "/recipes",
      element:<App />
    }
    ]);

const queryClient = new QueryClient()

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <QueryClientProvider client={queryClient}>
    <RouterProvider router={router} />
      </QueryClientProvider>
  </StrictMode>,
)
