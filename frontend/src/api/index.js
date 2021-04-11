import Service from "./service";

export const signUpApi = (data) => Service("post", "registration", data);

export const getTokenApi = (data) => Service("post", "token", data);

export const getProfileApi = () => Service("get", "users");
