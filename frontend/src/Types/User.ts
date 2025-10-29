import type {Recipe} from "./Recipe.ts";

export interface User{
    user_id: number,
    username:string,
    password: string,
    recipes:Recipe[],
    role?: "ADMIN" | "USER",
    accessToken: string;

}

export type userListProp = {
    list : User[]
}

export type userProp = {
    userInfo : User
}

export type userAuthRequest  = {
    username: string,
    password: string
}