import React from "react";

const ReadOnlyRow = ({ device, handleEditClick, handleDeleteClick, handleBindClick }) => {
  return (
    <tr>
      <td>{device.description}</td>
      <td>{device.address}</td>
      <td>{device.usernameOwner}</td>
      <td>{device.max_consumption}</td>
      <td>
        <button
          type="button"
          onClick={(event) => handleEditClick(event, device)}
        >
          Edit
        </button>
        <button type="button" onClick={() => handleDeleteClick(device.id)}>
          Delete
        </button>
        <button type="button" onClick={() => handleBindClick(device.id)}>
          Bind to an user
        </button>
      </td>
    </tr>
  );
};

export default ReadOnlyRow;
