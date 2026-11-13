import {  useEffect, useState } from 'react'
import './Customer.css'
function Customer({token}: {token: string}) {
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [phone, setPhone] = useState('')
    const [customers, setCustomers] = useState<any[]>([])
    useEffect(() => {
        if (!token) return
        fetch('https://project-keystone-2.onrender.com/api/customers', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Request failed: ${response.status}')
            }
            return response.json()
        })
        .then((data) => setCustomers(data))
        .catch((error) => console.error('Error:', error))
    }, [])
    const handleDelete = async (id: number) =>{
        try {
            const response = await fetch(`https://project-keystone-2.onrender.com/api/customers/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            if (response.ok) {
                alert('Customer deleted successfully!')
            }
            else{
                alert('Failed to deleted customer')
            }
        }
        catch (error) {
            console.error('Error:', error)
            alert('Backend server is not running')
        }
    }
    const handleSubmit = async (e: any) => {
    e.preventDefault()

    try {
        const response = await fetch('https://project-keystone-2.onrender.com/api/customers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify({
                name: name,
                email: email,
                phone: phone,
            }),
        })

        if (response.ok) {
            alert('Customer added successfully!')
            setName('')
            setEmail('')
            setPhone('')
        } else {
            alert('Failed to add customer')
        }
    } catch (error) {
        console.error('Error:', error)
        alert('Backend server is not running')
    }
}


  return (
    <div className="customer-page">
        <h1>Customer Management</h1>
        <p>Manage KEYSTONE customers here.</p>

        <form className="customer-form" onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Customer Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />

            <input
                type="email"
                placeholder="Customer Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />

            <input
                type="text"
                placeholder="Customer Phone"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
            />

            <button type="submit">Add Customer</button>
        </form>

        <div className="customer-list">
            <h2>Customer List</h2>

            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>
                {customers.map((customer) =>(
                    <tr key={customer.id}>
                        <td>{customer.name}</td>
                        <td>{customer.email}</td>
                        <td>
                            <button onClick={() => handleDelete(customer.id)}>
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

export default Customer