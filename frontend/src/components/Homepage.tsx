import {userStore} from "../Stores/UserStore.ts";
import {Link, useNavigate} from "react-router-dom";
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
        <div className="flex flex-col items-center">

            <div className="flex justify-center">
                <div className="text-white text-[36px] m-10">
                    <h1>
                        Welcome {userStore().user?.username ?? "Guest"} !
                    </h1>
                </div>
            </div>

            <div className="border-t border-white w-[80%] m-auto"></div>

            <div className="flex flex-col items-center px-20 w-full m-20 ">
                <div className="text-white text-[20px] w-[80%] text-center">
                    <p>
                        Discover a world of fresh inspiration in our ever-growing collection of mouthwatering recipes. Whether you're craving something classic, contemporary, or perfectly seasonal, we bring you an up-to-date selection of dishes crafted to spark creativity in every kitchen. Browse through our diverse, flavor-packed ideas, find your next favorite meal, and let our modern, easy-to-follow recipes turn everyday cooking into something truly delightful. Bon appétit!
                    </p>
                </div>

                <div className="flex justify-center m-20">
                    <Link
                        to="/dashboard/recipes"
                        className="bg-[#2C4278] text-xl text-white border-2 rounded-xl px-8 py-1 transition delay-75 ease-in-out hover:bg-[#3A5A9C]">
                        Browse all recipes
                    </Link>
                </div>
            </div>

        </div>

    )
}