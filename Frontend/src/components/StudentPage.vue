<!--- This component acts as a page for a specific student ---> 
<template>
  <div class="container">
    <StudentPageInfo :student="student"/>
    <button
      type="button"
      id="button"
      v-on:click="send(student)"
      class="btn btn-danger btn-lg"
      v-b-tooltip.hover
      title="Click to save changes"
    >Send Notification</button>
    <div v-if="coops.length" v-b-tooltip.hover title="Click to see this Coop">
      <StudentPageCoopItem v-for="coop in orderedCoops" :key="coop.id" :coop="coop"/>
    </div>
    <p v-else v-bind:style="{ color: textColor }">Student has no co-op terms.</p>
  </div>
</template>

<script>
import StudentPageInfo from "./StudentPageInfo.vue";
import StudentPageCoopItem from "./StudentPageCoopItem.vue";
import Router from "../router";
import axios from "axios";
import _ from "lodash";

var config = require("../../config");

// Axios config
var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  components: {
    StudentPageInfo,
    StudentPageCoopItem
  },
  props: {
    studentEmail: String
  },

  created: function() {
    var isLoggedIn = localStorage.getItem("isLoggedIn");
    // Send the user back to the login page if they are not logged in
    if (isLoggedIn === "false") {
      Router.push({
        path: "/login/",
        name: "LoginPage"
      });
    } else {
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") {
        this.bgColor = "rgb(53, 58, 62)";
        this.textColor = "white";
      } else {
        this.bgColor = "rgb(248, 249, 251)";
        this.textColor = "black";
      }

      if (typeof this.studentEmail === "undefined") {
        // Page has been refreshed, must get student email explicitly
        let pathEmail = Router.currentRoute.path.split("/")[2];

        // Fetch student from backend
        AXIOS.get(`/student/` + pathEmail)
          .then(response => {
            this.student = response.data;
          })
          .catch(e => {
            this.error = e;
          });
        // Get all coop terms for this student
        AXIOS.get(`/student/coops/` + pathEmail)
          .then(response => {
            this.coops = response.data;
          })
          .catch(e => {
            this.error = e;
          });
      } else {
        // Initializing with fetched student from backend
        AXIOS.get(`/student/` + this.studentEmail)
          .then(response => {
            this.student = response.data;
          })
          .catch(e => {
            this.error = e;
          });
        // Get all coop terms for this student
        AXIOS.get(`/student/coops/` + this.studentEmail)
          .then(response => {
            this.coops = response.data;
          })
          .catch(e => {
            this.error = e;
          });
      }
    }
  },
  data() {
    return {
      student: {
        type: Object
      },
      coops: {
        type: Object
      },
      error: "",
      bgColor: "",
      textColor: ""
    };
  },
  computed: {
    orderedCoops: function() {
      return _.sortBy(this.coops, "startDate").reverse();
    }
  },
  methods: {
    setDarkMode: function() {
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") {
        this.bgColor = "rgb(53, 58, 62)";
        this.textColor = "white";
      } else {
        this.bgColor = "rgb(248, 249, 251)";
        this.textColor = "black";
      }
    },
    send: function(student) {
      var fakeSelect = [student];
      Router.push({
        path: "/notifications/",
        name: "NotificationPage",
        params: {
          selected: fakeSelect
        }
      });
    }
  },
  mounted() {
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style>
.container {
  align-items: center;
}
#button {
  width: 20%;
  color: white;
  margin-top: 15px;
  border: 0px;
}
</style> 
