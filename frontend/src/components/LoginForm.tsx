import {type SubmitHandler, useForm} from 'react-hook-form';
import type {AuthFormFields} from "../Types/FormTypes.ts";
import {Link} from "react-router-dom";
import "../styles/AuthForm.css";

export default function LoginForm(){
    const{register, handleSubmit, formState:{errors, isSubmitting}} = useForm<AuthFormFields>();
    const onSubmit: SubmitHandler<AuthFormFields> = (data) =>{
        console.log(data);
    }
    return(
        <div className={"container"}>
                <h1 className={"HeaderText"}>Sign in</h1>
            <div className={"FormContainer"}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={"formBody"}>
                        <input {...register("username",{
                            required : "Username is required",
                            minLength: 4
                        })} type={"text"} placeholder={"Username"} className={"textInput"}/>
                        {errors.username &&( <div className={"text-danger"}>{errors.username.message}</div>)}
                        <input {...register("password",{
                            required :"Password is required"
                        })} type={"password"} placeholder={"Password"} className={"textInput"}/>
                        {errors.password &&( <div className={"text-danger"}>{errors.password.message}</div>)}
                        <button type={"submit"} disabled={isSubmitting} className={"submitButton"}>
                            {isSubmitting ? "Loading..." : "Submit"}
                        </button>
                    </div>
                </form>
            </div>
            <Link className={"redirectAuthLink"} to={"/register"}>New to the website? Register!</Link>
        </div>


    );

}