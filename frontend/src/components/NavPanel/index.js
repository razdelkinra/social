import "./index.css";

export const NavPanel = (props) => {
  const { tabs = [], activeTab, setActiveTab } = props;
  const setActiveTabHandler = (ind) => setActiveTab(ind);

  return (
    <ul className="menu-bar">
      {tabs.map((i, index) => (
        <li
          key={i.title}
          className={activeTab === i.type && "active"}
          onClick={() => setActiveTabHandler(i.type)}
        >
          {i.title}
        </li>
      ))}
    </ul>
  );
};
