<template>
  <!-- tr = table row, td = table data --->
  <tr v-bind:style="{ backgroundColor: bgColor }">
    <td class="td-checkbox">
      <input
        class="form-check-input position-static checkbox"
        type="checkbox"
        id="blankCheckbox"
        value="option1"
        aria-label="..."
        @change="updateState"
        v-model="selected"
      >
    </td>
    <td class="td-badge">
      <span class="badge badge-success">Employer</span>
    </td>
    <td class="td-name" v-on:click="goToEmployerPage">
      <span v-bind:style="{ color: textColor }">{{ employer.name }}</span>
    </td>
    <td class="td-email" v-on:click="goToEmployerPage">
      <span v-bind:style="{ color: textColor }">{{ employer.email }}</span>
    </td>
  </tr>
</template>

<script>
import Router from "../router";
import axios from "axios";

var config = require("../../config");

// Axios config
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
      selected: false,
      bgColor: "rgb(248, 249, 251)",
      textColor: "black"
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
      this.$emit("child-clicked", this.selected, this.employer);
    },
    setSelectedState: function(state) {
      this.selected = state;
      this.$emit("child-clicked", this.selected, this.employer);
    },
    setDarkMode: function(darkModeOn) {
      if (darkModeOn) {
        this.bgColor = "rgb(53, 58, 62)";
        this.textColor = "white";
      } else {
        this.bgColor = "rgb(248, 249, 251)";
        this.textColor = "black";
      }
    }
  },
  created() {
    var darkModeOn = localStorage.getItem("DarkModeOn");
    if (darkModeOn === "true") {
      this.bgColor = "rgb(53, 58, 62)";
      this.textColor = "white";
    } else {
      this.bgColor = "rgb(248, 249, 251)";
      this.textColor = "black";
    }
  },
  mounted() {
    this.$eventHub.$on("setAllSelectedState", this.setSelectedState);
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style scoped>
span {
  color: rgb(241, 240, 240);
}

/* tr {
  background-color: rgb(53, 58, 62);
} */

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