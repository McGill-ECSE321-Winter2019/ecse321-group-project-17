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
      <span class="badge badge-success">Employer</span>
    </td>
    <td class="td-name" v-on:click="goToEmployerPage">
      <span>{{ employer.name }}</span>
    </td>
    <td class="td-email" v-on:click="goToEmployerPage">
      <span>{{ employer.email }}</span>
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

// Fetch the employer with specified email from the backend
let getEmployer = async function(email) {
  return await AXIOS.get(`/employer/` + email)
    .then(response => {
      return response.data;
    })
    .catch(e => {
      this.error = e;
    });
};
export default {
  props: {
    employer: {
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
    goToEmployerPage: function() {
      let email = this.employer.email;
      getEmployer(this.employer.email).then(function(res) {
        // Go to the employer's page
        Router.push({
          path: "/employer/",
          name: "EmployerPage",
          params: {
            urlEmail: res.email,
            employerEmail: email // Pass as prop to the EmployerPage that will be rendered
          }
        });
      });
    },
    updateState: function() {
      this.selected = !this.selected;
      this.$emit("clicked", this.selected, this.employer);
    }
  }
};
</script>

<style scoped>
span {
  color: rgb(241, 240, 240);
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