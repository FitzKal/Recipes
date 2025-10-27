import type {userListProp} from "../Types/User.ts";
import Users from "./Users.tsx";

export default function DisplayUsersList(props:userListProp){
    return (<div className={"listBody"}>
        {props.list.map(user =>
        <Users key = {user.username} userInfo ={user}/>
        )}
    </div>);
}