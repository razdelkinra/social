import Service from "./service";

export const signUpApi = (data) =>
  Service("post", "registration", data, {
    success: "Регистрация произошла успешно",
  });

export const getTokenApi = (data) => Service("post", "token", data);

export const getProfileApi = () => Service("get", "api/profile");

export const getUsersApi = () => Service("get", "api/users");

export const searchUsersApi = (data) => Service("post", "api/users", data);

export const getFriendsApi = () => Service("get", "api/friends");
export const makeFriendsApi = (id) =>
  Service(
    "post",
    "api/friends",
    { friendId: id },
    { success: "Поздравляем! У вас новый друг" }
  );

export const requestToFriendApi = (userId, selfId) =>
  Service(
    "post",
    "api/friends/request",
    {
      userId,
      fromUserId: selfId,
      message: "Hello",
    },
    { success: "Мы отправли Ваш запрос. Дождитесь когда друг ответит на него" }
  );

export const getRequestsToFriendApi = () =>
  Service("get", "api/friends/requests");
