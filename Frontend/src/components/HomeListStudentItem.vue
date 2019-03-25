<template>
  <!-- tr = table row, td = table data --->
  <tr>
    <td class="td-checkbox">
      <input
        class="form-check-input position-static checkbox"
        type="checkbox"
        id="blankCheckbox"
        value="option1"
        aria-label="..."
        @change="updateState"
      >
    </td>
    <td class="td-badge">
      <span class="badge badge-primary">Student</span>
    </td>
    <td class="td-name" v-on:click="goToStudentPage">
      <span>{{ student.name }}</span>
    </td>
    <td class="td-email" v-on:click="goToStudentPage">
      <span>{{ student.email }}</span>
    </td>
    <td class="td-email" v-on:click="goToStudentPage">
      <span>{{ student.email }}</span>
    </td>
  </tr>
</template>

<script>
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

// Fetch the student with specified email from the backend
let getStudent = async function(email) {
  return await AXIOS.get(`/student/` + email)
    .then(response => {
      return response.data;
    })
    .catch(e => {
      this.error = e;
    });
};

export default {
  props: {
    student: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      selected: false
    };
  },
  methods: {
    goToStudentPage: function() {
      let email = this.student.email;
      getStudent(this.student.email).then(function(res) {
        // Go to the student's page
        Router.push({
          path: "/student/",
          name: "StudentPage",
          params: {
            urlEmail: res.email,
            studentEmail: email // Pass as prop to the StudentPage that will be rendered
          }
        });
      });
    },
    updateState: function() {
      this.selected = !this.selected;
      this.$emit("clicked", this.selected, this.student);
    }
  }
};
</script>

<style scoped>
span {
  color: white;
}

tr {
  background-color: rgb(53, 58, 62);
}

tr:hover {
  background-color: rgb(96, 101, 105);
}

.td-checkbox {
  width: 5%;
  text-align: left;
  padding-left: 15px;
}

.td-badge {
  width: 5%;
  text-align: left;
  padding-left: 15px;
}

.td-name {
  width: 20%;
  text-align: left;
  padding-left: 15px;
}

.td-email {
  width: 60%;
  text-align: left;
}

.checkbox {
  margin-left: 20px;
  margin-right: 10px;
}
</style>