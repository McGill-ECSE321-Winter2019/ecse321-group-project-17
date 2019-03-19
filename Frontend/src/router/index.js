import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import StudentInfo from '@/components/StudentInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/student',
      name: 'StudentInfo',
      component: StudentInfo
    }
  ]
})
