import type {Recipe} from "./Recipe.ts";

export interface User{
    user_id: number,
    username:string,
    password: string,
    recipes:Recipe[],
    role: "ADMIN" | "USER"
}

export type userListProp = {
    list : User[]
}

export type userProp = {
    userInfo : User
}