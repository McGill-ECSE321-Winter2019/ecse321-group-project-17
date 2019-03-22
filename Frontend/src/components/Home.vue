<template>
  <div id="home-container" class="card">
    <table v-if="studentsLoaded && employersLoaded">
      <tr id="tr-heading">
        <td></td>
        <td></td>
        <td>
          <h3>Name</h3>
        </td>
        <td>
          <h3>Email</h3>
        </td>
      </tr>
      <HomeListStudentItem v-for="student in students" :key="student.email" :student="student"/>
      <HomeListEmployerItem v-for="employer in employers" :key="employer.email" :employer="employer"/>
    </table>
    <h2 v-else id="h2-loading">Loading...</h2>
  </div>
</template>
    
<script>
import HomeListStudentItem from "./HomeListStudentItem.vue";
import HomeListEmployerItem from "./HomeListEmployerItem.vue";
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
    HomeListStudentItem,
    HomeListEmployerItem
  },
  created: function() {
    // Fetch all students from backend
    AXIOS.get(`/students`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.students = response.data;
        this.studentsLoaded = true;
      })
      .catch(e => {
        this.error = e;
      });
    // Fetch all employers from backend
    AXIOS.get(`/employers`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.employers = response.data;
        this.employersLoaded = true;
      })
      .catch(e => {
        this.error = e;
      });
  },
  data() {
    return {
      students: {
        type: Object
      },
      employers: {
        type: Object
      },
      studentsLoaded: false,
      employersLoaded: false
    };
  }
};
</script>

<style>
h2,
h3 {
  color: white;
}

#h2-loading {
  text-align: center;
}

#home-container {
  width: 70%;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}

#tr-heading {
  background-color: rgb(53, 58, 62);
  border-bottom: thick solid gray;
  border-bottom-color: rgb(27, 27, 27);
}

td {
  text-align: left;
  padding-left: 15px;
}
</style>
