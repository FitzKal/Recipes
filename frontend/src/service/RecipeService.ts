// ------------- GetAll -------------
import type {recipeRequest} from "../Types/Recipe.ts";

export const getAllRecipes = async (accessToken:string)=>{
    const res = await fetch("/api/recipes",{
        headers:{Authorization:`Bearer ${accessToken}`}
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else {
        const message = await res.text();
        throw new Error(message || "Something went wrong");
    }
}

// ------------- GetById -------------

export const getRecipeById = async (accessToken:string, recipeId: number | undefined) =>{
    const res = await fetch(`/api/recipes/${recipeId}`,{
        headers: {Authorization : `Bearer ${accessToken}`},
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else {
        const message = await res.text();
        throw new Error(message || "Request could not be completed");
    }
}

// ------------- Post -------------

export const postRecipe = async (accessToken:string, recipe:recipeRequest) =>{
    const res = await fetch("/api/recipes",{
        method : 'POST',
        headers: {
            Authorization : `Bearer ${accessToken}`,
            "Content-Type" : "application/json"
        },
        body:JSON.stringify(recipe)
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else {
        const message = await res.text();
        throw new Error(message || "Request could not be completed");
    }
}
