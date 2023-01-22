import React from "react";

const ClientDevicesRow = ({ device,handleViewConsumptionClick }) => {
  return (
    <tr>
      <td>{device.description}</td>
      <td>{device.address}</td>
      <td>{device.usernameOwner}</td>
      <td>{device.max_consumption}</td>
      <td><button type="button" onClick={() => handleViewConsumptionClick(device)}>
          View consumption
        </button></td>

    </tr>
  );
};

export default ClientDevicesRow;
