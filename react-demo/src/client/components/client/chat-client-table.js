import React, { useState, Fragment } from "react";
import "../../../commons/tables/fields/styles/client-table-style.css"
import ReadOnlyRow from "./chat-client-readonly-row";
import * as API_USERS from "../../api/client-api";



function ChatClientTable(props) {
    const [clients, setClients] = useState(props.tableData);
    const reloadHandler = props.reload;
    const [editContactId, setEditContactId] = useState(null);


    const handleStartChatClick = (clientUsername) => {
        props.handleStartChatClick(clientUsername);
    };


    return (

        <div className="app-container">

                <div style={{ overflow: 'auto', height: '400px', width: '500px' }}>
                    <table>
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {clients.map((client) => (
                                <Fragment>
                                        <ReadOnlyRow
                                            client={client}
                                            handleStartChatClick={handleStartChatClick}
                                        />
                                </Fragment>
                            ))}
                        </tbody>
                    </table>
                </div>
            

        </div >



    );
};

export default ChatClientTable;
