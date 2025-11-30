import {userStore} from "../../Stores/UserStore.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import type {recipeType} from "../../Types/Recipe.ts";
import Recipe from "./Recipe.tsx";
import {deleteRecipe} from "../../service/RecipeService.ts";
import {toast} from "react-toastify";

export default function RecipeElement(recipeProp:{recipe:recipeType,setUpdating:(recipe:recipeType)=>void}){

    const currentUser = userStore.getState().user;
    const queryClient = useQueryClient();
    const canEdit = (!!currentUser && (currentUser.username === recipeProp.recipe.username || currentUser.role === "ADMIN"));
    const hasId = recipeProp.recipe.id != null;

    const deleteMutation = useMutation({
        mutationFn:(id: number) => {
            const token = userStore.getState().user?.accessToken;
            if (!token) {
                throw new Error("Not authenticated");
            }
            return deleteRecipe(token, id);
        },
        onSuccess:() => {
            toast.success("The chosen recipe has been deleted");
            queryClient.invalidateQueries({queryKey:["recipes"]});
        },
        onError:(error) =>{
            if (error instanceof Error){
                console.log(error.message);
                toast.error("The delete was not successful, you are not the owner of the recipe, or don't have the permission to delete the recipe");
            } else {
                toast.error("Something went wrong");
            }
        }
    })

    const handleDelete = (id:number) =>{
        console.log(id)
        deleteMutation.mutate(id);
    }

    return(<div>
        <Recipe recipeInfo={recipeProp.recipe}/>
        {hasId && (
            <button className={"text-l border-2 rounded-2xl pl-1 pr-1 w-20 bg-red-800 " +
                "text-white transition delay-50 ease-in-out hover:bg-red-500"}
                    onClick={()=>handleDelete(recipeProp.recipe.id!)}>Delete</button>
        )}
        {canEdit
            ? <button className={"text-l border-2 rounded-2xl ml-10 pl-1 pr-1 w-20 bg-blue-800 " +
                "text-white transition delay-50 ease-in-out hover:bg-blue-500"}
                      onClick={() =>recipeProp.setUpdating(recipeProp.recipe)}>Edit</button>:<></> }

    </div>);
}