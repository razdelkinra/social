import { useState, useEffect } from "react";
import { UserList } from "../UserList";
import "./SearchUsers.css";

export const SearchUsers = ({ searchUsers }) => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [users, setUsers] = useState([]);

  useEffect(() => {
    if (firstName.length > 0 || lastName.length > 0) {
      searchUsers({ firstName, lastName }).then((resp) => {
        let size = resp.data.length > 10 ? 10 : resp.data.length
        setUsers(resp.data.slice(0, size))
      });
    } else {
      setUsers([]);
    }
  }, [firstName, lastName]);

  return (
    <div>
      <div className="search-users">
        <input
          placeholder="firstname"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
        <input
          placeholder="lastname"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
      </div>
      <UserList users={users} />
    </div>
  );
};
