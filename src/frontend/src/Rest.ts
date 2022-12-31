import axios from 'axios';

type AxiosOptions = {
    showErrors?: boolean;
}

const axiosRest = (options: AxiosOptions) => {
  const axiosClient = axios.create();

  axiosClient.interceptors.response.use(
    onFulfilled => {
      return onFulfilled;
    },
    onRejected => {
      const errorText = onRejected.response.data;
      if (options.showErrors) {
        window.alert(errorText);
      }
      return errorText;
    });
  return axiosClient;
};
export default axiosRest;
