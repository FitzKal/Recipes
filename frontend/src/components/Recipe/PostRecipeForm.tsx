import {useMutation, useQueryClient} from "@tanstack/react-query";
import {type SubmitHandler, useForm} from "react-hook-form";
import type {recipeRequest} from "../../Types/Recipe.ts";
import {userStore} from "../../Stores/UserStore.ts";
import {postRecipe} from "../../service/RecipeService.ts";
import {toast} from "react-toastify";

export default function PostRecipeForm(){
    const {register,handleSubmit,formState:{errors},reset} = useForm<recipeRequest>();
    const queryClient = useQueryClient();
    const currentUser = userStore.getState().user;

    const onSubmit: SubmitHandler<recipeRequest> = async (data) => {
        postMutation.mutate(data);
    }

    const postMutation = useMutation({
        mutationFn:(data:recipeRequest) => {
            if (!currentUser?.accessToken) {
                throw new Error("Not authenticated");
            }
            return postRecipe(currentUser.accessToken, data);
        },
        onSuccess:() =>{
            toast.success("Recipe posted successfully");
            reset();
            queryClient.invalidateQueries({queryKey:["recipes"]});
        },
        onError:(error) =>{
            if (error instanceof Error){
                toast.error(error.message);
            } else {
                toast.error("Something went wrong");
            }
        }
    })

    return (
        <div className={"flex flex-col justify-center m-auto mb-5"}>
        <div className={"border-t border-white w-[80%] m-auto mt-5 mb-10"}></div>
        <h1 className={"text-center text-white mr-10 text-3xl mb-10 "}>Add a new recipe</h1>
    <div className={"flex border-2 rounded-2xl p-5 w-250 self-center bg-[#AFBEE3]"}>
    <form onSubmit={handleSubmit(onSubmit)}>
    <div className={"flex fle gap-3 flex-wrap"}>
    <div className={"flex flex-col"}>
        <input{...register("recipeTitle",{
                required:"Title is required",
                minLength:3,
            })} type={"text"} placeholder={"Title"} className={"border-2 bg-white text-center w-75"} />
    {errors.recipeTitle &&( <div className={"text-[#600000] w-auto"}>{errors.recipeTitle.message}</div>)}
        </div>

        <div className={"flex flex-col"}>
        <input{...register("pictureSrc",{
                required:"Picture is required",
            })} type={"text"} placeholder={"Picture URL"} className={"border-2 bg-white text-center w-75"} />
        {errors.pictureSrc &&(<div className={"text-[#600000]"}>{errors.pictureSrc.message}</div>)}
            </div>

            <div className={"flex flex-col"}>
            <input{...register("description",{
                    required:"Description is required",
                    minLength:10
                })} type={"text"} placeholder={"Description"} className={"border-2 bg-white text-center w-75 he overflow-hidden"} />
            {errors.description &&(<div className={"text-[#600000]"}>{errors.description.message}</div>)}
                </div>

                <div className={"flex flex-col"}>
                <input{...register("ingredients",{
                        required:"The ingredients are required",
                        minLength:3
                    })} type={"text"} placeholder={"Ingredients"} className={"border-2 bg-white text-center w-75"} />
                {errors.ingredients &&(<div className={"text-[#600000]"}>{errors.ingredients.message}</div>)}
                    </div>
                <div className={"flex flex-col"}>
                    <input{...register("instructions",{
                        required:"The instructions are required",
                        minLength:3
                    })} type={"text"} placeholder={"Instructions"} className={"border-2 bg-white text-center w-75"} />
                    {errors.instructions &&(<div className={"text-[#600000]"}>{errors.instructions.message}</div>)}
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
                    <button type={"submit"} className={"border-2 pr-5 pl-5 ml-1 text-xl text-white bg-[#2C4278] transition delay-75 ease-in-out hover:bg-[#3A5A9C] max-h-10 w-30"}
                    >Post</button>
                    </div>
                    </form>
    </div>
        </div>);
                }