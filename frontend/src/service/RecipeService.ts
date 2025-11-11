// ------------- GetAll -------------
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