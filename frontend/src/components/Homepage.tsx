import {userStore} from "../Stores/UserStore.ts";


export default function Homepage(){
    return(<div className={"Home "}>
        <h1 className={"align-content-center text-center text-3xl font-semibold"}>This is the homePage</h1>
        <h2 className={"align-content-center text-center !text-blue-600 text-2xl"}>Welcome {userStore.getState().user?.username}</h2>
        </div>);
}