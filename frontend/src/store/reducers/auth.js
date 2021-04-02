import { createSlice } from "@reduxjs/toolkit";

export const slice = createSlice({
  name: "auth",
  initialState: {
    isAuthorized: false,
    credentials: null,
  },
  reducers: {
    login: (state, action) => {
      state.isAuthorized = true;
      state.credentials = action.payload;
    },
  },
});

export const { login } = slice.actions;

export const selectCount = (state) => state.counter.value;

export default slice.reducer;
