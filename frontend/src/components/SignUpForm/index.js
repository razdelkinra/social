import { Form, Field } from "react-final-form";
import { format } from "date-fns";
import { useHistory } from "react-router-dom";
import { DatePickerField } from "../DatePicker";
import { signUpApi } from "../../api/index";

import "./index.css";

const initialValues = {
  login: "",
  password: "",
  firstName: "",
  lastName: "",
  gender: "male",
  interests: "",
  birthDay: "",
  city: "",
};

const normalizeValues = (data) => {
  const { login, password, birthDay, ...rest } = data;
  const formeBirthDay = format(birthDay, "yyyy-MM-dd");

  return {
    ...rest,
    credential: {
      login,
      password,
    },
    birthDay: formeBirthDay,
  };
};

export const SignUpForm = () => {
  const history = useHistory();

  const onSubmitHandler = (values) => {
    const data = normalizeValues(values);
    signUpApi(data).then(() => history.push("login"));
  };

  return (
    <div className="signup-form">
      <Form
        onSubmit={(values) => onSubmitHandler(values)}
        initialValues={initialValues}
        render={({ handleSubmit, submitting, pristine }) => (
          <form onSubmit={handleSubmit}>
            <p>Sign up</p>
            <Field
              name="login"
              component="input"
              type="text"
              placeholder="Login"
            />
            <Field
              name="password"
              component="input"
              type="password"
              placeholder="Password"
            />
            <Field
              name="firstName"
              component="input"
              type="text"
              placeholder="Name"
            />

            <Field
              name="lastName"
              component="input"
              type="text"
              placeholder="Lastname"
            />

            <Field
              name="city"
              component="input"
              type="text"
              placeholder="City"
            />

            <Field
              name="interests"
              component="input"
              type="text"
              placeholder="Interests"
            />
            <div className="bdate-gender">
              <Field
                name="birthDay"
                dateFormat="yyyy/MM/dd"
                placeholder="Birthday"
                component={DatePickerField}
              />
              <Field
                name="gender"
                type="text"
                component="select"
                className="select"
              >
                <option value="male"> Male</option>
                <option value="female"> Female</option>
              </Field>
            </div>

            <button
              className="button"
              type="submit"
              disabled={submitting || pristine}
            >
              Send
            </button>
          </form>
        )}
      />
    </div>
  );
};
