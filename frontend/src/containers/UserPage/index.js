import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { UserProfileCard } from "../../components/UserProfileCard";
import { UserNavigation } from "../../components/UserNavigation";
import { getProfile } from "../../store/reducers/profile";

import "./index.css";

export const UserPage = () => {
  const profile = useSelector((state) => state.profile.data);
  const dispatch = useDispatch();
  const history = useHistory();

  useEffect(() => {
    dispatch(getProfile());
  }, []);

  const logoutHandler = () => {
    localStorage.clear();
    history.push("login");
  };

  return (
    <div className="user-page">
      <button className="logout-button" onClick={logoutHandler}>
        Logout
      </button>
      <UserProfileCard profile={profile} />
      <UserNavigation />
    </div>
  );
};
