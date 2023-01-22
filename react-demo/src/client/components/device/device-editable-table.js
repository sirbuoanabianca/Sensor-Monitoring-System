import React, { useState, Fragment } from "react";
import "../../../commons/tables/fields/styles/client-table-style.css"

import ReadOnlyRow from "./device-readonly-row";
import EditableRow from "./device-editable-row";
import * as API_USERS from "../../api/device-api";



function DeviceEditableTable(props) {
    const [devices, setDevices] = useState(props.tableData);
    const reloadHandler = props.reload;

    const [editFormData, setEditFormData] = useState({
        description: "",
        max_consumption: "",
        address: "",
    });

    const [editDeviceId, setEditDeviceId] = useState(null);

    // const handleAddFormChange = (event) => {
    //     event.preventDefault();

    //     const fieldName = event.target.getAttribute("name");
    //     const fieldValue = event.target.value;

    //     const newFormData = { ...addFormData };
    //     newFormData[fieldName] = fieldValue;

    //     setAddFormData(newFormData);
    // };

    const handleEditChange = (event) => {
        event.preventDefault();

        const fieldName = event.target.getAttribute("name");
        const fieldValue = event.target.value;

        const newFormData = { ...editFormData };
        newFormData[fieldName] = fieldValue;

        setEditFormData(newFormData);
    };

    // const handleAddFormSubmit = (event) => {
    //     event.preventDefault();

    //     const newContact = {
    //         id: nanoid(),
    //         fullName: addFormData.fullName,
    //         address: addFormData.address,
    //         phoneNumber: addFormData.phoneNumber,
    //         email: addFormData.email,
    //     };

    //     const newContacts = [...clients, newContact];
    //     setClients(newContacts);
    // };

    const handleSaveEditSubmit = (event) => {
        event.preventDefault();

        const editedDevice = {
            id: editDeviceId,
            description: editFormData.description,
            max_consumption: editFormData.max_consumption,
            address: editFormData.address,
        };

        const newDevice = [...devices];

        const index = devices.findIndex((contact) => contact.id === editDeviceId);

        newDevice[index] = editedDevice;

        setDevices(newDevice);
        setEditDeviceId(null);

        editDevice(editedDevice);
    };

    function editDevice(device) {

        return API_USERS.updateDevice(device, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully updated device with id: " + result);
                reloadHandler();
            } else {
                //TODO:
                // this.setState(({
                //     errorStatus: status,
                //     error: error
                // }));
            }
        });
    }

    const handleEditClick = (event, editedDevice) => {
        event.preventDefault();
        setEditDeviceId(editedDevice.id);

        const formValues = {
            description: editedDevice.description,
            max_consumption: editedDevice.max_consumption,
            address: editedDevice.address,
        };

        setEditFormData(formValues);
    };

    const handleCancelClick = () => {
        setEditDeviceId(null);
    };

    const handleDeleteClick = (deviceId) => {
        const newDevice = [...devices];

        const index = devices.findIndex((contact) => contact.id === deviceId);

        newDevice.splice(index, 1);

        setDevices(newDevice);
        deleteDevice(deviceId);
    };

    function deleteDevice(id) {
        return API_USERS.deleteDevice(id, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully deleted device with id: " + result);
                reloadHandler();
            } else {
                //TODO:
                // this.setState(({
                //     errorStatus: status,
                //     error: error
                // }));
                console.log("ERROR");
            }
        });

    }

    return (
        <div className="app-container">

            <form onSubmit={handleSaveEditSubmit}>
                <div style={{ overflow: 'auto', height: '400px', width: '1200px' }}>
                    <table>
                        <thead>
                            <tr>
                                <th>Description</th>
                                <th>Address</th>
                                <th>Owner</th>
                                <th>Maximum hourly consumption [kWh]</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {devices.map((device) => (
                                <Fragment>
                                    {editDeviceId === device.id ? (
                                        <EditableRow
                                            editFormData={editFormData}
                                            handleEditFormChange={handleEditChange}
                                            handleCancelClick={handleCancelClick}
                                        />
                                    ) : (
                                        <ReadOnlyRow
                                            device={device}
                                            handleEditClick={handleEditClick}
                                            handleDeleteClick={handleDeleteClick}
                                            handleBindClick={props.handleBind}
                                        />
                                    )
                                    }
                                </Fragment>
                            ))}
                        </tbody>
                    </table>
                </div>
            </form >

        </div >
    );
};

export default DeviceEditableTable;
