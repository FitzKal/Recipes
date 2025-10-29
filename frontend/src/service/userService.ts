import type {User, userAuthRequest} from "../Types/User.ts";

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

export const createUser = async (user : userAuthRequest) => {
    const res = await fetch("api/recipe/user",{
        method : 'POST',
        headers: {
            "Content-Type" : "application/json",
        },
        body: JSON.stringify(user),
    });
    if (res.ok){
        const jsonList = res.json();
        console.log(jsonList);
        return jsonList;
    }else{
        throw new Error("Could not post user");
    }
}