import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";




export default function ElectionUpdate(props) {
 const navigate = useNavigate();
  const [data, setData] = useState({
    year : '',
    pstartDate : '',
    pendtDate : '',
    startDate : '',
    duration : '',
  });

  const handleInput = (event) => {
      setData({...data,[event.target.name]:event.target.value})
  }

function handleSubmit(event){
  console.log(data.duration);
  event.preventDefault()
  axios.put('http://localhost:8080/elections/update',{id:props.id,year:data.year,pstartDate:data.pstartDate,pendDate:data.pendtDate,startDate:data.startDate,duration:data.duration}).then(response => console.log(response)).catch(err => console.log(err))
}

  return (
    <div className="min-h-full h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div className="max-w-md w-full space-y-8">
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
                Election Saison
              </label>
              <div className="mt-1 sm:mt-0 sm:col-span-2">
                <input
                  type="number"
                  name="year"
                  id="election-season"
                  onChange={handleInput}
                  autoComplete="given-name"
                  className="max-w-lg block w-full shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:max-w-xs sm:text-sm border-gray-300 rounded-md"
                />
              </div>
            </div>

            <div className="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
              <label htmlFor="last-name" className="block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">
                debut de Candidature
              </label>
              <div className="mt-1 sm:mt-0 sm:col-span-2">
                
                <input
                  type="datetime-local"
                  name="pstartDate"
                  id="debut-candidature"
                  onChange={handleInput}
                  autoComplete="family-name"
                  className="max-w-lg block w-full shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:max-w-xs sm:text-sm border-gray-300 rounded-md"
                />
              </div>
            </div>

            <div className="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
              <label htmlFor="last-name" className="block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">
                 Fin de Candidature
              </label>
              <div className="mt-1 sm:mt-0 sm:col-span-2">
                <input
                  type="datetime-local"
                  name="pendtDate"
                  id="fin-candidature"
                  onChange={handleInput}
                  autoComplete="family-name"
                  className="max-w-lg block w-full shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:max-w-xs sm:text-sm border-gray-300 rounded-md"
                />
              </div>
            </div>

            <div className="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
              <label htmlFor="last-name" className="block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">
                Date d'election
              </label>
              <div className="mt-1 sm:mt-0 sm:col-span-2">
                
                <input
                  type="datetime-local"
                  name="startDate"
                  id="date-election"
                  onChange={handleInput}
                  autoComplete="family-name"
                  className="max-w-lg block w-full shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:max-w-xs sm:text-sm border-gray-300 rounded-md"
                />
              </div>
            </div>


            <div className="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
              <label htmlFor="street-address" className="block text-sm font-medium text-gray-700 sm:mt-px sm:pt-2">
                Durée de l'election
              </label>
              <div className="mt-1 sm:mt-0 sm:col-span-2">
                <input
                  type="number"
                  name="duration"
                  id="duration"
                  onChange={handleInput}
                  autoComplete="street-address"
                  className="block max-w-lg w-full shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm border-gray-300 rounded-md"
                />
              </div>
            </div>

          </div>
        </div>


      </div>

      <div className="pt-5">
        <div className="flex justify-end">
          <button
            type="button"
            className="bg-white py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            Cancel
          </button>
          <button
          onClick={()=> navigate(-1)}
            type="submit"
            className="ml-3 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            Save
          </button>
        </div>
      </div>
      
    </form>
    </div>
    </div>
  )
}
