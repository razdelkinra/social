import { useState, useEffect } from "react";
import { UserList } from "../UserList";
import "./SearchUsers.css";

export const SearchUsers = ({ searchUsers }) => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [users, setUsers] = useState([]);

  useEffect(() => {
    if (firstName.length > 3 || lastName.length > 3) {
      searchUsers({ firstName, lastName }).then((resp) => setUsers(resp.data));
    } else {
      setUsers([]);
    }
  }, [firstName, lastName]);

  return (
    <div>
      <div className="search-users">
        <input
          placeholder="Enter First Name"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
        <input
          placeholder="Enter Last Name"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
      </div>
      <UserList users={users} />
    </div>
  );
};
