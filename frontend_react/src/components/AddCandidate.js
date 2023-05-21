import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";



export default function AddCandidate(props) {

  const [data, setData] = useState({
    id : props.id,
    name : props.name,
    emailAddress : props.emailAddress,
    apogeeCode : props.apogeeCode,
    role:props.role,
    nbrVotes:props.nbrVotes,
    positionTitle:props.positionTitle,
    election:props.election,
  });
  console.log(data.id);
  const handleInput = (event) => {
      setData({...data,[event.target.name]:event.target.value})
  }

function handleSubmit(event){
  console.log(data.duration);
  event.preventDefault()
  axios.put('http://localhost:8080/candidate/update',{id:data.id,name:data.name,emailAddress:data.emailAddress,apogeeCode:data.apogeeCode,role:data.role,nbrVotes:data.nbrVotes,positionTitle:data.positionTitle,election:data.election}).then(response => console.log(response)).catch(err => console.log(err))
}

  return (

    <form onSubmit={handleSubmit} className="space-y-8 divide-y divide-gray-200" >
      <div className="space-y-8 divide-y divide-gray-200 sm:space-y-5">


        <div className="pt-8 space-y-6 sm:pt-10 sm:space-y-5">
          <div>
            <h3 className="text-lg leading-6 font-medium text-gray-900">Personal Information</h3>
            <p className="mt-1 max-w-2xl text-sm text-gray-500">Use a permanent address where you can receive mail.</p>
          </div>
          <div className="space-y-6 sm:space-y-5">
            <div className="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
              <label htmlFor="first-name" className="block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">
                Nom du candidat
              </label>
              <div className="mt-1 sm:mt-0 sm:col-span-2">
                <input
                  type="text"
                  name="name"
                  id="candidate_name"
                  onChange={handleInput}
                  autoComplete="given-name"
                  className="max-w-lg block w-full shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:max-w-xs sm:text-sm border-gray-300 rounded-md"
                  value={data.name}
                />
              </div>
            </div>

            <div className="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
              <label htmlFor="first-name" className="block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">
                Adresse email
              </label>
              <div className="mt-1 sm:mt-0 sm:col-span-2">
                <input
                  type="text"
                  name="emailAddress"
                  id="emailAddress"
                  onChange={handleInput}
                  autoComplete="given-name"
                  value={data.emailAddress}
                  className="max-w-lg block w-full shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:max-w-xs sm:text-sm border-gray-300 rounded-md"
                />
              </div>
            </div>
            <div className="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
              <label htmlFor="first-name" className="block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">
                Code Apogée
              </label>
              <div className="mt-1 sm:mt-0 sm:col-span-2">
                <input
                  type="text"
                  name="apogeeCode"
                  id="apogeeCode"
                  onChange={handleInput}
                  autoComplete="given-name"
                  value={data.apogeeCode}
                  className="max-w-lg block w-full shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:max-w-xs sm:text-sm border-gray-300 rounded-md"
                />
              </div>
            </div>
          </div>
        </div>


      </div>

      <div className="pt-5">
        <div className="flex justify-end">
          <Link to={"/candidates"}> <button
            type="button"
            className="bg-white py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            Cancel
          </button>
          </Link>
          <button
            type="submit"
            className="ml-3 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            Save
          </button>
        </div>
      </div>
    </form>
    
  )
}
