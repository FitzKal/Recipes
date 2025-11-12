import {useMutation, useQueryClient} from "@tanstack/react-query";
import {type SubmitHandler, useForm} from "react-hook-form";
import type {recipeType} from "../../Types/Recipe.ts";
import {userStore} from "../../Stores/UserStore.ts";
import {updateRecipe} from "../../service/RecipeService.ts";
import {toast} from "react-toastify";

export default function UpdateRecipeForm(recipeToUpdate:{recipeInfo?:recipeType,manageEditing:()=>void}){
    const {register,handleSubmit,formState:{errors}} = useForm<recipeType>({
        defaultValues:{
            id:recipeToUpdate.recipeInfo?.id,
            recipeTitle:recipeToUpdate.recipeInfo?.recipeTitle,
            description:recipeToUpdate.recipeInfo?.description,
            pictureSrc:recipeToUpdate.recipeInfo?.pictureSrc,
            ingredients:recipeToUpdate.recipeInfo?.ingredients,
            instructions:recipeToUpdate.recipeInfo?.instructions,
            category:recipeToUpdate.recipeInfo?.category
        }
    });
    const queryClient = useQueryClient();
    const currentUser = userStore.getState().user;

    const onSubmit: SubmitHandler<recipeType> = async (data:recipeType) => {
        console.log(data);
        updateMutation.mutate(data);
    }

    const updateMutation = useMutation({
        mutationFn:(data:recipeType) => {
            if (!currentUser?.accessToken || data.id == null) {
                throw new Error("Missing authentication or book id");
            }
            return updateRecipe(currentUser.accessToken, data, data.id);
        },
        onSuccess:() =>{
            toast.success("The chosen recipe was updated!");
            queryClient.invalidateQueries({queryKey:["recipes"]});
        },
        onError:(error)=>{
            if (error instanceof Error){
                toast.error(error.message);
            }else{
                toast.error("Something went wrong");
            }
        }

    })


    return (
        <div className={"flex flex-col justify-center m-auto mb-5"}>
            <h1 className={"text-center mr-10 text-xl mb-3"}>Update a recipe</h1>
            <div className={"flex border-2 rounded-2xl p-2 w-250 self-center bg-pink-200"}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={"flex fle gap-3 flex-wrap"}>
                        <div className={"flex flex-col"}>
                            <input{...register("recipeTitle",{
                                required:"Title is required",
                                minLength:3,
                            })} type={"text"} placeholder={"title"} className={"border-2 bg-white text-center w-75"} />
                            {errors.recipeTitle &&( <div className={"text-red-500 w-auto"}>{errors.recipeTitle.message}</div>)}
                        </div>

                        <div className={"flex flex-col"}>
                            <input{...register("pictureSrc",{
                                required:"Picture is required",
                            })} type={"text"} placeholder={"picture URL"} className={"border-2 bg-white text-center w-75"} />
                            {errors.pictureSrc &&(<div className={"text-red-500"}>{errors.pictureSrc.message}</div>)}
                        </div>

                        <div className={"flex flex-col"}>
                            <input{...register("description",{
                                required:"Description is required",
                                minLength:10
                            })} type={"text"} placeholder={"description"} className={"border-2 bg-white text-center w-75 he overflow-hidden"} />
                            {errors.description &&(<div className={"text-red-500"}>{errors.description.message}</div>)}
                        </div>

                        <div className={"flex flex-col"}>
                            <input{...register("ingredients",{
                                required:"The ingredients are required",
                                minLength:3
                            })} type={"text"} placeholder={"Ingredients"} className={"border-2 bg-white text-center w-75"} />
                            {errors.ingredients &&(<div className={"text-red-500"}>{errors.ingredients.message}</div>)}
                        </div>
                        <div className={"flex flex-col"}>
                            <input{...register("instructions",{
                                required:"The instructions are required",
                                minLength:3
                            })} type={"text"} placeholder={"Instructions"} className={"border-2 bg-white text-center w-75"} />
                            {errors.instructions &&(<div className={"text-red-500"}>{errors.instructions.message}</div>)}
                        </div>
                        <label>Choose a genre: </label>
                        <select{...register("category",{
                            required:true,
                        })} className={"bg-white border-2 max-h-lh"}>
                            <option value="DESSERT">Dessert</option>
                            <option value="SOUP">Soup</option>
                            <option value="MAIN">Main</option>
                            <option value="DRINK">Drink</option>
                        </select>
                        <button type={"submit"} className={"border-2 pr-2 pl-2 ml-1 bg-blue-400 transition delay-75 ease-in-out hover:bg-blue-600 max-h-7 w-30"}
                        >Update</button>
                        <button className={"border-2 pr-2 pl-2 ml-1 bg-red-500 transition delay-75 ease-in-out hover:bg-red-600 max-h-7 w-30"}
                                onClick={recipeToUpdate.manageEditing}>Close</button>
                    </div>
                </form>
            </div>
        </div>);
}