import type {recipeType} from "../../Types/Recipe.ts";
import {Link} from "react-router-dom";



export default function Recipe(recipeProp: { recipeInfo: recipeType }) {

    return (
        <div className={"flex flex-col w-60"}>
            <Link to={`/dashboard/recipes/${recipeProp.recipeInfo.id}`} className={"text-2xl text-white mb-3 transition delay-50 ease-in-out hover:text-[#3A5A9C]"}>{recipeProp.recipeInfo.recipeTitle}</Link>
            <img className={"h-80 w-55 mb-2 transition delay-150 duration-300 ease-in-out hover:scale-130"}
                 src={recipeProp.recipeInfo.pictureSrc} alt={recipeProp.recipeInfo.recipeTitle}/>
            <p className={"text-white"}>Type: {recipeProp.recipeInfo.category}</p>
            <p className={"mb-2 text-white"}>Added by: {recipeProp.recipeInfo.username}</p>
        </div>
    )
}