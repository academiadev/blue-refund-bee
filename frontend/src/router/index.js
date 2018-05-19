import Vue from 'vue'
import Router from 'vue-router'
import LoginScreen from '@/components/LoginScreen'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'LoginScreen',
      component: LoginScreen
    }
  ]
})
