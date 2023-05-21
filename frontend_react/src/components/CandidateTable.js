import axios from 'axios';
import React, {useState, useEffect, Component} from 'react';
import { Link } from 'react-router-dom';
/* This example requires Tailwind CSS v2.0+ */
// const people = [
//     { name: 'Lindsay Walton', title: 'Front-end Developer', email: 'lindsay.walton@example.com', role: 'Member' },
//     // More people...
//   ]
  
  export default function Table() {
    
    let[data,setdata]=useState(null)
    function DeleteHandler(event,id){
      console.log(id)
      event.preventDefault()
      axios.delete('http://localhost:8080/election/delete/'+id)
      window.location.reload(false)
    }



    // 3. Create out useEffect function
    useEffect(() => {
        fetch("http://localhost:8080/elections")
        .then(response => response.json())
        // 4. Setting *data* to the API DATA that we received from the response above
        .then(data => setdata(data))
        },[])
    
    return (
      
      <div className="px-4 sm:px-6 lg:px-8">
        <div className="sm:flex sm:items-center">
          <div className="sm:flex-auto">
            <h1 className="text-xl font-semibold text-gray-900">Election</h1>
            <p className="mt-2 text-sm text-gray-700">
              La liste de tous les election faite .
            </p>
          </div>
        </div>
        <div className="mt-8 flex flex-col">
          <div className="-my-2 -mx-4 overflow-x-auto sm:-mx-6 lg:-mx-8">
            <div className="inline-block min-w-full py-2 align-middle md:px-6 lg:px-8">
              <div className="overflow-hidden shadow ring-1 ring-black ring-opacity-5 md:rounded-lg">
                <table className="min-w-full divide-y divide-gray-300">
                  <thead className="bg-gray-50">
                    <tr>
                      <th scope="col" className="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-6">
                        Saison
                      </th>
                      <th scope="col" className="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-6">
                        Date du debue de candidature
                      </th>
                      <th scope="col" className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">
                        Date de fin de candidature 
                      </th>
                      <th scope="col" className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">
                        Date de debut des election
                      </th>
                      <th scope="col" className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">
                        duration
                      </th>
                      <th scope="col" className="relative py-3.5 pl-3 pr-4 sm:pr-6">
                        <span className="sr-only">Edit</span>
                      </th>
                      <th scope="col" className="relative py-3.5 pl-3 pr-4 sm:pr-6">
                        <span className="sr-only">Delete</span>
                      </th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 bg-white">
                    {data && data.map((candidate) => (
                      <tr key={candidate.id}>
                        <td className="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-6">
                          {candidate.year}
                        </td>
                        <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{candidate.pstartDate}</td>
                        <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{candidate.pendDate}</td>
                        <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{candidate.startDate}</td>
                        <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{candidate.duration}</td>
                        <td className="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{candidate.positionTitle}</td>
                        <td className="relative whitespace-nowrap py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-6">
                          <Link to={'/candidates/'+candidate.id}><a className="text-indigo-600 hover:text-indigo-900">
                            Edit<span className="sr-only">, {candidate.name}</span>
                          </a>
                          </Link>
                        </td>
                        <td className="relative whitespace-nowrap py-4 pl-3 pr-4 text-right text-sm font-medium sm:pr-6">
                          <Link to ={'/candidates/'}><a onClick={event => DeleteHandler(event,candidate.id)} href="" className="text-red-600 hover:text-red-900" >
                            Delete
                          </a>
                          </Link>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
  