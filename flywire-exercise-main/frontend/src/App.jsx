// src/App.jsx

import React, { useEffect, useState } from 'react';
import Sidebar from "./components/Sidebar";
import Topbar from "./components/Topbar";
import { FaPlus } from "react-icons/fa";

export default function App() {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeEmployees, setActiveEmployees] = useState([]);
  const [inactiveEmployees, setInactiveEmployees] = useState([]);
  const [viewActive, setViewActive] = useState(true); // toggle state

  // Fetch all employees on mount
  useEffect(() => {
    fetch("http://localhost:8080/api/employees/active")
      .then((res) => res.json())
      .then((data) => {
        setEmployees(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching employees:", err);
        setLoading(false);
      });
  }, []);

  // fetch both groups on load employees
  useEffect(() => {
    Promise.all([
      fetch("http://localhost:8080/api/employees/active").then(res => res.json()),
      fetch("http://localhost:8080/api/employees/inactive").then(res => res.json())
    ])
    .then(([active, inactive]) => {
      setActiveEmployees(active);
      setInactiveEmployees(inactive);
      setLoading(false);
    })
    .catch(err => {
      console.error("Failed to fetch employees:", err);
      setLoading(false);
    });
  }, []);  

  // Add employee stub (you can later hook this to a form)
  const handleAddEmployee = () => {
    const newEmployee = {
      id: Date.now(),
      name: "New Employee",
      position: "Intern",
      hireDate: new Date().toLocaleDateString("en-US"),
      active: true,
      directReports: []
    };

    fetch("http://localhost:8080/api/employees", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newEmployee)
    })
      .then((res) => res.json())
      .then((createdEmployee) => {
        setEmployees((prev) => [...prev, createdEmployee]);
      })
      .catch((err) => {
        console.error("Failed to add employee:", err);
      });
  };

  const handleDelete = (id) => {
    if (!window.confirm("Are you sure you want to delete this employee?")) return;
  
    fetch(`http://localhost:8080/api/employees/${id}`, {
      method: "DELETE"
    })
      .then((res) => {
        if (res.ok) {
          setEmployees((prev) => prev.filter((e) => e.id !== id));
        } else {
          return res.json().then((err) => {
            throw new Error(err.error || "Delete failed");
          });
        }
      })
      .catch((err) => {
        console.error("Failed to delete employee:", err);
        alert("Error deleting employee.");
      });
  };
  
  const handleEdit = (id) => {
    alert("Edit employee " + id);
    // TODO: Open modal or redirect to edit form
  };
  

  return (
    <div className="layout">
      <Sidebar />
      <div className="content">
        <Topbar />
        <main className="p-6 text-gray-800 space-y-6">
          {/* Add Employee Button */}
          <div className="right-padded">
            <button className="custom-button btn-lg" onClick={handleAddEmployee}>
              <FaPlus />
              Add Employee
            </button>
          </div>

          {/* Employee Table */}
          <div className="flex justify-start gap-4 px-6 pt-4">
            <button
              className={`toggle-button ${viewActive ? "active" : ""} btn-lg`}
              onClick={() => setViewActive(true)}
            >
              Active Employees
            </button>
            <button
              className={`toggle-button ${!viewActive ? "active" : ""} btn-lg`}
              onClick={() => setViewActive(false)}
            >
            Inactive Employees
            </button>
          </div>

<div className="px-4">
  {loading ? (
    <p>Loading employees...</p>
  ) : (
    <table className="employee-table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Position</th>
          <th>Hire Date</th>
          <th>Active</th>
          <th># Direct Reports</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {(viewActive ? activeEmployees : inactiveEmployees).map((emp) => (
          <tr key={emp.id}>
            <td>{emp.name}</td>
            <td>{emp.position}</td>
            <td>{emp.hireDate}</td>
            <td>{emp.active ? "Yes" : "No"}</td>
            <td>{emp.directReports.length}</td>
            <td>
              <button className="action-button edit" onClick={() => handleEdit(emp.id)}>Edit</button>
              <button className="action-button delete" onClick={() => handleDelete(emp.id)}>Delete</button>
            </td>
          </tr>
        ))}
      </tbody>
        </table>
          )}
        </div>

        </main>
      </div>
    </div>
  );
}
