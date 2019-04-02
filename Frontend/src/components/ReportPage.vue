<! --- This component acts as a page to create a notification -->
<template>
  <div v-if="this.reportLoaded" class="container">
    <div class="card" id="info" v-bind:style="{ backgroundColor : bgColor}">
      <h4>
        <b v-bind:style="{ color : textColor}">Report Information</b>
      </h4>
      <br style="display:block;margin:10px0;">
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
      <br>
      <span>
        <b v-bind:style="{ color : textColor}">Report Status:</b>
      </span>
      <span style="color:orange" v-if="report.reportStatus === 'Unsubmitted'">Unsubmitted</span>
      <span style="color:lightblue" v-else-if="report.reportStatus === 'Submitted'">Submitted</span>
      <span style="color:red" v-else-if="report.reportStatus === 'Late'">Late</span>
      <span style="color:lightgreen" v-else>Reviewed</span>
      <br>
      <span>
        <b v-bind:style="{ color : textColor}">Report Due Date:</b>
      </span>
      <span v-bind:style="{ color : textColor}">{{report.dueDate}}</span>
    </div>
    <div>
      <br>
    </div>
    <div class="card" id="edit" v-bind:style="{ backgroundColor : bgColor}">
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
            <input type="date" v-model="selectedDate" name="due-date">
            <button id="due-date-button" class="btn btn-light btn-sm" v-on:click="setDueDate">Set</button>
          </td>
        </tr>
      </table>
    </div>
    <div>
      <br>
    </div>
    <div class="card" id="view" v-bind:style="{ backgroundColor : bgColor}">
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
      reportLoaded: false,
      bgColor: "",
      textColor: ""
    };
  },
  created: function() {
    this.fetchReport();

    var darkModeOn = localStorage.getItem("DarkModeOn");
    if (darkModeOn === "true") {
      this.bgColor = "rgb(53, 58, 62)";
      this.textColor = "white";
    } else {
      this.bgColor = "rgb(248, 249, 251)";
      this.textColor = "black";
    }
  },
  methods: {
    setReportType: function() {
      alert("This function has not been implemented yet.");
    },
    setReportStatus: function() {
      AXIOS.put(
        "/report/update?" +
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
      alert("This function has not been implemented yet.");
    },
    fetchReport: function() {
      var reportId = parseInt(Router.currentRoute.path.split("/")[2]);
      // Fetch coop from backend
      AXIOS.get(`/report/` + reportId)
        .then(response => {
          this.report = response.data;
          this.reportLoaded = true;
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

<style>
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
b {
  color: white;
}
</style>