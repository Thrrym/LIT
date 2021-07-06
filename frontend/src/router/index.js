import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import store from "../store.js";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
  {
    path: "/debug",
    name: "Debug",
    component: () => import("../views/Debug.vue"),
    meta: {
      requiresAuth: true,
    }
  },
  {
    path: "/signin",
    name: "Sign In",
    component: () => import("../views/SignIn.vue"),
  },
  {
    path: "/new-entry",
    name: "New Entry",
    component: () => import("../views/NewEntry.vue"),
  },
  {
    path: "/profile",
    name: "Profile",
    component: () => import("../views/Profile.vue"),
  },
  {
    path: "/search",
    name: "Search",
    component: () => import("../views/Search.vue"),
  },
];

const router = new VueRouter({
  routes,
});

router.beforeEach((to, from, next) => {
  if(to.matched.some(record => record.meta.requiresAuth)) {
    if (store.getters.loggedIn) {
      next()
      return
    }
    next('/')
  } else {
    next()
  }
});

export default router;
