import type {User} from "./User.ts";

export interface Recipe {
    id:number,
    recipeTitle:string,
    description?: string,
    ingredients: string,
    instructions: string,
    category?: "DESSERT" | "SOUP" | "MAIN" | "DRINK",
    pictureSrc?: string,
    user?:User,
    username:string
}

export type recipeType =  {
    id:number,
    recipeTitle:string,
    description?: string,
    ingredients: string,
    instructions: string,
    category?: "DESSERT" | "SOUP" | "MAIN" | "DRINK",
    pictureSrc?: string,
    user?:User,
    username:string
}