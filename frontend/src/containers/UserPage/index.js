import { UserProfileCard } from "../../components/UserProfileCard";
import { UserNavigation } from "../../components/UserNavigation";
import "./index.css";

export const UserPage = () => {
  return (
    <div className="user-page">
      <UserProfileCard />
      <UserNavigation />
    </div>
  );
};
