import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home'
import Login from  '../components/Login'
import Main from "../components/main/Main";
Vue.use(Router);

const router= new Router({
  routes: [
    { path:'/' , redirect: '/login'},
    { path:'/login' , component: Login},
    {
      path: '/home',
      component: Home ,
      redirect: '/home/main',
      children :[
        {path: '/home/main', component: Main},
      ]
    }
  ]
});
export default router;
//挂载路由导航

// router.beforeEach(((to, from, next) => {
//   //to从哪来 from到哪去
//   if (to.path==='/login'){
//       return next();
//   }
//   const token = window.sessionStorage.getItem("token");
//   if (!token){
//     return router.replace('/login');
//   }else{
//     return next();
//   }
// }));
