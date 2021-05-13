import { useState } from "react";
import { NavPanel } from "../NavPanel";
import { UserList } from "../UserList";
import { SearchUsers } from "../SearchUsers";
import {
  getFriendsApi,
  getUsersApi,
  getRequestsToFriendApi,
  searchUsersApi,
} from "../../api/index";
import "./index.css";

export const UserNavigation = () => {
  const [activeTab, setActiveTab] = useState("users");

  const tabs = [
    { title: "Friend request", type: "requests" },
    { title: "Friends", type: "friends" },
    { title: "Search", type: "search" },
  ];

  const TabContent = {
    friends: <UserList getList={getFriendsApi} type={activeTab} />,
    requests: <UserList getList={getRequestsToFriendApi} type={activeTab} />,
    search: <SearchUsers searchUsers={searchUsersApi} />,
  };

  return (
    <div className="user-navigation">
      <NavPanel tabs={tabs} activeTab={activeTab} setActiveTab={setActiveTab} />
      {TabContent[activeTab]}
    </div>
  );
};
