import { useState } from 'react'

function CustomerPortal() {
  const [customerId, setCustomerId] = useState('')
  const [workOrders, setWorkOrders] = useState<any[]>([])
  const [searched, setSearched] = useState(false)
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const[priority, setPriority] = useState('MEDIUM')
  const[siteId, setSiteId] = useState('')

  const loadWorkOrders = async () => {
    try {
      const response = await fetch(
        `https://project-keystone-2.onrender.com/api/work-orders/customer/${customerId}`
      )

      if (!response.ok) {
        alert('Unable to load work orders')
        return
      }

      const data = await response.json()
      setWorkOrders(data)
      setSearched(true)
    } catch (error) {
      console.error('Error:', error)
      alert('Backend server is not running')
    }
  }
  const createRequest = async () => {
  try {
    const response = await fetch(
      `https://project-keystone-2.onrender.com/api/work-orders/customer/${customerId}/request`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title,
          description,
          priority,
          status:'NEW',
          siteId: Number(siteId),
        }),
      }
     )

    if (!response.ok) {
      alert('Unable to create request')
      return
     }

     alert('Request created successfully')

     setTitle('')
     setDescription('')
     setSiteId('')
     setPriority('MEDIUM')

     loadWorkOrders()
    } catch (error) {
     console.error('Error:', error)
     alert('Backend server is not running')
     }
   }

  return (
    <div>
      <h2>Customer Portal</h2>

      <input
        type="number"
        placeholder="Customer ID"
        value={customerId}
        onChange={(e) => setCustomerId(e.target.value)}
      />

      <button type="button" onClick={loadWorkOrders}>
        View My Work Orders
      </button>
      <h3>Raise New Request</h3>
       <input
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
       />

       <input
       type="text"
       placeholder="Description"
       value={description}
       onChange={(e) => setDescription(e.target.value)}
      />

      <input
      type="number"
       placeholder="Site ID"
       value={siteId}
       onChange={(e) => setSiteId(e.target.value)}
      />

      <select
       value={priority}
       onChange={(e) => setPriority(e.target.value)}
      >
       <option value="LOW">LOW</option>
       <option value="MEDIUM">MEDIUM</option>
       <option value="HIGH">HIGH</option>
      </select>

       <button type="button" onClick={createRequest}>
        Raise Request
       </button>

      {searched && workOrders.length === 0 && (
        <p>No work orders found.</p>
      )}

      {workOrders.map((workOrder) => (
        <div key={workOrder.id}>
          <h3>{workOrder.title}</h3>
          <p>Status: {workOrder.status}</p>
          <p>Priority: {workOrder.priority}</p>
          <p>Description: {workOrder.description}</p>
          <p>Loaded workorder: {workOrders.length}</p>
        </div>
      ))}
    </div>
  )
}

export default CustomerPortal