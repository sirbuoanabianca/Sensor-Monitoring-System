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
          placeholder="Enter a username..."
          name="username"
          value={editFormData.username}
          onChange={handleEditFormChange}
        ></input>
      </td>
      <td>
        <input
          type="text"
          required="required"
          placeholder="Enter a role..."
          name="role"
          value={editFormData.role}
          onChange={handleEditFormChange}
        ></input>
      </td>

      <td>
        <input
          type="text"
          placeholder="Enter a password to change..."
          name="password"
          value={editFormData.password}
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
