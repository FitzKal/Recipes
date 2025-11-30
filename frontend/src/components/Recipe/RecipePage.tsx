
import {useParams} from "react-router-dom"
import {useQuery} from "@tanstack/react-query";
import {userStore} from "../../Stores/UserStore.ts";
import {useEffect} from "react";
import {toast} from "react-toastify";
import {getRecipeById} from "../../service/RecipeService.ts";


export default function RecipePage(){
    const {param} =  useParams();
    const id = param !== undefined ? parseInt(param) : undefined;
    const currentUser =userStore.getState().user;
    const {data, error, isError, isLoading} = useQuery({
        queryKey:["RecipeById"],
        queryFn: async () =>{
            if (!currentUser?.accessToken){
                throw new Error("Authenticaion failed");
            }
            return await getRecipeById(currentUser.accessToken,id);
        },
        enabled:!!currentUser?.accessToken
    });

    useEffect(()=>{
        if (isError && error instanceof Error){
            toast.error(error.message);
        }
    },[isError,error])


    return isLoading ? (
        <p>Loading...</p>
    ): (
        <div className={"flex flex-col"}>
            <h1 className={"text-4xl text-center text-white mb-8 mt-8"}>{data.recipeTitle}</h1>
            <div className={"flex justify-center mb-3"}>
                <img className={"h-120 w-80 transition delay-150 duration-300 ease-in-out hover:scale-130"} src={data.pictureSrc} alt={data.recipeTitle}/>
            </div>
            <div className={"text-center text-white flex flex-col gap-1"}>
                <h2 className={"text-center text-white text-xl"}>Written by: <strong className={"text-white font-bold"}>{data.author}</strong></h2>
                <p>Genre: <strong className={"text-white font-bold"}>{data.category}</strong></p>
                <p>Added by: <strong className={"text-white font-bold"}>{data.username}</strong></p>
            </div>
            <div className={"border-t border-white w-[60%] m-auto mt-10"}></div>
            <div className={"mt-5 ml-10 mr-10 mb-20"}>
                <h2 className={"text-2xl text-white mt-5"}><strong>Description:</strong></h2>
                <p className={"text-white"}>{data.description}</p>
                <h2 className={"text-2xl text-white mt-5"}><strong>Ingredients:</strong></h2>
                <p className={"text-white"}>{data.ingredients}</p>
                <h2 className={"text-2xl text-white mt-5"}><strong>Instructions:</strong></h2>
                <p className={"text-white"}>{data.instructions}</p>
            </div>

        </div>
    );
}