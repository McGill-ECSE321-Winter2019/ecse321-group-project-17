<!--- This component is the home page --->
<template>
  <div>
    <div id="home-container" class="card" v-bind:style="{ backgroundColor: bgColor }">
      <div class="col-md-4" v-b-tooltip.hover title="Select to view Students, Employers, or both">
        <select
          v-model="selectedProfile"
          class="mr-sm-2 custom-select home-filter-box"
          @change="updateProfileTypeSelected"
        >
          <option>Students &amp; Employers</option>
          <option>Students</option>
          <option>Employers</option>
        </select>
      </div>
      <div>
        <table v-if="studentsLoaded && employersLoaded">
          <tr id="tr-heading" v-bind:style="{ backgroundColor: bgColor }">
            <td id="td-checkbox">
              <input
                class="form-check-input position-static"
                type="checkbox"
                id="home-checkbox"
                value="option1"
                aria-label="..."
                @change="updateAllSelectedState"
              >
            </td>
            <td id="td-badge1">
              <span class="badge badge-light">&nbsp;&nbsp;&nbsp; Type &nbsp;&nbsp;&nbsp;</span>
            </td>
            <td id="td-name">
              <h4 v-bind:style="{ color: textColor }">Name</h4>
            </td>
            <td id="td-email">
              <h4 v-bind:style="{ color: textColor }">Email</h4>
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
          <HomeListStudentItem
            v-for="student in externalStudents"
            :key="student.studentID"
            :student="student.person"
            @child-clicked="handleSelect"
          />
          <HomeListEmployerItem
            v-for="employer in orderedEmployers"
            :key="employer.email"
            :employer="employer"
            @child-clicked="handleSelect"
          />
        </table>
        <h2 v-else id="h2-loading" v-bind:style="{ color: textColor }">Loading...</h2>
      </div>
    </div>
    <div>
      <button
        id="stats"
        type="button"
        class="btn btn-light btn-lg"
        v-on:click="goToStatistics"
        v-b-tooltip.hover
        title="Select to view Statistics Page"
      >Generate Statistics</button>

      <button
        id="notifs"
        type="button"
        class="btn btn-light btn-lg"
        v-on:click="goToNotifications"
        v-b-tooltip.hover
        title="Select to send a notification"
      >Create Notification</button>
    </div>
  </div>
</template>
    
<script>
import HomeListStudentItem from "./HomeListStudentItem.vue";
import HomeListEmployerItem from "./HomeListEmployerItem.vue";
import Router from "../router";
import axios from "axios";
import _ from "lodash";

var config = require("../../config");

// Axios config
var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

// Axios config for student POV
backendUrl = "https://cooperator-backend-00.herokuapp.com";
var AXIOS_Student = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

// Axios config for employer POV
// backendUrl =
//   "https://ecse321-group12.herokuapp.com: ??? ";
// var AXIOS_Employer = axios.create({
//   baseURL: backendUrl,
//   headers: { "Access-Control-Allow-Origin": frontendUrl }
// });

// Remove a person from the selected profiles list
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
    HomeListEmployerItem
  },
  created: function() {
    var isLoggedIn = localStorage.getItem("isLoggedIn");
    // Send the user back to the login page if they are not logged in
    if (isLoggedIn === "false") {
      Router.push({
        path: "/login/",
        name: "LoginPage"
      });
    }

    var darkModeOn = localStorage.getItem("DarkModeOn");
    if (darkModeOn === "true") {
      this.bgColor = "rgb(53, 58, 62)";
      this.textColor = "white";
    } else {
      this.bgColor = "rgb(248, 249, 251)";
      this.textColor = "black";
    }
    // Fetch all students from backend
    AXIOS.get(`/students`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.students = response.data;
      })
      .catch(e => {
        this.error = e;
      });
    // Fetch all students from student POV database
    AXIOS_Student.get("/getAllStudents/")
      .then(response => {
        this.externalStudents = response.data;
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
    // Fetch all employers from employer POV database
  },
  data() {
    return {
      students: {
        type: Object
      },
      externalStudents: {
        type: Array
      },
      employers: {
        type: Object
      },
      studentsLoaded: false,
      employersLoaded: false,
      allSelected: false,
      selected: [],
      selectedProfile: "Students & Employers",
      bgColor: "",
      textColor: ""
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
  mounted() {
    this.$root.$on("setDarkModeState", this.setDarkMode);
  },
  methods: {
    updateProfileTypeSelected: function() {
      this.studentsLoaded = false;
      this.employersLoaded = false;

      if (this.selectedProfile === "Students & Employers") {
        // Fetch all students and employers from backend
        AXIOS.get(`/students`)
          .then(response => {
            this.students = response.data;
          })
          .catch(e => {
            this.error = e;
          });
        AXIOS_Student.get("/getAllStudents/")
          .then(response => {
            this.externalStudents = response.data;
            this.studentsLoaded = true;
          })
          .catch(e => {
            this.error = e;
          });
        AXIOS.get(`/employers`)
          .then(response => {
            this.employers = response.data;
            this.employersLoaded = true;
          })
          .catch(e => {
            this.error = e;
          });
      } else if (this.selectedProfile === "Students") {
        // Fetch only students from the backend
        AXIOS.get(`/students`)
          .then(response => {
            this.students = response.data;
          })
          .catch(e => {
            this.error = e;
          });
        AXIOS_Student.get("/getAllStudents/")
          .then(response => {
            this.externalStudents = response.data;
            this.studentsLoaded = true;
          })
          .catch(e => {
            this.error = e;
          });
        this.employers = [];
        this.employersLoaded = true;
      } else if (this.selectedProfile === "Employers") {
        // Fetch only employers from the backend
        AXIOS.get(`/employers`)
          .then(response => {
            this.employers = response.data;
            this.employersLoaded = true;
          })
          .catch(e => {
            this.error = e;
          });
        this.students = [];
        this.externalStudents = [];
        this.studentsLoaded = true;
      }
    },
    handleSelect: function(isSelected, person) {
      // Adds a profile to the selected list if their box is checked
      if (isSelected) {
        this.selected.push(person);
      } else {
        remove(this, person);
      }
    },
    updateAllSelectedState: function() {
      // Checks or unchecks all boxes in the list
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
          selectedProfile: this.selectedProfile
        }
      });
    },
    goToNotifications: function() {
      // A notification must be sent to at least one profile
      if (this.selected.length != 0) {
        Router.push({
          path: "/notifications/",
          name: "NotificationPage",
          params: {
            selected: this.selected
          }
        });
      } else {
        alert("No profiles selected!");
      }
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
  }
};
</script>

<style>
p,
h2,
h4 {
  color: white;
}

.light-button {
  background-color: rgb(195, 201, 206);
  border-color: rgb(129, 133, 136);
}

.home-filter-box {
  margin-bottom: 10px;
}

#h2-loading {
  text-align: center;
}

#home-container {
  width: 70%;
  max-height: 580px;
  min-width: 550px;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
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

#home-checkbox {
  margin-left: 20px;
  margin-right: 10px;
}

#td-checkbox {
  width: 5%;
  text-align: left;
  padding-left: 15px;
}

#td-badge1 {
  width: 5%;
  text-align: left;
  padding-left: 15px;
  padding-right: 29px;
}

#td-name {
  width: 20%;
  text-align: left;
  padding-left: 15px;
}

#td-email {
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
