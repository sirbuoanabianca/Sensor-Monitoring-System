import React, { useState, Fragment } from "react";
import "../../../commons/tables/fields/styles/client-table-style.css"

import ClientDevicesRow from "./client-device-readonly-row";



function ClientDevicesTable(props) {
    const [devices, setDevices] = useState(props.tableData);
    const [currentClientId, setCurrentClientId] = useState(props.currentClientId);
    const reloadHandler = props.reload;

    const [editFormData, setEditFormData] = useState({
        description: "",
        max_consumption: "",
        address: "",
    });


    return (
        <div className="app-container">


            <div style={{ overflow: 'auto', height: '400px', width: '1200px' }}>
                <table>
                    <thead>
                        <tr>
                            <th>Description</th>
                            <th>Address</th>
                            <th>Owner</th>
                            <th>Maximum hourly consumption [kWh]</th>
                            <th>Action</th>

                        </tr>
                    </thead>
                    <tbody>
                        {devices.map((device) => (
                            <Fragment>

                                <ClientDevicesRow
                                    device={device}
                                    handleViewConsumptionClick={props.handleViewConsumptionClick}
                                />

                            </Fragment>
                        ))}
                    </tbody>
                </table>
            </div>


        </div >
    );
};

export default ClientDevicesTable;
