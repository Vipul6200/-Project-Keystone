import { useEffect, useState } from 'react'
import Customer from './pages/Customer'
import Site from './pages/Site'
import Technician from './pages/Technician' 
import Dashboard from './pages/Dashboard'
import CustomerPortal from './pages/CustomerPortal'
import './App.css'

function App(){
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [token, setToken] = useState('')
  const [workOrders, setWorkOrder] = useState<any[]>([])
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [status, setStatus] = useState('NEW')
  const [priority, setPriority] = useState('')
  const [searchTitle, setSearchTitle] = useState('')
  const [filterStatus, setFilterStatus] = useState('')
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const handleLogin = async () => {
  try {
    const response = await fetch('https://project-keystone-2.onrender.com/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    })

    if (!response.ok) {
      alert('Login failed. Username ya password check karein.')
      return
    }

    const data = await response.json()
    setToken(data.token)
    alert('Login successful!')
   } catch (error) {
    console.error('Login error:', error)
    alert('Backend se connection nahi ho pa raha hai.')
  }

}
  const handleSearch = async () => {
  try {
    const response = await fetch(
      `https://project-keystone-2.onrender.com/api/work-orders/search?title=${searchTitle}`
    )

    const data = await response.json()
    setWorkOrder(data)
  } catch (error) {
    console.error('Error:', error)
    alert('Backend server is not running')
  }
}

  const handleFilter = async () => {
  try {
    const response = await fetch(
      `https://project-keystone-2.onrender.com/api/work-orders/filter?status=${filterStatus}`
    )

    const data = await response.json()
    setWorkOrder(data)
    setPage(0)
    setTotalPages(0)
  } catch (error) {
    console.error('Error:', error)
    alert('Backend server is not running')
  }
  
}

  const handleWorkOrderSubmit = async (e: any) => {
  e.preventDefault()

  try {
    const response = await fetch('https://project-keystone-2.onrender.com/api/work-orders', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        title,
        description,
        status,
        priority,
      }),
    })

    if (response.ok) {
      alert('Work Order added successfully!')
      setTitle('')
      setDescription('')
      setStatus('NEW')
      setPriority('')
      const newWorkOrder = await response.json()
      setWorkOrder([...workOrders, newWorkOrder])

    } else {
      alert('Failed to add Work Order')
    }
  } catch (error) {
    console.error('Error:', error)
    alert('Backend server is not running')
  }
 }
  const [showCustomer, setShowCustomer] = useState(false)
  const [showSite, setShowSite] = useState(false)
  const [showTechnician, setShowTechnician] = useState(false)
  const [showDashboard, setShowDashboard] = useState(false)
  const [showCustomerPortal, setShowCustomerPortal] = useState(false)

  useEffect(() => {
    if (!token) return
    fetch(`https://project-keystone-2.onrender.com/api/work-orders/paged?page=${page}&size=5`, {
      headers:{
        'Authorization': `Bearer ${token}`
      },
    })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Request failed: ${response.status}')
      }
     return response.json()
    })
    .then((data) => {setWorkOrder(data.content)
     setTotalPages(data.totalPages)
     })
    .catch((error) => console.error('Error:', error))
    console.log(page)
    console.log(totalPages)
  }, [])

  const columns = [
    'NEW',
    'ASSIGNED',
    'IN_PROGRESS',
    'ON_HOLD',
    'COMPLETED',
    'CLOSED',
    'CANCELLED',
  ]

  return (
    <>
    {!token && 
      <div>
        <h2>Keystone Login</h2>

        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button type="button" onClick={handleLogin}>
          Login
        </button>
      </div>
    }
    
    <div className="kanban">
      <button onClick={() => setShowCustomer(!showCustomer)} >
       Customer Management
      </button>
      {showCustomer && <Customer token={token} />}
      <button onClick={() => setShowSite(!showSite)} >
         Site Management
      </button> 
      {showSite && <Site token={token} />}
      <button onClick={() => setShowTechnician(!showTechnician)}>
         Technician Field Views
      </button>
      {showTechnician && <Technician token={token} />}
      <button onClick={() => setShowDashboard(!showDashboard)}>
        Dashboard
      </button>
      {showDashboard && <Dashboard />}
      <button onClick={() => setShowCustomerPortal(!showCustomerPortal)}>
        Customer Portal
      </button>
      {showCustomerPortal && <CustomerPortal />}
      <h1>KEYSTONE - Work Order Board</h1>
    <input 
       type="text"
       placeholder="Search Work Order"
       value={searchTitle}
       onChange={(e) => setSearchTitle(e.target.value)}
    />
      <button type="button" onClick={handleSearch}>Search</button>

      <select
        value={filterStatus}
         onChange={(e) => setFilterStatus(e.target.value)}
        >
        <option value="">All Status</option>
        <option value="NEW">NEW</option>
        <option value="ASSIGNED">ASSIGNED</option>
        <option value="IN_PROGRESS">IN_PROGRESS</option>
        <option value="ON_HOLD">ON_HOLD</option>
        <option value="COMPLETED">COMPLETED</option>
        <option value="CLOSED">CLOSED</option>
        <option value="CANCELLED">CANCELLED</option>
        </select>

        <button type="button" onClick={handleFilter}>Filter</button>
      <form onSubmit={handleWorkOrderSubmit}>
    <input
        type="text"
        placeholder="Work Order Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
    />

    <input
        type="text"
        placeholder="Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
    />

    <select value={status} onChange={(e) => setStatus(e.target.value)}>
        <option value="NEW">NEW</option>
        <option value="ASSIGNED">ASSIGNED</option>
        <option value="IN_PROGRESS">IN_PROGRESS</option>
        <option value="ON_HOLD">ON_HOLD</option>
        <option value="COMPLETED">COMPLETED</option>
        <option value="CLOSED">CLOSED</option>
        <option value="CANCELLED">CANCELLED</option>
        </select>

    <input
        type="text"
        placeholder="Priority"
        value={priority}
        onChange={(e) => setPriority(e.target.value)}
    />

    <button type="submit">Add Work Order</button>
      </form>

      <div className="board">
        <div>
            <button type="button" onClick={() => setPage(page - 1)}>
             Previous
               </button>
                 <span> Page {page + 1} </span>
                 <button type="button" onClick={() => setPage(page + 1)}>
                  Next
                </button>
          </div>
        {columns.map((status) => (
          <div className="column" key={status}>
            <h2>{status}</h2>
            {workOrders
             .filter((workOrder) => workOrder.status === status)
             .map((workOrder) => (
             <div className="work-order" key={workOrder.id}>
             <h3>{workOrder.title}</h3>
             <p>{workOrder.description}</p>
             <p>Priority: {workOrder.priority}</p>
             </div>
             ))}
           </div>
         ))}
       </div>
      </div>
  </>
  )
}
export default App