import Vue from "vue";
import Router from "vue-router";
import Hello from "@/components/Hello";
import StudentPage from "@/components/StudentPage";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Hello",
      component: Hello
    },
    {
      path: "/student/:id/",
      name: "StudentPage",
      component: StudentPage
    }
  ]
});
