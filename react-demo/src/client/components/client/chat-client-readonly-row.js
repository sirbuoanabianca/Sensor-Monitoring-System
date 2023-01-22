import React from "react";

const ReadOnlyRow = ({ client, handleStartChatClick }) => {
  return (
    <tr>
      <td>{client.username}</td>
      <td>
        <button type="button" onClick={() => handleStartChatClick(client.username)}>
          Start Chatting
        </button>
      </td>
    </tr>
  );
};

export default ReadOnlyRow;
