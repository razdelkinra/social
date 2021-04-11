import { useState, useEffect } from "react";
import { useSelector } from "react-redux";

export default function useAuth() {
  const [auth, setAuth] = useState();
  const hasToken = localStorage.getItem("token");
  const isAuthorized = useSelector((state) => state.auth.isAuthorized);

  useEffect(() => {
    hasToken || isAuthorized ? setAuth(true) : setAuth(false);
  }, [isAuthorized, hasToken]);

  return auth;
}
