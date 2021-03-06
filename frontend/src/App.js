import { store } from "./store";
import { Provider } from "react-redux";
import { BrowserRouter as Router, Switch } from "react-router-dom";
import { LoginForm } from "./components/LoginForm";
import { SignUpForm } from "./components/SignUpForm";
import { UserPage } from "./containers/UserPage";
import { PrivateRoute, PublicRoute } from "./components/Routes";
import { NotificationContainer } from "react-notifications";
import "react-notifications/lib/notifications.css";

const App = () => {
  return (
    <Provider store={store}>
      <Router>
        <Switch>
          <PublicRoute path="/login" exact component={LoginForm} />
          <PublicRoute path="/registration" exact component={SignUpForm} />
          <PrivateRoute path="/" component={UserPage} />
        </Switch>
      </Router>
      <NotificationContainer />
    </Provider>
  );
};

export default App;
