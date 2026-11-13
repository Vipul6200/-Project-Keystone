import { useState } from 'react'

function Technician({token}: {token: string}) {
  const [assignedTo, setAssignedTo] = useState('')
  const [workOrders, setWorkOrders] = useState<any[]>([])
  const [attachments, setAttachments] = useState<Record<number, any[]>>({})
  const [fileNames, setFileNames] = useState<Record<number, string>>({})
  const [fileUrls, setFileUrls] = useState<Record<number, string>>({})

  const loadAttachments = async (workOrderId: number) => {
    try {
      const response = await fetch(
        `https://project-keystone-2.onrender.com/api/work-orders/${workOrderId}/attachments`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      )

      const data = await response.json()

      setAttachments((previous) => ({
        ...previous,
        [workOrderId]: data,
      }))
    } catch (error) {
      console.error('Attachment error:', error)
    }
  }

  const addAttachment = async (workOrderId: number) => {
    try {
      const response = await fetch(
        `https://project-keystone-2.onrender.com/api/work-orders/${workOrderId}/attachments`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            fileName: fileNames[workOrderId] || '',
            fileUrl: fileUrls[workOrderId] || '',
          }),
        }
      )

      if (response.ok) {
        alert('Attachment added')

        setFileNames((previous) => ({
          ...previous,
          [workOrderId]: '',
        }))

        setFileUrls((previous) => ({
          ...previous,
          [workOrderId]: '',
        }))

        await loadAttachments(workOrderId)
      } else {
        alert('Unable to add attachment')
      }
    } catch (error) {
      console.error('Attachment error:', error)
      alert('Backend server is not running')
    }
  }

  return (
    <div>
      <h2>Technician Field View</h2>

      <input
        type="number"
        placeholder="Technician ID"
        value={assignedTo}
        onChange={(e) => setAssignedTo(e.target.value)}
      />

      <button
        type="button"
        onClick={async () => {
          try {
            const response = await fetch(
              `https://project-keystone-2.onrender.com/api/work-orders/technician/${assignedTo}`,
              {
                headers: { Authorization: `Bearer ${token}` },
              }
            )

            const data = await response.json()
            setWorkOrders(data)

            for (const workOrder of data) {
              await loadAttachments(workOrder.id)
            }
          } catch (error) {
            console.error('Error:', error)
            alert('Backend server is not running')
          }
        }}
      >
        Load Assigned Work Orders
      </button>

      {workOrders.map((workOrder) => (
        <div key={workOrder.id}>
          <h3>{workOrder.title}</h3>
          <p>{workOrder.description}</p>
          <p>Status: {workOrder.status}</p>
          <p>Priority: {workOrder.priority}</p>

          <button
            type="button"
            onClick={async () => {
              try {
                const response = await fetch(
                  `https://project-keystone-2.onrender.com/api/work-orders/${workOrder.id}/status?status=IN_PROGRESS`,
                  {
                    method: 'PUT',
                    headers: { Authorization: `Bearer ${token}` },
                  }
                )

                if (response.ok) {
                  alert('Work Order started')
                } else {
                  alert('Unable to start Work Order')
                }
              } catch (error) {
                console.error('Error:', error)
                alert('Backend server is not running')
              }
            }}
          >
            Start
          </button>

          <button
            type="button"
            onClick={async () => {
              try {
                const response = await fetch(
                  `https://project-keystone-2.onrender.com/api/work-orders/${workOrder.id}/status?status=ON_HOLD`,
                  {
                    method: 'PUT',
                    headers: { Authorization: `Bearer ${token}` },
                  }
                )

                if (response.ok) {
                  alert('Work Order put on hold')
                } else {
                  alert('Unable to put Work Order on hold')
                }
              } catch (error) {
                console.error('Error:', error)
                alert('Backend server is not running')
              }
            }}
          >
            Hold
          </button>

          <button
            type="button"
            onClick={async () => {
              try {
                const response = await fetch(
                  `https://project-keystone-2.onrender.com/api/work-orders/${workOrder.id}/status?status=IN_PROGRESS`,
                  {
                    method: 'PUT',
                    headers: { Authorization: `Bearer ${token}` },
                  }
                )

                if (response.ok) {
                  alert('Work Order resumed')
                } else {
                  alert('Unable to resume Work Order')
                }
              } catch (error) {
                console.error('Error:', error)
                alert('Backend server is not running')
              }
            }}
          >
            Resume
          </button>

          <button
            type="button"
            onClick={async () => {
              try {
                const response = await fetch(
                  `https://project-keystone-2.onrender.com/api/work-orders/${workOrder.id}/status?status=COMPLETED`,
                  {
                    method: 'PUT',
                    headers: { Authorization: `Bearer ${token}` },
                  }
                )

                if (response.ok) {
                  alert('Work Order completed')
                } else {
                  alert('Unable to complete Work Order')
                }
              } catch (error) {
                console.error('Error:', error)
                alert('Backend server is not running')
              }
            }}
          >
            Complete
          </button>

          <hr />

          <h4>Attachments</h4>

          <button
            type="button"
            onClick={() => loadAttachments(workOrder.id)}
          >
            Load Attachments
          </button>

          {attachments[workOrder.id]?.map((attachment) => (
            <div key={attachment.id}>
              <p>File: {attachment.fileName}</p>
              <p>URL: {attachment.fileUrl}</p>
            </div>
          ))}

          <input
            type="text"
            placeholder="File name"
            value={fileNames[workOrder.id] || ''}
            onChange={(e) =>
              setFileNames((previous) => ({
                ...previous,
                [workOrder.id]: e.target.value,
              }))
            }
          />

          <input
            type="text"
            placeholder="File URL"
            value={fileUrls[workOrder.id] || ''}
            onChange={(e) =>
              setFileUrls((previous) => ({
                ...previous,
                [workOrder.id]: e.target.value,
              }))
            }
          />

          <button
            type="button"
            onClick={() => addAttachment(workOrder.id)}
          >
            Add Attachment
          </button>
        </div>
      ))}
    </div>
  )
}

export default Technician