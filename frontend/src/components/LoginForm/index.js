import { useState } from "react";
import { useDispatch } from "react-redux";
import { login } from "../../store/reducers/auth";
import "./index.css";

export const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const dispatch = useDispatch();

  const disableSubmit = !(email && password);

  return (
    <div className="login-form">
      <form>
        <p>Welcome</p>
        <input
          type="email"
          placeholder="Email"
          value={email}
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
          value="Sign in"
          disabled={disableSubmit}
          onClick={() => dispatch(login({ email, password }))}
        />
      </form>
    </div>
  );
};
