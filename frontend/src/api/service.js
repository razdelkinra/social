import axios from "axios";

function Service(httpMethod, path, payload, headers) {
  const token = localStorage.getItem("token");
  const headerAuth = token && { Authorization: `Bearer ${token}` };
  const url = "http://jsonplaceholder.typicode.com";
  const cancel = axios.CancelToken.source();

  const service = axios.create({
    headers: {
      ...headers,
      ...headerAuth,
    },
    cancelToken: cancel.token,
  });

  const handleSuccess = (response) => response;

  service.interceptors.response.use(handleSuccess);

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
