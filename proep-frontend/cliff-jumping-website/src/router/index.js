import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import JumpingSites from '../views/JumpingSites.vue'
import News from '../views/News.vue'
import NewsInfo from '../views/NewsInfo.vue'
import Events from '../views/Events.vue'
import SignIn from '../views/SignIn.vue'
import Profile from '../views/Profile.vue'
// import NewsInfo from "./NewsInfo"
Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import( /* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/about-us',
    name: 'About',
    component: About
  },
  {
    path: '/jumping-sites',
    name: 'JumpingSites',
    component: JumpingSites
  },
  {
    path: '/newsInfo/:id',
    name: 'NewsInfo',
    component: NewsInfo
  },
  {
    path: '/news',
    name: 'News',
    component: News
  },
  {
    path: '/events',
    name: 'Events',
    component: Events
  },
  {
    path: '/sign-in',
    name: 'SignIn',
    component: SignIn
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router