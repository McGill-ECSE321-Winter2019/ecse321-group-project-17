<template>
  <div id="home-container" class="card">
    <table>
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
      <HomeListStudentItem v-for="student in students" :key="student.id" :student="student"/>
      <HomeListEmployerItem v-for="employer in employers" :key="employer.id" :employer="employer"/>
    </table>
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
      })
      .catch(e => {
        this.error = e;
      });
    // Fetch all employers from backend
    AXIOS.get(`/employers`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.employers = response.data;
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
      }
    };
  }
};
</script>

<style>
h3 {
  color: white;
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
