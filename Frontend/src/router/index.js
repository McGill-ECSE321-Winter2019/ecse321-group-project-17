import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home";
import LoginPage from "@/components/LoginPage";
import StudentPage from "@/components/StudentPage";
import EmployerPage from "@/components/EmployerPage";
import CoopPage from "@/components/CoopPage";
import Statistics from "@/components/Statistics";
import ReportPage from "@/components/ReportPage";
import NotificationPage from "@/components/NotificationPage";
import AccountPage from "@/components/AccountPage";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/login/",
      name: "LoginPage",
      component: LoginPage
    },
    {
      path: "/account/",
      name: "AccountPage",
      component: AccountPage
    },
    {
      path: "/",
      redirect: "/login/"
    },
    {
      path: "/home/",
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
    },
    {
      path: "/report/:id/",
      name: "ReportPage",
      component: ReportPage,
      props: true
    }
  ]
});
