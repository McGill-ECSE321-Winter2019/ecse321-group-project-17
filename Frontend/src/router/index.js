import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home";
import StudentPage from "@/components/StudentPage";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home
    },
    {
      path: "/student/:id/",
      name: "StudentPage",
      component: StudentPage,
      props: true
    }
  ]
});
