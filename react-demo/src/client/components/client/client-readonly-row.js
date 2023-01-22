import React from "react";

const ReadOnlyRow = ({ client, handleEditClick, handleDeleteClick }) => {
  return (
    <tr>
      <td>{client.username}</td>

      <td>{client.role}</td>
      <td>********</td>
      <td>
        <button
          type="button"
          onClick={(event) => handleEditClick(event, client)}
        >
          Edit
        </button>
        <button type="button" onClick={() => handleDeleteClick(client.id)}>
          Delete
        </button>
      </td>
    </tr>
  );
};

export default ReadOnlyRow;
