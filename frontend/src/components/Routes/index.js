import { Redirect, Route } from "react-router-dom";
import useAuth from "../../hooks/useAuth";

export const PrivateRoute = ({ component: Component, ...rest }) => {
  const isLogin = useAuth();
  return (
    <Route
      {...rest}
      render={(props) =>
        isLogin !== false ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{
              pathname: "/login",
              state: { from: props.location },
            }}
          />
        )
      }
    />
  );
};

export const PublicRoute = ({ component: Component, ...rest }) => {
  const isLogin = useAuth();
  return (
    <Route
      {...rest}
      render={(props) =>
        isLogin ? <Redirect to="/" /> : <Component {...props} />
      }
    />
  );
};
