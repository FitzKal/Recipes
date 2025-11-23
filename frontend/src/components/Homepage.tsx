import {userStore} from "../Stores/UserStore.ts";
import {useNavigate} from "react-router-dom";
import {useMutation} from "@tanstack/react-query";
import "../styles/Homepage.css";
import {toast} from "react-toastify";
import {userLogout} from "../service/AuthServices.ts";


export default function Homepage(){
    const navigate = useNavigate();
    useMutation({
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
    });
    return(
            <div>
                <div className="bg-gray-800 text-white border-b border-white/10 mx-auto mt-10 w-[400px] ">
                    <div className="relative">
                        <input
                            className="text-[16px] w-full bg-transparent placeholder:text-slate-400 text-slate-700 text-sm border border-slate-200 rounded-md pl-3 pr-28 py-2 transition duration-300 ease focus:outline-none focus:border-slate-400 hover:border-slate-300 shadow-sm focus:shadow"
                            placeholder="Search in all recipes"
                        />
                        <button
                            className="text-[16px] absolute top-1 right-1 flex items-center rounded bg-[#2C4278] py-1 px-2.5 border border-transparent text-center text-sm text-white transition-all shadow-sm hover:shadow focus:bg-[#3A5A9C] focus:shadow-none active:bg-[#3A5A9C] hover:bg-[#3A5A9C] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            type="button"
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor"
                                 className="w-4 h-4 mr-2">
                                <path fill-rule="evenodd"
                                      d="M10.5 3.75a6.75 6.75 0 1 0 0 13.5 6.75 6.75 0 0 0 0-13.5ZM2.25 10.5a8.25 8.25 0 1 1 14.59 5.28l4.69 4.69a.75.75 0 1 1-1.06 1.06l-4.69-4.69A8.25 8.25 0 0 1 2.25 10.5Z"
                                      clip-rule="evenodd"/>
                            </svg>

                            Search
                        </button>
                    </div>
                </div>
                <div className={"border-t border-white w-[80%] m-auto mt-10 mb-10"}></div>
            </div>
)
}