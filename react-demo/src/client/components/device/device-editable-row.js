import React from "react";

const EditableRow = ({
  editFormData,
  handleEditFormChange,
  handleCancelClick,
}) => {
  return (
    <tr>
      <td>
        <input
          type="text"
          required="required"
          placeholder="Enter a description..."
          name="description"
          value={editFormData.description}
          onChange={handleEditFormChange}
        ></input>
      </td>
      <td>
        <input
          type="text"
          required="required"
          placeholder="Enter an address..."
          name="address"
          value={editFormData.address}
          onChange={handleEditFormChange}
        ></input>
      </td>
      <input
        type="text"
        required="required"
        placeholder="Can't enter an owner from here..."
        name="usernameOwner"
        value={editFormData.usernameOwner}
        onChange={handleEditFormChange}
        disabled
      ></input>
      <td>
        <input
          type="text"
          required="required"
          placeholder="Enter a maximum hourly consumption..."
          name="max_consumption"
          value={editFormData.max_consumption}
          onChange={handleEditFormChange}
        ></input>

      </td>
      <td>
        <button type="submit">Save</button>
        <button type="button" onClick={handleCancelClick}>
          Cancel
        </button>
      </td>
    </tr>
  );
};

export default EditableRow;
