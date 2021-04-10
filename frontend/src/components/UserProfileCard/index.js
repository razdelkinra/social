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
        <h4>{birthDay}</h4>
        <h4>{city}</h4>
        <h4>{interests}</h4>
      </div>
    </div>
  );
};
