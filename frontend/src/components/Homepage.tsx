import {userStore} from "../Stores/UserStore.ts";
import {useNavigate} from "react-router-dom";
import {useMutation} from "@tanstack/react-query";
import "../styles/Homepage.css";
import {toast} from "react-toastify";
import type {MouseEventHandler} from "react";
import {userLogout} from "../service/AuthServices.ts";


export default function Homepage(){
    const navigate = useNavigate();
    const currentUser = userStore.getState().user;

    const logoutMutation = useMutation({
        mutationFn: () => {
            const token = userStore.getState().user?.accessToken;
            if (!token) {
                throw new Error("Not authenticated");
            }
            return userLogout(token);
        },
        onSuccess:() =>{
            userStore.getState().stateLogout();
            navigate("/login");
            toast.success("Successfully logged out!");
        },
        onError:(error) => {
            if (error instanceof Error){
                toast.error(error.message);

            }else{
                toast.error("Could not complete request");
            }
        }
    })
    const handleLogout: MouseEventHandler<HTMLButtonElement> = () => {
        logoutMutation.mutate();
    };

    return(
        <div>
            <h1 className={"text-[5rem] text-center mt-60"}>Welcome <strong className={"text-blue-700"}>{currentUser?.username ?? ""}</strong></h1>
            <div className={"flex justify-center mt-20"}>
                <button className={"LogOutButton"}
                        onClick={handleLogout}>Log out</button>
            </div>
        </div>
    )
}