<template>
  <div>
    <HomeFilters
      @updateCoopNumber="updateCoopNumber"
      @updateStartTerm="updateStartTerm"
      @updateEndTerm="updateEndTerm"
      @updateProfile="updateProfile"
    />
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
                @change="updateAllSelectedState"
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
            v-for="student in orderedStudents"
            :key="student.email"
            :student="student"
            @child-clicked="handleSelect"
          />
          <HomeListEmployerItem
            v-for="employer in orderedEmployers"
            :key="employer.email"
            :employer="employer"
            @child-clicked="handleSelect"
          />
        </table>
        <h2 v-else id="h2-loading">Loading...</h2>
      </div>
    </div>
    <div>
      <button id="stats" type="button" class="btn btn-light btn-lg" v-on:click="goToStatistics">
        Generate Statistics
        <img src="./../assets/line-chart.png">
      </button>
      <button id="notifs" type="button" class="btn btn-light btn-lg" v-on:click="goToNotifications">
        Create Notification
        <img src="./../assets/envelope.png">
      </button>
    </div>
  </div>
</template>
    
<script>
import HomeListStudentItem from "./HomeListStudentItem.vue";
import HomeListEmployerItem from "./HomeListEmployerItem.vue";
import Router from "../router";
import HomeFilters from "./HomeFilters.vue";
import axios from "axios";
import _ from "lodash";

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
      allSelected: false,
      selected: [],
      selectedProfile: "",
      selectedStartTerm: "",
      selectedEndTerm: "",
      selectedCoopNumber: ""
    };
  },
  computed: {
    orderedStudents: function() {
      return _.sortBy(this.students, "name");
    },
    orderedEmployers: function() {
      return _.sortBy(this.employers, "name");
    }
  },
  methods: {
    updateProfile: function(value) {
      if (this.selectedProfile === value) return; // Nothing to update

      this.studentsLoaded = false;
      this.employersLoaded = false;
      this.selectedProfile = value;

      if (this.selectedProfile === "Students & Employers") {
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
            this.employers = response.data;
            this.employersLoaded = true;
          })
          .catch(e => {
            this.error = e;
          });
      } else if (this.selectedProfile === "Students") {
        AXIOS.get(`/students`)
          .then(response => {
            this.students = response.data;
            this.studentsLoaded = true;
          })
          .catch(e => {
            this.error = e;
          });
        this.employers = [];
        this.employersLoaded = true;
      } else if (this.selectedProfile === "Employers") {
        AXIOS.get(`/employers`)
          .then(response => {
            this.employers = response.data;
            this.employersLoaded = true;
          })
          .catch(e => {
            this.error = e;
          });
        this.students = [];
        this.studentsLoaded = true;
      }
    },
    updateCoopNumber: function(value) {
      this.selectedCoopNumber = value;
    },
    updateStartTerm: function(value) {
      this.selectedStartTerm = value;
    },
    updateEndTerm: function(value) {
      this.selectedEndTerm = value;
    },
    handleSelect: function(isSelected, person) {
      if (isSelected) {
        this.selected.push(person);
      } else {
        remove(this, person);
      }
    },
    updateAllSelectedState: function() {
      this.allSelected = !this.allSelected;
      this.$eventHub.$emit("setAllSelectedState", this.allSelected);
    },
    goToStatistics: function() {
      Router.push({
        path: "/statistics/",
        name: "Statistics",
        params: {
          students: this.students,
          employers: this.employers,
          selectedProfile: this.selectedProfile,
          selectedStartTerm: this.selectedStartTerm,
          selectedEndTerm: this.selectedEndTerm,
          selectedCoopNumber: this.selectedCoopNumber
        }
      });
    },
    goToNotifications: function() {
      Router.push({
        path: "/notifications/",
        name: "NotificationPage"
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

.light-button {
  background-color: rgb(195, 201, 206);
  border-color: rgb(129, 133, 136);
}

#h2-loading {
  text-align: center;
}

#home-container {
  width: 70%;
  max-height: 380px;
  min-width: 550px;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}

#scroll-container {
  max-height: 380px;
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
  margin-top: 15px;
  margin-right: 15px;
}

#notifs {
  margin-left: 15px;
  margin-top: 15px;
}
</style>
