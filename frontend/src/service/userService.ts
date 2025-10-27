import type {User} from "../Types/User.ts";

export const getAllUsers= async ():Promise<User[]>=>{
    const res = await fetch("/api/recipe/user");
    if (res.ok){
        const jsonList = res.json();
        console.log(jsonList);
        return jsonList;
    }else{
        throw new Error("Could not retrieve list");
    }
}