import { useState, useEffect } from "react";
import { useSelector } from "react-redux";

export default function useAuth() {
  const [auth, setAuth] = useState();
  const isAuthorized = useSelector((state) => state.auth.isAuthorized);

  useEffect(() => {
    isAuthorized ? setAuth(true) : setAuth(false);
  }, [isAuthorized]);

  return auth;
}
