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
            <p>Регистрация</p>
            <Field
              name="login"
              component="input"
              type="text"
              placeholder="Логин"
            />
            <Field
              name="password"
              component="input"
              type="password"
              placeholder="Пароль"
            />
            <Field
              name="firstName"
              component="input"
              type="text"
              placeholder="Имя"
            />

            <Field
              name="lastName"
              component="input"
              type="text"
              placeholder="Фамилия"
            />

            <Field
              name="city"
              component="input"
              type="text"
              placeholder="Город"
            />

            <Field
              name="interests"
              component="input"
              type="text"
              placeholder="Интересы"
            />
            <div className="bdate-gender">
              <Field
                name="birthDay"
                dateFormat="yyyy/MM/dd"
                placeholder="Дата Рождения"
                component={DatePickerField}
              />
              <Field
                name="gender"
                type="text"
                component="select"
                className="select"
              >
                <option value="male"> Мужской</option>
                <option value="female"> Женский</option>
              </Field>
            </div>

            <button
              className="button"
              type="submit"
              disabled={submitting || pristine}
            >
              Отправить
            </button>
          </form>
        )}
      />
    </div>
  );
};
