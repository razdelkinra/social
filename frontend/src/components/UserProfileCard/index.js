import "./index.css";

export const UserProfileCard = () => {
  return (
    <div className="user-profile">
      <div className="user-profile__image"></div>

      <div className="user-profile__body">
        <h2 className="user-profile__body-name">John Smith</h2>
        <h4 className="user-profile__body-job">Product Designer</h4>
        <div className="user-profile__body-bio">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dignissimos,
          aperiam.
        </div>
      </div>
    </div>
  );
};
