import { createSlice } from "@reduxjs/toolkit";
import { getProfileApi } from "../../api";

export const slice = createSlice({
  name: "profile",
  initialState: {
    isFetching: false,
    data: {
      name: null,
      birthDate: null,
      sex: null,
      homeTown: null,
      photoUrl: null,
    },
  },
  reducers: {
    getProfileRequest: (state) => {
      state.isFetching = "true";
    },
    getProfileReceive: (state, action) => {
      state.data = action.payload;
      state.isFetching = "false";
    },
    getProfileError: (state, action) => {
      state.isFetching = "false";
      state.error = action.payload.error;
    },
  },
});

const { getProfileRequest, getProfileReceive, getProfileError } = slice.actions;

export const getProfile = () => (dispatch) => {
  dispatch(getProfileRequest());
  return getProfileApi()
    .then(({ data }) => dispatch(getProfileReceive(data)))
    .catch((error) => {
      error.clientMessage = "Ошибка при получении данных профиля";
      dispatch(getProfileError({ error }));
    });
};

export const selectCount = (state) => state.counter.value;

export default slice.reducer;
