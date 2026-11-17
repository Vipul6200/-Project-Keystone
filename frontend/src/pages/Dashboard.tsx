import { useEffect, useState } from 'react'

function Dashboard({ token }: { token: string }) {
  const [summary, setSummary] = useState<any>(null)

  useEffect(() => {
    if (!token) return

    fetch('https://project-keystone-2.onrender.com/api/reports/summary', {
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
      .then((data) => setSummary(data))
      .catch((error) => {
        console.error('Dashboard error:', error)
      })
  }, [token])

  if (!summary) {
    return <p>Loading Dashboard...</p>
  }

  return (
    <div>
      <h2>Dashboard</h2>

      <h3>Work Order Status</h3>
      <p>Total Work Orders: {summary.totalWorkOrders}</p>
      <p>New: {summary.newCount}</p>
      <p>Assigned: {summary.assignedCount}</p>
      <p>In Progress: {summary.inProgressCount}</p>
      <p>Completed: {summary.completedCount}</p>

      <h3>SLA</h3>
      <p>Overdue: {summary.overdueCount}</p>
      <p>On Time: {summary.onTimeCount}</p>
      <p>At Risk: {summary.atRiskCount}</p>
      <p>Breached: {summary.breachedCount}</p>

      <h3>Technician Breakdown</h3>
      {Object.entries(summary.technicianBreakdown || {}).map(
        ([technicianId, count]) => (
          <p key={technicianId}>
            Technician {technicianId}: {String(count)} Work Orders
          </p>
        )
      )}
    </div>
  )
}

export default Dashboard