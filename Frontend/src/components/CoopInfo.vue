<!--- This component renders the information of a specific coop for a coop page -->
<template>
  <div id="info-container" class="card" v-bind:style="{ backgroundColor : bgColor}">
    <h3 v-bind:style="{ color : textColor}">
      <span class="badge badge-warning">Coop</span> &nbsp; &nbsp;
      <strong>{{coop.title}}</strong>
    </h3>
    <br>
    <span v-bind:style="{ color : textColor}">
      <b v-bind:style="{ color : textColor}">Status:</b>
    </span>
    <span class="badge badge-info" v-if="coop.status === 'NotStarted'">Not Started</span>
    <span class="badge badge-warning" v-else-if="coop.status === 'InProgress'">In Progress</span>
    <span class="badge badge-success" v-else-if="coop.status === 'Completed'">Complete</span>
    <span class="badge badge-danger" v-else>Incomplete</span>
    <p/>
    <p @click="goToStudentPage" v-bind:style="{ color : textColor}">
      <b v-bind:style="{ color : textColor}">Student:</b>
     {{ coop.student.name }}
    </p>
    <p @click="goToEmployerPage" v-bind:style="{ color : textColor}">
      <b v-bind:style="{ color : textColor}">Company:</b>
      {{ coop.employer.company }}
    </p>
    <p @click="goToEmployerPage" v-bind:style="{ color : textColor}">
      <b v-bind:style="{ color : textColor}">Employer:</b>
      {{ coop.employer.name }}
    </p>
    <p v-bind:style="{ color : textColor}">
      <b v-bind:style="{ color : textColor}">Salary per hour:</b>
      {{ coop.salaryPerHour }}
    </p>
    <p v-bind:style="{ color : textColor}">
      <b v-bind:style="{ color : textColor}">Hours per week:</b>
      {{ coop.hoursPerWeek }}
    </p>
    <p v-bind:style="{ color : textColor}">
      <b v-bind:style="{ color : textColor}">Dates:</b>
      {{ coop.startDate }} to {{ coop.endDate }}
    </p>
    <p v-bind:style="{ color : textColor}">
      <b v-bind:style="{ color : textColor}">Location:</b>
      {{ coop.address }}
    </p>
  </div>
</template>

<script>
import Router from "../router";

export default {
  props: {
    coop: {
      type: Object,
      required: true
    }
  },
  methods: {
    goToEmployerPage: function() {
      // Go to the employer associated with this coop term
      Router.push({
        path: "/employer/",
        name: "EmployerPage",
        params: {
          emailUrl: this.coop.employer.email,
          employerEmail: this.coop.employer.email
        }
      });
    },
    goToStudentPage: function() {
      // Go to the student associated with this coop term
      Router.push({
        path: "/student/",
        name: "StudentPage",
        params: {
          emailUrl: this.coop.student.email,
          studentEmail: this.coop.student.email
        }
      });
    },
    setDarkMode: function() {
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") {
        this.bgColor = "rgb(53, 58, 62)";
        this.textColor = "white";
      } else {
        this.bgColor = "rgb(248, 249, 251)";
        this.textColor = "black";
      }
    }
  },
  created() {
    var isLoggedIn = localStorage.getItem("isLoggedIn");
    // Send the user back to the login page if they are not logged in
    if (isLoggedIn === "false") {
      Router.push({
        path: "/login/",
        name: "LoginPage"
      });
    } else {
      // Fetches the user's selected UI mode from browser local storage
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") {
        this.bgColor = "rgb(53, 58, 62)";
        this.textColor = "white";
      } else {
        this.bgColor = "rgb(248, 249, 251)";
        this.textColor = "black";
      }
    }
  },
  data() {
    return {
      bgColor: "",
      textColor: ""
    };
  },
  mounted() {
    // Listens to the setDarkModeState event emitted from the LogoBar component
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style>
#info-container {
  width: 65%;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
  display: inline-block;
}

h4,
p {
  color: rgb(241, 240, 240);
}
</style>