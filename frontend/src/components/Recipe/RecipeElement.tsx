import {userStore} from "../../Stores/UserStore.ts";
import {useQueryClient} from "@tanstack/react-query";
import type {recipeType} from "../../Types/Recipe.ts";
import Recipe from "./Recipe.tsx";

export default function RecipeElement(recipeProp:{recipe:recipeType}){

    const currentUser = userStore.getState().user;
    const queryClient = useQueryClient();


    return(<div>
        <Recipe recipeInfo={recipeProp.recipe}/>

    </div>);
}