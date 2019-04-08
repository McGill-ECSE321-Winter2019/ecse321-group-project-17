<!--- This component acts as a page to view a speicific report --->
<template>
  <div v-if="this.reportLoaded" class="container">
    <div class="card" id="info" v-bind:style="{ backgroundColor : bgColor}">
      <h3 v-bind:style="{ color : textColor}">
        <span class="badge badge-info">Report</span> &nbsp;
        <small @click="goToStudentPage">&nbsp; {{coop.student.name}} </small>
        &nbsp;-&nbsp; 
        <small @click="goToCoopPage"> {{coop.title}} at {{coop.employer.company}} </small>
      </h3>
      <br>
      <p>
      <span>
        <b v-bind:style="{ color : textColor}">Report Type:</b>
      </span>
      <span
        v-if="report.reportType === 'Contract'"
        v-bind:style="{ color : textColor}"
      >Employer Contract</span>
      <span
        v-else-if="report.reportType === 'Technical'"
        v-bind:style="{ color : textColor}"
      >Technical Report</span>
      <span
        v-else-if="report.reportType === 'StudentEval'"
        v-bind:style="{ color : textColor}"
      >Student Evaluation</span>
      <span
        v-else-if="report.reportType === 'EmployerEval'"
        v-bind:style="{ color : textColor}"
      >Employer Evaluation</span>
      <span v-else v-bind:style="{ color : textColor}">Biweekly Report</span>
      <p>
      <p>
      <span>
        <b v-bind:style="{ color : textColor}">Report Status:&nbsp;</b>
      </span>
       <span class="badge badge-warning" v-if="report.reportStatus === 'Unsubmitted'">Unsubmitted</span>
      <span class="badge badge-primary" v-else-if="report.reportStatus === 'Submitted'">Submitted</span>
      <span class="badge badge-danger" v-else-if="report.reportStatus === 'Late'">Late</span>
      <span class="badge badge-success" v-else>Reviewed</span>
      </p>
      <span>
        <b v-bind:style="{ color : textColor}">Report Due Date:</b>
      </span>
      <span v-bind:style="{ color : textColor}">{{report.dueDate}}</span>
    </div>
    <div>
      <br>
    </div>
    <div class="card" id="edit" v-bind:style="{ backgroundColor : bgColor }">
      <h4>
        <b v-bind:style="{ color : textColor}">Modify Report Info</b>
      </h4>
      <table>
        <tr>
          <td style="padding:15px">
            <span>
              <b v-bind:style="{ color : textColor}">Set Report Type:</b>
            </span>
          </td>
          <td style="padding-right:15px">
            <select v-model="selectedType" class="custom-select filter-box" id="report-type">
              <option disabled value>Select a Report Type...</option>
              <option value="Contract">Employer Contract</option>
              <option value="Technical">Technical Report</option>
              <option value="StudentEval">Student Evaluation</option>
              <option value="EmployerEval">Employer Evaluation</option>
              <option value="TwoWeek">Biweekly Report</option>
            </select>
          </td>
          <td>
            <button
              id="report-type-button"
              class="btn btn-light btn-sm"
              v-on:click="setReportType"
            >Set</button>
          </td>
        </tr>
        <tr>
          <td style="padding:15px">
            <span>
              <b v-bind:style="{ color : textColor}">Set Report Status:</b>
            </span>
          </td>
          <td style="padding-right:15px">
            <select v-model="selectedStatus" class="custom-select filter-box" id="report-status">
              <option disabled value>Select a Report Status...</option>
              <option value="Unsubmitted">Unsubmitted</option>
              <option value="Submitted">Submitted</option>
              <option value="Late">Late</option>
              <option value="Reviewed">Reviewed</option>
            </select>
          </td>
          <td>
            <button
              id="report-status-button"
              class="btn btn-light btn-sm"
              v-on:click="setReportStatus"
            >Set</button>
          </td>
        </tr>
        <tr>
          <td style="padding:15px">
            <span style="color:white">
              <b v-bind:style="{ color : textColor}">Set Due Date:</b>
            </span>
          </td>
          <td>
            <input type="date" v-model="selectedDate" name="due-date" id="due">
            <button id="due-date-button" class="btn btn-light btn-sm" v-on:click="setDueDate">Set</button>
          </td>
        </tr>
      </table>
    </div>
    <div>
      <br>
    </div>
    <p id="title1" class="title"></p>
    <p id="title2" class="title"></p>
    <p id="title3" class="title"></p>
    <p id="title4" class="title"></p>
    <p id="title5" class="title"></p>
    <p id="title6" class="title"></p>
    <div v-if="report.reportType === 'Contract'">
      <div class="card" id="viewCon" v-bind:style="{ backgroundColor : bgColor }">
        <div id="t">
          <h5 v-bind:style="{ color : textColor }">
            <strong>Contract from:</strong>
          </h5>
          <h6 v-bind:style="{ color : textColor }">
            <strong>{{coop.employer.name}}</strong>
            at {{coop.employer.company}}
          </h6>
          <h6 v-bind:style="{ color : textColor }">{{coop.employer.email}}</h6>
          <h6 v-bind:style="{ color : textColor }">{{coop.employer.phone}}</h6>
          <h6 v-bind:style="{ color : textColor }">{{coop.address}}</h6>
          <br>
          <h5 v-bind:style="{ color : textColor }">
            <strong>Contract for:</strong>
          </h5>
          <h6 v-bind:style="{ color : textColor }">{{coop.student.name}}</h6>
          <h6 v-bind:style="{ color : textColor }">{{coop.student.email}}</h6>
          <h6 v-bind:style="{ color : textColor }">{{coop.student.id}}</h6>
          <h6 v-bind:style="{ color : textColor }">McGill University</h6>
          <br>
          <h5 v-bind:style="{ color : textColor }">
            <strong>Contract Information:</strong>
          </h5>
          <b-container fluid>
            <h6 v-bind:style="{ color : textColor }">
              <strong>Title:</strong>
              {{coop.title}} &nbsp;&nbsp;
              <input
                class="reportField"
                type="text"
                id="title"
                v-model="title"
                placeholder="Enter new title"
              >
            </h6>
            <h6 v-bind:style="{ color : textColor }">
              <strong>Start Date:</strong>
              {{coop.startDate}} &nbsp;&nbsp;
              <input
                class="reportField"
                type="text"
                id="start"
                v-model="start"
                placeholder="Enter new start date"
              >
            </h6>
            <h6 v-bind:style="{ color : textColor }">
              <strong>End Date:</strong>
              {{coop.endDate}} &nbsp;&nbsp;
              <input
                class="reportField"
                type="text"
                id="end"
                v-model="end"
                placeholder="Enter new end date"
              >
            </h6>
            <h6 v-bind:style="{ color : textColor }">
              <strong>Hourly Wage:</strong>
              {{coop.salaryPerHour}}$ &nbsp;&nbsp;
              <input
                class="reportField"
                type="text"
                id="pay"
                v-model="pay"
                placeholder="Enter new hourly wage"
              >
            </h6>
            <h6 v-bind:style="{ color : textColor }">
              <strong>Hours a Week:</strong>
              {{coop.hoursPerWeek}} &nbsp;&nbsp;
              <input
                class="reportField"
                type="text"
                id="hours"
                v-model="hours"
                placeholder="Enter new weekly hours"
              >
            </h6>
            <h6 v-bind:style="{ color : textColor }">
              <strong>Location:</strong>
              {{coop.address}} &nbsp;&nbsp;
              <input
                class="reportField"
                type="text"
                id="address"
                v-model="address"
                placeholder="Enter new address"
              >
            </h6>
          </b-container>
        </div>
      </div>
      <p>
        <br>
      </p>
      <button
        type="button"
        id="button"
        v-on:click="save(title, start, end, pay, hours)"
        class="btn btn-success btn-lg"
        v-b-tooltip.hover
        title="Click to save changes"
      >Save Changes</button>
    </div>

    <div v-else class="card" id="viewCon" v-bind:style="{ backgroundColor : bgColor}">
      <h4>
        <b v-bind:style="{ color : textColor}">View File</b>
      </h4>
      <br>
      <h5 style="color:lightblue">[Pretend that this is a pdf viewer or something]</h5>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Router from "../router";

var config = require("../../config");

// Axios config
var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var frontendUrl_dev = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  data() {
    return {
      selectedType: "",
      selectedStatus: "",
      selectedDate: "",
      report: {
        type: Object
      },
      coop: {
        type: Object
      },
      reportLoaded: false,
      hours: "",
      pay: "",
      title: "",
      start: "",
      end: "",
      address: "",
      bgColor: "",
      textColor: ""
    };
  },
  created: function() {
    var isLoggedIn = localStorage.getItem("isLoggedIn");
    // Send the user back to the login page if they are not logged in
    if (isLoggedIn === "false") {
      Router.push({
        path: "/login/",
        name: "LoginPage"
      });
    } else {
      // Fetch report from the backend
      this.fetchReport();

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
  methods: {
    getCoop: function() {
      var repId = this.report.id;
      AXIOS.get(`/coopFromReport/` + repId)
        .then(response => {
          this.coop = response.data;
        })
        .catch(e => {
          console.log(e.message);
        });
    },
    goToCoopPage: function() {
      // Go to the employer associated with this coop term
      Router.push({
        path: "/coop/",
        name: "CoopPage",
        params: {
          id: this.coop.id
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
    setReportType: function() {
      AXIOS.put(
        "/report/updateType?" +
          "id=" +
          this.report.id +
          "&type=" +
          this.selectedType
      )
        .then(response => {
          this.fetchReport();
        })
        .catch(e => {
          console.log(e.message);
        });
    },
    save: function() {
      var val = document.getElementById("title").value;
      var didSomething = false;
      if (val === undefined || val == null || val.length <= 0) {
        console.log("hello");
      } else {
        AXIOS.put("/coop/updateTitle?" + "id=" + this.coop.id + "&title=" + val)
          .then(response => {
            didSomething = true;
          })
          .catch(e => {
            console.log(e.message);
            document.getElementById("title1").innerText =
              "Title failed to update";
          });
      }
      val = document.getElementById("start").value;
      if (val === undefined || val == null || val.length <= 0) {
      } else {
        AXIOS.put("/coop/updateStart?" + "id=" + this.coop.id + "&date=" + val)
          .then(response => {
            didSomething = true;
          })
          .catch(e => {
            console.log(e.message);
            document.getElementById("title2").innerText =
              "Start date failed to update";
          });
      }
      val = document.getElementById("end").value;
      if (val === undefined || val == null || val.length <= 0) {
      } else {
        AXIOS.put("/coop/updateEnd?" + "id=" + this.coop.id + "&date=" + val)
          .then(response => {
            didSomething = true;
          })
          .catch(e => {
            console.log(e.message);
            document.getElementById("title3").innerText =
              "Start date failed to update";
          });
      }
      val = document.getElementById("pay").value;
      if (val === undefined || val == null || val.length <= 0) {
      } else {
        AXIOS.put("/coop/updatePay?" + "id=" + this.coop.id + "&pay=" + val)
          .then(response => {
            didSomething = true;
          })
          .catch(e => {
            console.log(e.message);
            document.getElementById("title4").innerText =
              "Pay failed to update";
          });
      }
      val = document.getElementById("hours").value;
      if (val === undefined || val == null || val.length <= 0) {
      } else {
        AXIOS.put("/coop/updateHours?" + "id=" + this.coop.id + "&hours=" + val)
          .then(response => {
            didSomething = true;
          })
          .catch(e => {
            console.log(e.message);
            document.getElementById("title5").innerText =
              "Hours failed to update";
          });
      }
      val = document.getElementById("address").value;
      if (val === undefined || val == null || val.length <= 0) {
      } else {
        AXIOS.put(
          "/coop/updateAddress?" + "id=" + this.coop.address + "&address=" + val
        )
          .then(response => {
            didSomething = true;
          })
          .catch(e => {
            console.log(e.message);
            document.getElementById("title6").innerText =
              "Address failed to update";
          });
      }
      if (didSomething === true) {
        /* idk probs do something maybe refresh page*/
      }
    },
    setReportStatus: function() {
      AXIOS.put(
        "/report/updateStatus?" +
          "id=" +
          this.report.id +
          "&status=" +
          this.selectedStatus
      )
        .then(response => {
          this.fetchReport();
        })
        .catch(e => {
          console.log(e.message);
        });
    },
    setDueDate: function() {
      var val = document.getElementById("due").value;
      AXIOS.put("/report/updateDate?" + "id=" + this.report.id + "&date=" + val)
        .then(response => {
          this.fetchReport();
        })
        .catch(e => {
          console.log(e.message);
        });
    },
    fetchReport: function() {
      var reportId = parseInt(Router.currentRoute.path.split("/")[2]);
      // Fetch coop from backend
      AXIOS.get(`/report/` + reportId)
        .then(response => {
          this.report = response.data;
          this.reportLoaded = true;
          this.getCoop();
        })
        .catch(e => {
          console.log(e.message);
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
  mounted() {
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style scoped>
.container {
  align-content: center;
}
#info {
  width: 70%;
  min-width: 550px;
  margin: auto;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
  color: white;
  display: inline-block;
}
#edit {
  width: 70%;
  min-width: 550px;
  margin: auto;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
  color: black;
  display: inline-block;
}
#view {
  width: 70%;
  min-width: 550px;
  margin: auto;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
  display: inline-block;
}

#viewCon {
  width: 70%;
  min-width: 550px;
  margin: auto;
  padding: 15px;
  text-align: left;
  background-color: white;
  display: inline-block;
  color: black;
}

.reportField {
  width: 28%;
  border-radius: 4px;
  border: 0px;
  margin: auto;
  margin-top: 10px;}

#button {
  width: 20%;
  color: white;
  margin-bottom: 15px;
  border: 0px;
}

#t {
  color: black;
}
.title {
  text-align: left;
  color: red;
  font-size: 15px;
  padding-left: 15px;
}
</style>