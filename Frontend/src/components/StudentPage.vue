<!--- This component acts as a page for a specific student ---> 
<template>
  <div class="container">
    <StudentPageInfo :student="student"/>
    <div v-if="coops.length">
      <StudentPageCoopItem v-for="coop in orderedCoops" :key="coop.id" :coop="coop"/>
    </div>
    <p v-else>Student has no co-op terms.</p>
  </div>
</template>

<script>
import StudentPageInfo from "./StudentPageInfo.vue";
import StudentPageCoopItem from "./StudentPageCoopItem.vue";
import axios from "axios";
import _ from 'lodash';

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
    StudentPageInfo,
    StudentPageCoopItem
  },
  props: {
    studentEmail: String
  },
  created: function() {
    // Initializing with fetched student from backend
    AXIOS.get(`/student/` + this.studentEmail)
      .then(response => {
        // JSON responses are automatically parsed.
        this.student = response.data;
      })
      .catch(e => {
        this.error = e;
      });
    // Get all coop terms for this student
    AXIOS.get(`/coops/` + this.studentEmail)
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
      student: {
        type: Object
      },
      coops: {
        type: Object
      },
      error: ""
    };
  },
  computed: {
    orderedCoops: function () {
      return _.sortBy(this.coops, 'startDate')
    }
  },
};
</script>

<style>
.container {
  align-items: center;
}
</style>
