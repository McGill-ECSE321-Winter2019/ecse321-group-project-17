<template>
  <div>
    <HomeFilters/>
    <div id="home-container" class="card">
      <div>
        <table v-if="studentsLoaded && employersLoaded">
          <tr id="tr-heading">
            <td class="td-checkbox">
              <input
                class="form-check-input position-static checkbox"
                type="checkbox"
                id="blankCheckbox"
                value="option1"
                aria-label="..."
              >
            </td>
            <td class="td-badge1">
              <span class="badge badge-light">Type</span>
            </td>
            <td class="td-name">
              <h3>Name</h3>
            </td>
            <td class="td-email">
              <h3>Email</h3>
            </td>
          </tr>
        </table>
      </div>
      <div id="scroll-container">
        <table v-if="studentsLoaded && employersLoaded">
          <HomeListStudentItem
            v-for="student in students"
            :key="student.email"
            :student="student"
            @clicked="handleSelect"
          />
          <HomeListEmployerItem
            v-for="employer in employers"
            :key="employer.email"
            :employer="employer"
            @clicked="handleSelect"
          />
        </table>
        <h2 v-else id="h2-loading">Loading...</h2>
      </div>
      <div id="stats" v-on:click="goToStatistics">
        Generate Statistics
      </div>
    </div>
  </div>
</template>
    
<script>
import HomeListStudentItem from "./HomeListStudentItem.vue";
import HomeListEmployerItem from "./HomeListEmployerItem.vue";
import Router from "../router";
import HomeFilters from "./HomeFilters.vue";
import axios from "axios";

var config = require("../../config");

var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

// Remove a person from the selected list
let remove = function(context, person) {
  for (var i = 0; i < context.selected.length; i++) {
    if (context.selected[i].email === person.email) {
      context.selected.splice(i, 1);
    }
  }
};

export default {
  components: {
    HomeListStudentItem,
    HomeListEmployerItem,
    HomeFilters
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
      employersLoaded: false,
      selected: []
    };
  },
  methods: {
    handleSelect: function(isSelected, student) {
      if (isSelected) {
        this.selected.push(student);
      } else {
        remove(this, student);
      }
    },
    goToStatistics: function() {
        Router.push({
            path: "/statistics/",
            name: "Statistics",
            params: {
              students: this.students,
              employers: this.employers
          }
        });

    }
  }
};
</script>

<style>
p,
h2,
h3 {
  color: white;
}

#h2-loading {
  text-align: center;
}

#home-container {
  width: 70%;
  min-height: 400px;
  min-width: 550px;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}

#scroll-container {
  min-height: 400px;
  overflow: auto;
}

#tr-heading {
  background-color: rgb(53, 58, 62);
  border-bottom: thick solid gray;
  border-bottom-color: rgb(27, 27, 27);
}

.checkbox {
  margin-left: 20px;
  margin-right: 10px;
}

.td-checkbox {
  width: 5%;
  text-align: left;
  padding-left: 15px;
}

.td-badge1 {
  width: 5%;
  text-align: left;
  padding-left: 15px;
  padding-right: 29px;
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

td {
  text-align: left;
  padding-left: 15px;
}

#stats {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
}
</style>
