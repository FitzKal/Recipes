// ------------- LOGIN -------------
import type {userAuthRequest} from "../Types/User.ts";

export const userLogin = async (loginData:userAuthRequest) =>{
    const res = await fetch("/api/auth/login",{
        method: 'POST',
        headers:{
            "Content-Type" : "application/json",
        },
        body: JSON.stringify(loginData)
    });
    if (res.ok){
        const responseData = await res.json();
        console.log(responseData);
        return responseData;
    }else{
        const message = await res.text();
        throw new Error(message || "request could not be completed");
    }

}