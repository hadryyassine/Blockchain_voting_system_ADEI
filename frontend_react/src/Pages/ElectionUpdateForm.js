import { useParams } from "react-router-dom"
import ElectionUpdate from "../components/ElectionUpdate"
import Election from "../components/ElectionUpdate"

export default function ElectionUpdateForm(){
    let {id} = useParams();
    return(
        <>
            <ElectionUpdate id = {id}>

            </ElectionUpdate>
        </>
    )
}