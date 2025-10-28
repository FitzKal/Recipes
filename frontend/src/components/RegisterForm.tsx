import type {AuthFormFields} from "../Types/FormTypes.ts";
import {type SubmitHandler, useForm} from 'react-hook-form';
import "../styles/AuthForm.css";
import {Link} from "react-router-dom";


export default function RegisterForm(){
    const {register,handleSubmit ,formState:{errors, isSubmitting}} = useForm<AuthFormFields>();

    const onSubmit:SubmitHandler<AuthFormFields> = (data) =>{
        console.log(data)
    }

    return(
        <div className={"container"}>
            <h1 className={"HeaderText"}>Register</h1>
            <div className={"FormContainer"}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={"formBody"}>
                        <input {...register("username",{
                            required : "Username is required",
                            minLength:{
                                value: 4,
                                message: "The username should be at least 4 characters long"
                            }
                        })} type={"text"} placeholder={"Username"} className={"textInput"}/>
                        {errors.username &&( <div className={"text-danger"}>{errors.username.message}</div>)}
                        <input {...register("password",{
                            required :"Password is required",
                            minLength:{
                                value: 8,
                                message: "The given password is too weak"
                            }
                        })} type={"password"} placeholder={"Password"} className={"textInput"}/>
                        {errors.password &&( <div className={"text-danger"}>{errors.password.message}</div>)}
                        <button type={"submit"} disabled={isSubmitting} className={"submitButton"}>
                            {isSubmitting ? "Loading..." : "Submit"}
                        </button>
                    </div>
                </form>
            </div>
            <Link className={"redirectAuthLink"} to={"/"}>Already have an account? Sign in!</Link>
        </div>
    );
}