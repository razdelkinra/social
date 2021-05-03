import "./index.css";

export const UserProfileCard = ({ profile }) => {
  const { birthDay, city, firstName, lastName, interests } = profile;

  return (
    <div className="user-profile">
      <div className="user-profile__image"></div>

      <div className="user-profile__body">
        <h2 className="user-profile__body-name">
          {firstName} {lastName}{" "}
        </h2>
        <h4 style={{ color: "gold" }}>{birthDay}</h4>
        <h4 style={{ color: "blueviolet" }}>{city}</h4>
        <h4 style={{ color: "crimson" }}>{interests}</h4>
      </div>
    </div>
  );
};
