// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import  ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/global.css'
import store from '@/store/store.js'
import Vuex from 'vuex'
axios.defaults.baseURL='http://localhost';//请求根路径
Vue.prototype.$http=axios;
Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(Vuex);
/* eslint-disable no-new */
axios.defaults.withCredentials = true;
axios.interceptors.request.use(config =>{
  let token = window.sessionStorage.getItem('token');
  if (token){
    config.headers.token=token;
  }
  return config;
});
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
});
