import {useEffect, useState} from "react";
import type {User} from "./Types/User.ts";
import {getAllUsers} from "./service/userService.ts";
import DisplayUsersList from "./components/DisplayUsersList.tsx";

function App() {
    const [userList,setList] = useState<User[]>([]);
     useEffect(() =>{
         const load = async ()=>{
             const list = await getAllUsers();
             setList(list);
         }
         load();
     },[])



  return (
      <DisplayUsersList list = {
          userList
      } />
  )
}

export default App
