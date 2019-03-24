import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home";
import StudentPage from "@/components/StudentPage";
import EmployerPage from "@/components/EmployerPage";
import CoopPage from "@/components/CoopPage";
import Statistics from "@/components/Statistics";
import NotificationPage from "@/components/NotificationPage";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home
    },
    {
      path: "/student/:urlEmail/",
      name: "StudentPage",
      component: StudentPage,
      props: true
    },
    {
      path: "/employer/:urlEmail/",
      name: "EmployerPage",
      component: EmployerPage,
      props: true
    },
    {
      path: "/coop/:id/",
      name: "CoopPage",
      component: CoopPage,
      props: true
    },
    {
      path: "/statistics/",
      name: "Statistics",
      component: Statistics,
      props: true
    },
    {
      path: "/notifications/",
      name: "NotificationPage",
      component: NotificationPage,
      props: true
    }
  ]
});
