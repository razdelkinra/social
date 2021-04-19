import { useState } from "react";
import { NavPanel } from "../NavPanel";
import { UserList } from "../UserList";
import {
  getFriendsApi,
  getUsersApi,
  getRequestsToFriendApi,
} from "../../api/index";
import "./index.css";

export const UserNavigation = () => {
  const [activeTab, setActiveTab] = useState("users");

  const tabs = [
    { title: "Users", type: "users" },
    { title: "Friend request", type: "requests" },
    { title: "Friends", type: "friends" },
    { title: "Message", type: "messages" },
    { title: "News", type: "news" },
  ];

  const TabContent = {
    users: <UserList getList={getUsersApi} type={activeTab} />,
    friends: <UserList getList={getFriendsApi} type={activeTab} />,
    requests: <UserList getList={getRequestsToFriendApi} type={activeTab} />,
    messages: null,
    news: null,
  };

  return (
    <div className="user-navigation">
      <NavPanel tabs={tabs} activeTab={activeTab} setActiveTab={setActiveTab} />
      {TabContent[activeTab]}
    </div>
  );
};
