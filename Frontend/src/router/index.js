import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home";
import StudentPage from "@/components/StudentPage";
import EmployerPage from "@/components/EmployerPage";

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
    },
    {
      path: "/employer/:id/",
      name: "EmployerPage",
      component: EmployerPage,
      props: true
    }
  ]
});
