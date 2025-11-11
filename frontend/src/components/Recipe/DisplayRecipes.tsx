import {userStore} from "../../Stores/UserStore.ts";
import {useQuery} from "@tanstack/react-query";
import {getAllRecipes} from "../../service/RecipeService.ts";
import {useEffect} from "react";
import {toast} from "react-toastify";
import type {recipeType} from "../../Types/Recipe.ts";
import RecipeElement from "./RecipeElement.tsx";

export default function DisplayRecipes(){
    const currentUser=  userStore.getState().user;

    const{data, error, isLoading, isError} = useQuery({
        queryKey:["recipes"],
        queryFn: async () =>{
            if (!currentUser?.accessToken){
                throw new Error("You are not authenticated");
            }
            return await getAllRecipes(currentUser.accessToken);
        },
        enabled: !!currentUser?.accessToken,
    });

    useEffect(() => {
        if (isError && error instanceof Error){
            toast.error(error.message);
        }
    }, [error,isError]);

    return isLoading ? (
        <p>Loading...</p>
    ) : (<div className={"m-auto"}>
        <div className={"flex flex-wrap justify-center flex-row gap-15 mt-10"}>
            {data.map((recipe:recipeType) =>
                <RecipeElement key={recipe.id} recipe={recipe} />
            )}
        </div>
    </div>);
}