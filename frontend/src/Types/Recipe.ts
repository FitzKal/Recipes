import type {User} from "./User.ts";

export interface Recipe {
    id:number,
    recipeTitle:string,
    description?: string,
    category: "DESSERT" | "SOUP" | "MAIN_COURSE",
    pictureSrc: string,
    user:User
}