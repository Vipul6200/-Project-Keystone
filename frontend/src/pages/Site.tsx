import { useEffect, useState } from 'react'
import './Site.css'

function Site({token}: {token: string}) {
    const [siteName, setSiteName] = useState('')
    const [location, setLocation] = useState(' ')
    const [sites, setSites] = useState<any[]>([])
    useEffect(() => {
      if (!token) return
        fetch('https://project-keystone-2.onrender.com/api/sites', {
            headers: {
            Authorization: `Bearer ${token}`,
           },
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Request failed: ${response.status}`)
            }
            return response.json()
        })
        .then((data) => setSites(data))
        .catch((error) => console.error('Error:', error))

    }, [])
    const handleDelete = async (id: number) => {
    try {
        const response = await fetch(`https://project-keystone-2.onrender.com/api/sites/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
            }
        })

        if (response.ok) {
            alert('Site deleted successfully!')
        } else {
            alert('Failed to delete site')
        }
    } catch (error) {
        console.error('Error:', error)
        alert('Backend server is not running')
    }
}
    const handleSubmit = async (e: any) => {
        e.preventDefault()
        
        try {
            const response = await fetch('https://project-keystone-2.onrender.com/api/sites', {
            method:'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
            body:JSON.stringify({
                siteName: siteName,
                location: location,
            }),
          })
          if (response.ok) {
            alert('Site added successfuly!')
            setSiteName('')
            setLocation('')
          }
          else{
            alert('Failed to add site')
          }
        }catch (error){
            console.error('Error:', error)
            alert('Backend server is not running')
        }
        
    }
    return (
        <div className="site-page">
            <h1>Site Management</h1>
            <p>Manage KEYSTONE site here.</p>

            <form className="site-form"onSubmit={handleSubmit}>
                <input
                 type="text"
                 placeholder="Site Name"
                 value={siteName}
                 onChange={(e) => setSiteName(e.target.value)}
                />
                <input
                 type="text"
                 placeholder="Location"
                 value={location}
                 onChange={(e) => setLocation(e.target.value)}
                />

                <button type="submit">Add Site</button>
            </form>
            <div className="site-list">
                <h2>Site List</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    {sites.map((site) => (
                        <tr key={site.id}>
                            <td>{site.siteName}</td>
                            <td>{site.location}</td>
                            <td>
                                <button onClick={() => handleDelete(site.id)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Site