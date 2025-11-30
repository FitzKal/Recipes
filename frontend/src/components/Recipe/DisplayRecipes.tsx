import {userStore} from "../../Stores/UserStore.ts";
import {useQuery} from "@tanstack/react-query";
import {getAllRecipes} from "../../service/RecipeService.ts";
import {type MouseEventHandler, useEffect, useState} from "react";
import {toast} from "react-toastify";
import type {Recipe, recipeType} from "../../Types/Recipe.ts";
import RecipeElement from "./RecipeElement.tsx";
import PostRecipeForm from "./PostRecipeForm.tsx";
import UpdateRecipeForm from "./UpdateRecipeForm.tsx";

export default function DisplayRecipes(){
    const currentUser=  userStore.getState().user;
    const [isPosting, setPosting] = useState<boolean>(false);
    const [isUpdating, setUpdating] = useState<boolean>(false);
    const [toUpdate, setToUpdate] = useState<Recipe>();


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

    const handlePosting:MouseEventHandler<HTMLButtonElement> = () =>{
        if (!isPosting){
            setPosting(true);
        }else {
            setPosting(false);
        }
    }

    const handleUpdating = (recipe?:Recipe) =>{
        if (!isUpdating){
            setUpdating(true);
            setToUpdate(recipe);
        }else {
            setUpdating(false);
        }
    }

    return isLoading ? (
        <p className={"text-white text-2xl"}>Loading...</p>
    ):(<div className={"m-auto mb-10 pb-10"}>
        <div className={"flex flex-column justify-around px-20 "}>
            <h1 className={" text-white text-[36px] mt-10"}>All recipes</h1>
            <button className={"bg-[#2C4278] text-l text-white border-2 rounded-xl px-8 py-1  mt-13 ml-20 transition delay-75 ease-in-out hover:bg-[#3A5A9C]"}
                    onClick={handlePosting}>Add Recipe</button>
        </div>
        {(isPosting) ? <PostRecipeForm /> : <></>}
        {(isUpdating)?<UpdateRecipeForm recipeInfo={toUpdate} manageEditing = {handleUpdating}/>:<></>}
        <div className={"border-t border-white w-[80%] m-auto mt-5 mb-5"}></div>
        <div className={"flex flex-wrap justify-center flex-row gap-15 m-10 p-10"}>
            {data.map((recipe:recipeType) =>
                <RecipeElement key={recipe.id} recipe={recipe} setUpdating={handleUpdating}/>
            )}
        </div>
    </div>);
}