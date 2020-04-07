import 'babel-polyfill';
import Vue from 'vue';
import App from './App';
import router from './router/client';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/common.less';
import './assets/font/iconfont.css';
import axios from 'axios';
axios.defaults.withCredentials=true;

import axiosclient from './config/axios-client'
Vue.prototype.$baseURL = axiosclient.defaults.baseURL
Vue.use(ElementUI);

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
});
