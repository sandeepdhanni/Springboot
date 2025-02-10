import React, { useCallback, useEffect, useState } from 'react';
import Base from './Base';
import './usertask.css';
import MUIDataTable from 'mui-datatables';
import axios from 'axios';
import Checkbox from "@mui/material/Checkbox";

const Task = () => {
    const [taskData, setTaskData] = useState([]);
    const [filteredData, setFilteredData] = useState([]); // Store filtered data
    const [selectedRows, setSelectedRows] = useState([]); // Store selected rows from filtered data
    const [groups, setGroups] = useState([]); // Store groups
    const [selectedGroup, setSelectedGroup] = useState(null); // Store the selected group
    const [users, setUsers] = useState([]); // Store users for selected group
    const [selectedUser, setSelectedUser] = useState(""); // Store the selected user
    const url = process.env.REACT_APP_API_URL;

    const fetchData = useCallback(async () => {
        try {
            const response = await axios.get(`${url + process.env.REACT_APP_GET_ALL_TASK_ENDPOINT}`);
            console.log("response: ", response.data);
            setTaskData(response.data);
            setFilteredData(response.data); // Initially, set filtered data to all tasks
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }, [url]);

    const fetchGroup = useCallback(async () => {
        try {
            const response = await axios.get(`${url + process.env.REACT_APP_GET_GROUP_ENDPOINT}`);
            console.log("response group: ", response.data);
            setGroups(response.data);
        } catch (error) {
            console.error('Error fetching group:', error);
        }
    }, [url]);

    useEffect(() => {
        fetchData();
        fetchGroup();
    }, [fetchData, fetchGroup]);

    const columns = [
        {
            name: "Select",
            options: {
                customBodyRender: (value, tableMeta) => {
                    const rowData = filteredData[tableMeta.rowIndex];
                    return (
                        <Checkbox
                            checked={selectedRows.some(selectedRow => JSON.stringify(selectedRow) === JSON.stringify(rowData))}
                            onChange={() => handleCheckboxChange(rowData)}
                        />
                    );
                },
            },
        },
        { name: "instanceId", label: "Instance Key" },
        { name: "taskId", label: "Task Id" },
        { name: "accountNumber", label: "Account Number" },
        { name: "accountValue", label: "Account Value" },
        { name: "assignedGroup", label: "Assigned Group" },
    ];

    const options = {
        filter: true,
        selectableRows: "none",
        textLabels: {
            body: {
                noMatch: "No tasks available",
            },
        },
    };

    const handleCheckboxChange = (row) => {
        setSelectedRows(prevSelectedRows => {
            const isSelected = prevSelectedRows.some(selectedRow => JSON.stringify(selectedRow) === JSON.stringify(row));
            return isSelected
                ? prevSelectedRows.filter(selectedRow => JSON.stringify(selectedRow) !== JSON.stringify(row))
                : [...prevSelectedRows, row];
        });
    };

    // Handle group selection
    const handleGroupChange = (event) => {
        const selectedGroupId = event.target.value;
        const group = groups.find((group) => group.groupId === parseInt(selectedGroupId));
        setSelectedGroup(group); // Set selected group
        setUsers(group ? group.users : []); // Set users based on selected group
        setSelectedUser(""); // Reset user selection when group changes
        setSelectedRows([]);
    };

    // Handle user selection
    const handleUserChange = (event) => {
        setSelectedUser(event.target.value); // Set selected user
    };

    // Filter task data based on selected group 
    useEffect(() => {
        let filtered = taskData;
        // Filter by selected group
        if (selectedGroup) {
            filtered = filtered.filter(task => task.assignedGroup === selectedGroup.groupName);
        }
        setFilteredData(filtered);
    }, [selectedGroup, taskData]);

    const handleButtonClick = async () => {
        if(selectedUser===""){
            alert("select Group & Users !");
            return;
        }
        // Make a copy of selectedRows to avoid directly mutating the state
        const updatedSelectedRows = [...selectedRows];
    
        // Update the assignedUser field in each selected row
        updatedSelectedRows.forEach((row) => {
            row.assignedUser = selectedUser;
        });
    
        // Log the updated selected rows for debugging
        console.log("Updated Selected Rows: ", updatedSelectedRows);
    
        // Call your backend API to update the tasks (POST request)
        try {
            const response = await axios.post(`${url + process.env.REACT_APP_ASIGN_USER_ENDPOINT}`, updatedSelectedRows);
            console.log("API Response: ", response.data);
            // Handle the response as needed (e.g., show a success message, update UI)
        } catch (error) {
            console.error("Error updating tasks:", error);
            // Handle the error (e.g., show an error message)
        }
        // Optionally, reset selected rows after API call if needed
         setSelectedRows([]);
         fetchData();
    };
    

    return (
        <Base>
            <div className='userTask-container'>
                <div className='dropdown-container'>
                    {/* First Dropdown for selecting Group */}
                    <div>
                        <select className="dropdown" id="group" onChange={handleGroupChange} value={selectedGroup ? selectedGroup.groupId : ""}>
                            <option value="">Select a group</option>
                            {groups.map((group) => (
                                <option key={group.groupId} value={group.groupId}>
                                    {group.groupName}
                                </option>
                            ))}
                        </select>
                    </div>

                    {/* Second Dropdown for selecting Users based on selected group */}
                    <div>
                        <select className="dropdown" id="user" onChange={handleUserChange} value={selectedUser}>
                            <option value="">Select a user</option>
                            {users.map((user) => (
                                <option key={user.userId} value={user.userName}>
                                    {user.userName} - {user.taskCount} tasks
                                </option>
                            ))}
                        </select>
                    </div>
                    <div>
                        <button className='table-btn' onClick={handleButtonClick}>
                            Assign
                        </button>
                    </div>
                </div>

                <div className='table-data'>
                    <MUIDataTable
                        title={"Unassigned Task List"}
                        data={filteredData} 
                        columns={columns}
                        options={options}
                    />
                </div>

                {/* Log the selected data */}
                <div>
                    <h3>Selected Data:</h3>
                    <pre>{JSON.stringify(selectedRows, null, 2)}</pre>
                </div>
            </div>
        </Base>
    );
};

export default Task;
