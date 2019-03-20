<!--- This component acts as a page for a specific student ---> 
<template>
  <div class="container">
    <StudentPageInfo :student="student"/>
    <StudentPageCoopItem/>
  </div>
</template>

<script>
import StudentPageInfo from "./StudentPageInfo.vue";
import StudentPageCoopItem from "./StudentPageCoopItem.vue";
import axios from "axios";

var config = require("../../config");

var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

// let studentID = this.$route.params.id;

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
    AXIOS.get(`/student/student1@mcgill.ca`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.student = response.data;
      })
      .catch(e => {
        this.errorPerson = e;
      });
  },
  data() {
    return {
      student: {
        type: Object
      }
    };
  }
};
</script>

<style>
.container {
  align-items: center;
}
</style>
