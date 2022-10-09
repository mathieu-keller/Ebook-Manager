import axios from 'axios';

const axiosRest = axios.create({

  validateStatus: function (status) {
    return status < 500; // Resolve only if the status code is less than 500
  }
});

axiosRest.interceptors.response.use(
  response => {
    if (response.status >= 400) {
      window.alert(response.data);
    }
    return response;
  },
  error => {
    return error;
  });
export default axiosRest;
