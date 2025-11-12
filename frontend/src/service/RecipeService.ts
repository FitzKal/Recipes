// ------------- GetAll -------------
import type {recipeRequest, recipeType} from "../Types/Recipe.ts";

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

// ------------- Update -------------

export const updateRecipe = async (accessToken:string,updateRequest:recipeType,id:number) =>{
    const res = await fetch(`/api/recipes/${id}`,{
        method: "PUT",
        headers:{
            Authorization: `Bearer ${accessToken}`,
            "Content-Type" : "application/json",
        },
        body:JSON.stringify(updateRequest),
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else{
        const message = await res.text();
        throw new Error(message || "You are not the owner of the recipe, or don't have permission to edit the recipe");
    }
}

// ------------- Delete -------------
export const deleteRecipe = async (accessToken:string, id:number) =>{
    const res = await fetch(`/api/recipes/${id}`,{
        method:"DELETE",
        headers:{
            Authorization: `Bearer ${accessToken}`,
        }
    })
    if (res.ok){
        return await res.text();
    }else{
        const error = await res.text();
        throw new Error(error || "You are not the owner of the recipe, or don't have permission to delete the recipe");
    }
}