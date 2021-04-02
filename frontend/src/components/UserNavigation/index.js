import { useState } from "react";
import { NavPanel } from "../NavPanel";
import "./index.css";

export const UserNavigation = () => {
  const [activeTab, setActiveTab] = useState(0);

  const tabs = [
    { title: "Друзья" },
    { title: "Сообщения" },
    { title: "Новости" },
  ];

  return (
    <div className="user-navigation">
      <NavPanel tabs={tabs} activeTab={activeTab} setActiveTab={setActiveTab} />
    </div>
  );
};
