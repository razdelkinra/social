import { useState } from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router-dom";
import { getToken } from "../../store/reducers/auth";
import "./index.css";

export const LoginForm = () => {
  const [login, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const dispatch = useDispatch();
  const history = useHistory();

  const disableSubmit = !(login && password);

  return (
    <div className="login-form">
      <form>
        <p>Welcome</p>
        <input
          type="login"
          placeholder="Login"
          value={login}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <input
          type="button"
          value="Login"
          disabled={disableSubmit}
          onClick={() => dispatch(getToken({ login, password }))}
        />
        <button
          className="sign-up-button"
          onClick={() => history.push("registration")}
        >
          Sign Up
        </button>
      </form>
    </div>
  );
};
