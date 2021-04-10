import { createSlice } from "@reduxjs/toolkit";
import { getTokenApi } from "../../api";

export const slice = createSlice({
  name: "auth",
  initialState: {
    credentials: null,
    token: "",
    isAuthorized: false,
  },
  reducers: {
    getTokenRequest: (state) => {
      state.isFetching = "true";
    },
    getTokenReceive: (state, action) => {
      state.isFetching = "false";
      state.token = action.payload;
      state.isAuthorized = true;
    },
    getTokenError: (state, action) => {
      state.isFetching = "false";
      state.error = action.payload.error;
    },
  },
});

const { getTokenRequest, getTokenReceive, getTokenError } = slice.actions;

export const getToken = (data) => (dispatch) => {
  dispatch(getTokenRequest());
  return getTokenApi(data)
    .then(({ data }) => {
      dispatch(getTokenReceive(data));
      localStorage.setItem("token", data);
    })
    .catch((error) => {
      error.clientMessage = "Ошибка при получении токена";
      dispatch(getTokenError({ error }));
    });
};

export default slice.reducer;
