<!--- This component acts as a page for a specific employer ---> 
<template>
  <div class="container">
    <EmployerPageInfo :employer="employer"/>
    <div v-if="coops.length">
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
  created: function() {
    if (typeof this.employerEmail === "undefined") {
      // Page has been refreshed, must get employer email explicitly
      let pathEmail = Router.currentRoute.path.split("/")[2];

      // Fetch employer from backend
      AXIOS.get(`/employer/` + pathEmail)
        .then(response => {
          // JSON responses are automatically parsed.
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
</style>
