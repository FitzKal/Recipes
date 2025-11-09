
import './styles/main.css';

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import {createBrowserRouter, Navigate, RouterProvider} from 'react-router-dom';
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import LoginForm from "./components/LoginForm.tsx";
import RegisterForm from "./components/RegisterForm.tsx";
import App from "./App.tsx";
import Homepage from "./components/Homepage.tsx";
import Navbar from "./components/Navbar.tsx";

const router = createBrowserRouter([{
        path:'/',
        element:<Navigate to={"/login"} />,
        errorElement: <div>404 not found</div>
    },
    {
      path:'/login',
      element:<LoginForm/>
    },
    {
        path:"/register",
        element:<RegisterForm />
    },
    {
      path: "/recipes",
      element:<Navigate to={"/login"} />
    },
    {
        path:"/dashboard",
        element: <Navbar />,
        children: [
            {
                path:"/dashboard/home",
                element:<Homepage/>
            }
        ]

    }
    ]);

const queryClient = new QueryClient()

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <QueryClientProvider client={queryClient}>
    <RouterProvider router={router} />
          <App />
      </QueryClientProvider>
  </StrictMode>,
)
