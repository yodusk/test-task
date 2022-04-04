import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',

    component: () => import('../views/Login.vue')
  },
  {
    path: '/signup',
    name: 'SignUp',

    component: () => import('../views/SignUp.vue')
  },
  {
    path: '/admin',
    name: 'AdminPanel',

    component: () => import('../views/AdminPanel.vue')
  },
  {
    path: '/user',
    name: 'UserPanel',

    component: () => import('../views/UserPanel.vue')
  },
  {
    path: '/unauthorized',
    name: 'Unauthorized',

    component: () => import('../views/Unauthorised.vue')
  },

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
