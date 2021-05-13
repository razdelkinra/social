import axios from "axios";
import { NotificationManager } from "react-notifications";

function Service(httpMethod, path, payload, messages, headers) {
  const token = localStorage.getItem("token");
  const headerAuth = token && { Authorization: token };
  let query = window.location.href
  let end = window.location.href.indexOf('3000')
  const url = query.substr(0, end + 5) ;
  const cancel = axios.CancelToken.source();

  const service = axios.create({
    headers: {
      ...headers,
      ...headerAuth,
    },
    cancelToken: cancel.token,
  });

  service.interceptors.request.use(
    function (config) {
      console.log("requestSuc");
      return config;
    },
    function (error) {
      console.log("requestErr");
      return Promise.reject(error);
    }
  );

  service.interceptors.response.use(
    function (config) {
      messages && NotificationManager.success(messages.success);
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
  );

  // const handleSuccess = (response) => response;

  // service.interceptors.response.use(handleSuccess);

  switch (httpMethod) {
    case "get":
      return service.get(`${url}${path}`);
    case "post":
      return service.request({
        method: "POST",
        url: `${url}${path}`,
        data: payload,
      });
    default:
      break;
  }
}

export default Service;
