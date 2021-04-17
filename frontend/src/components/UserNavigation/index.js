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
    { title: "Пользователи", type: "users" },
    { title: "Заявки в друзья", type: "requests" },
    { title: "Друзья", type: "friends" },
    { title: "Сообщения", type: "messages" },
    { title: "Новости", type: "news" },
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
