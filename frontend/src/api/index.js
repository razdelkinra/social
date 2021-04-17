import Service from "./service";

export const signUpApi = (data) => Service("post", "registration", data);

export const getTokenApi = (data) => Service("post", "token", data);

export const getProfileApi = () => Service("get", "api/profile");

export const getUsersApi = () => Service("get", "api/users");

export const getFriendsApi = () => Service("get", "api/friends");
export const makeFriendsApi = (id) =>
  Service("post", "api/friends", { friendId: id });

export const requestToFriendApi = (userId, selfId) =>
  Service("post", "api/friends/request", {
    userId,
    fromUserId: selfId,
    message: "Hello",
  });
export const getRequestsToFriendApi = () =>
  Service("get", "api/friends/requests");
