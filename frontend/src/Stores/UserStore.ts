import type {User} from "../Types/User.ts";
import {create} from "zustand/react";

interface UserState {
    user : User | null,
    stateLogin: (user:User) => void,
    stateLogout: () => void
}

export const userStore = create<UserState>((set) =>({
    user : null,
    stateLogin : (user:User) =>set({user}),
    stateLogout:() =>set({user : null})
}));