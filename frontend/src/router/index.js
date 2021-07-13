import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import store from "../store.js";
// import myEntries from './views/MyEntries.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/debug",
    name: "Debug",
    component: () => import("../views/Debug.vue"),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/new-entry",
    name: "New Entry",
    component: () => import("../views/NewEntry.vue"),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/profile",
    name: "Profile",
    component: () => import("../views/Profile.vue"),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/search",
    name: "Search",
    component: () => import("../views/Search.vue"),
    meta: {
      requiresAuth: true,
    },
  },
  // {
  //   path: "/myentries",
  //   name: "myentries",
  //   component: () => import("../views/MyEntries.vue"),
  //   meta: {
  //     requiresAuth: true,
  //   },
  // },
];

const router = new VueRouter({
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (store.getters.loggedIn) {
      next();
      return;
    }
    next("/");
  } else {
    next();
  }
});

export default router;
