"use strict";

import Vue from 'vue';
import axios from "axios";

// 完整配置:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
  baseURL: '/api', // 使用代理路径
  // timeout: 60 * 1000, // 超时时间
  // withCredentials: true, // 检查跨站点的访问控制
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function(config) {
    // 在请求发送之前做一些处理
    return config;
  },
  function(error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 添加响应拦截器
_axios.interceptors.response.use(
  function(response) {
    // 对响应数据做点什么
    return response;
  },
  function(error) {
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);

const Plugin = {
  install: function(Vue, options) {
    Vue.axios = _axios;
    window.axios = _axios;
    Object.defineProperties(Vue.prototype, {
      axios: {
        get() {
          return _axios;
        }
      },
      $axios: {
        get() {
          return _axios;
        }
      }
    });
  }
};

Vue.use(Plugin)

// 导出 axios 实例供直接导入使用
export default _axios;
export { _axios as axios };
