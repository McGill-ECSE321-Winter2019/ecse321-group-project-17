<!--- This component acts as a page for a specific employer ---> 
<template>
  <div class="container">
    <EmployerPageInfo :employer="employer"/>
    <button
      type="button"
      id="button"
      v-on:click="send(employer)"
      class="btn btn-danger btn-lg"
      v-b-tooltip.hover
      title="Click to send notification to this employer"
    >Send Notification</button>
    <div v-if="coops.length" v-b-tooltip.hover title="Click to see this Coop">
      <EmployerPageCoopItem v-for="coop in orderedCoops" :key="coop.id" :coop="coop"/>
    </div>
    <p v-else>Employer has no co-op terms.</p>
  </div>
</template>

<script>
import EmployerPageInfo from "./EmployerPageInfo.vue";
import EmployerPageCoopItem from "./EmployerPageCoopItem.vue";
import Router from "../router";
import axios from "axios";

var config = require("../../config");

var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

// Axios config
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  components: {
    EmployerPageInfo,
    EmployerPageCoopItem
  },
  props: {
    employerEmail: String
  },
  methods: {
    send: function(employer) {
      var pseudoSelect = [employer];
      Router.push({
        path: "/notifications/",
        name: "NotificationPage",
        params: {
          selected: pseudoSelect
        }
      });
    }
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
      if (typeof this.employerEmail === "undefined") {
        // Page has been refreshed, must get employer email explicitly from URL
        let pathEmail = Router.currentRoute.path.split("/")[2];

        // Fetch employer from backend
        AXIOS.get(`/employer/` + pathEmail)
          .then(response => {
            this.employer = response.data;
          })
          .catch(e => {
            this.error = e;
          });
        // Get all coop terms for this employer
        AXIOS.get(`/employer/coops/` + pathEmail)
          .then(response => {
            this.coops = response.data;
          })
          .catch(e => {
            this.error = e;
          });
      } else {
        // Initializing with fetched employer from backend
        AXIOS.get(`/employer/` + this.employerEmail)
          .then(response => {
            this.employer = response.data;
          })
          .catch(e => {
            this.error = e;
          });
        // Get all coop terms for this employer
        AXIOS.get(`/employer/coops/` + this.employerEmail)
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
      employer: {
        type: Object
      },
      coops: {
        type: Object
      },
      error: ""
    };
  },
  computed: {
    // Sorts coops by start date
    orderedCoops: function() {
      return _.sortBy(this.coops, "startDate").reverse();
    }
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
  background-color: red;
  margin: auto;
}
</style>
