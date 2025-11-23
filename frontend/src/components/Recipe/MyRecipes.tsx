import {userStore} from "../../Stores/UserStore.ts";
import {type MouseEventHandler, useEffect, useState} from "react";
import type {recipeType} from "../../Types/Recipe.ts";
import {useQuery} from "@tanstack/react-query";
import {getUserRecipe} from "../../service/RecipeService.ts";
import {toast} from "react-toastify";
import PostRecipeForm from "./PostRecipeForm.tsx";
import UpdateRecipeForm from "./UpdateRecipeForm.tsx";
import RecipeElement from "./RecipeElement.tsx";

export default function MyBooks(){
    const currentUser = userStore.getState().user;

    const [isPosting, setPosting] = useState<boolean>(false);
    const [isUpdating, setUpdating] = useState<boolean>(false);
    const [toUpdate, setToUpdate] = useState<recipeType>();

    const {data, error, isError, isLoading} = useQuery({
        queryKey: ["recipes"],
        queryFn: async () =>{
            if (!currentUser?.accessToken){
                throw new Error("Could not authenticate");
            }
            return await getUserRecipe(currentUser.accessToken);
        },
        enabled: !!currentUser?.accessToken,
    });

    useEffect(()=>{
        if (isError && error instanceof Error){
            toast.error(error.message);
        }
    },[isError,error])

    const handlePosting:MouseEventHandler<HTMLButtonElement> = () =>{
        if (!isPosting){
            setPosting(true);
        }else {
            setPosting(false);
        }
    }

    const handleUpdating = (recipe?:recipeType) =>{
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
            <div className={"flex flex-column justify-between px-50 "}>
                <h1 className={" text-white text-[36px] mt-10"}>My recipes</h1>
                <button className={"bg-[#2C4278] text-l text-white border-2 rounded-xl pl-8 pr-8 pt-1 pb-1  mt-13 ml-20 transition delay-75 ease-in-out hover:bg-[#3A5A9C]"}
                        onClick={handlePosting}>Add Recipe</button>
            </div>
                {(isPosting) ? <PostRecipeForm /> : <></>}
                {(isUpdating)?<UpdateRecipeForm recipeInfo={toUpdate} manageEditing = {handleUpdating}/>:<></>}
            <div className={"border-t border-white w-[80%] m-auto mt-5 mb-5"}></div>
            <div className={"flex flex-wrap justify-center flex-row gap-15 m-10 p-10"}>
                {data.map((recipe:recipeType) =>
                    <RecipeElement key={recipe.id} recipe={recipe} setUpdating={handleUpdating} />
                )}
            </div>
        </div>
    );
}