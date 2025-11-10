export type AuthFormFields = {
    username: string,
    password: string
}

export type UserEditRequest = {
    username:string,
    role: "ADMIN" | "USER"
}