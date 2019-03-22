<!--- This component acts as a page for a specific employer ---> 
<template>
  <div class="container">
    <EmployerPageInfo :employer="employer"/>
    <div v-if="coops.length">
      <EmployerPageCoopItem v-for="coop in coops" :key="coop.id" :coop="coop"/>
    </div>
    <p v-else>Employer has no co-op terms.</p>
  </div>
</template>

<script>
import EmployerPageInfo from "./EmployerPageInfo.vue";
import EmployerPageCoopItem from "./EmployerPageCoopItem.vue";
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
    // Initializing with fetched employer from backend
    AXIOS.get(`/employer/` + this.employerEmail)
      .then(response => {
        // JSON responses are automatically parsed.
        this.employer = response.data;
      })
      .catch(e => {
        this.error = e;
      });
    // Get all coop terms for this employer 
    AXIOS.get(`/coops/` + this.employerEmail)
      .then(response => {
        // JSON responses are automatically parsed.
        this.coops = response.data;
      })
      .catch(e => {
        this.error = e;
      });
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
  }
};
</script>

<style>
.container {
  align-items: center;
}
</style>
