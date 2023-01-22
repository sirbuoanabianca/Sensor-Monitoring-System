import React, { useState, Fragment } from "react";
import "../../../commons/tables/fields/styles/client-table-style.css"
import ReadOnlyRow from "./client-readonly-row";
import EditableRow from "./client-editable-row";
import * as API_USERS from "../../api/client-api";



function ClientEditableTable(props) {
    const [clients, setClients] = useState(props.tableData);
    const reloadHandler = props.reload;

    console.log(clients);
    // const [addFormData, setAddFormData] = useState({
    //     username: "",
    //     password: "",
    //     role: "",
    // });

    const [editFormData, setEditFormData] = useState({
        username: "",
        password: "",
        role: "",
    });

    const [editContactId, setEditContactId] = useState(null);

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

        const editedContact = {
            id: editContactId,
            username: editFormData.username,
            role: editFormData.role,
            password: editFormData.password
        };

        const newContacts = [...clients];

        const index = clients.findIndex((contact) => contact.id === editContactId);

        newContacts[index] = editedContact;

        setClients(newContacts);
        setEditContactId(null);

        if (editedContact.password === undefined)
            editedContact.password = "";

        console.log("Pass ", editClient.password);
        editClient(editedContact);
    };

    function editClient(client) {

        return API_USERS.updateDevice(client, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully updated client with id: " + result);
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

    const handleEditClick = (event, contact) => {
        event.preventDefault();
        setEditContactId(contact.id);
        console.log("PASS=", contact.password);


        const formValues = {
            username: contact.username,
            role: contact.role,
        };

        setEditFormData(formValues);
    };

    const handleCancelClick = () => {
        setEditContactId(null);
    };

    const handleDeleteClick = (contactId) => {
        const newContacts = [...clients];

        const index = clients.findIndex((contact) => contact.id === contactId);

        newContacts.splice(index, 1);

        setClients(newContacts);
        deleteClient(contactId);
    };

    function deleteClient(id) {
        return API_USERS.deleteClient(id, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully deleted client with id: " + result);
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
                <div style={{ overflow: 'auto', height: '400px', width: '900px' }}>
                    <table>
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Password</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {clients.map((client) => (
                                <Fragment>
                                    {editContactId === client.id ? (
                                        <EditableRow
                                            editFormData={editFormData}
                                            handleEditFormChange={handleEditChange}
                                            handleCancelClick={handleCancelClick}
                                        />
                                    ) : (
                                        <ReadOnlyRow
                                            client={client}
                                            handleEditClick={handleEditClick}
                                            handleDeleteClick={handleDeleteClick}
                                        />
                                    )}
                                </Fragment>
                            ))}
                        </tbody>
                    </table>
                </div>
            </form >

        </div >



    );
};

export default ClientEditableTable;
