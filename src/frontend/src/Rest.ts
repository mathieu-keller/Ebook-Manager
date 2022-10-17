import axios from 'axios';

const axiosRest = axios.create();

axiosRest.interceptors.response.use(
  response => {
    if (response.status >= 400 || response.status === undefined) {
      window.alert(response.data);
    }
    return response;
  },
  error => {
    return error;
  });
export default axiosRest;
