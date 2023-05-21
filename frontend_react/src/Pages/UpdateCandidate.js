import AddCandidate from "../components/AddCandidate";
import { useEffect,useState } from "react";
import { useParams } from "react-router-dom";
export default function UpdateCandidate(){
    let { Candidateid } = useParams();
    let [data, setdata] = useState(null)
    useEffect(() => {
        fetch("http://localhost:8080/candidates/"+Candidateid)
        .then(response => response.json())
        // 4. Setting *data* to the API DATA that we received from the response above
        .then(data => setdata(data))
        },[])
    
    return <>
{      data &&   <AddCandidate id={ Candidateid } name={data.name} emailAddress={data.emailAddress} apogeeCode={data.apogeeCode} role={data.role} nbrVotes={data.nbrVotes} positionTitle={data.positionTitle} election={data.election}/>
}        </>
}