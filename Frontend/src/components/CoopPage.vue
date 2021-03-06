<!--- This component acts as a page for a specific coop --->
<template>
  <div class="container">
    <CoopInfo :coop="coop"/>
    <div id="report-table" class="card" v-bind:style="{ backgroundColor: bgColor }">
      <table v-cloak>
        <tr id="tr-heading" v-bind:style="{ backgroundColor: bgColor }">
          <th class="td-report-type">
            <h5 v-bind:style="{ color: textColor }">Report Type</h5>
          </th>
          <th class="td-report-status">
            <h5 v-bind:style="{ color: textColor }">Report Status</h5>
          </th>
          <th class="td-due-date">
            <h5 v-bind:style="{ color: textColor }">Due Date</h5>
          </th>
          <th class="td-view"/>
          <th class="td-remove"/>
        </tr>
        <CoopReportListItem
          v-for="report in this.orderedReports"
          :key="report.id"
          :report="report"
          @remove-report="removeReport"
        />
      </table>
      <h4 v-if="!hasReports(coop)" id="no-reports" v-bind:style="{ color: textColor }">
        <br>This coop has no reports
      </h4>
    </div>
  </div>
</template>

<script>
import CoopInfo from "./CoopInfo.vue";
import CoopReportListItem from "./CoopReportListItem.vue";
import Router from "../router";
import axios from "axios";
import _ from "lodash";

var config = require("../../config");

var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

// Axios config
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  components: {
    CoopInfo,
    CoopReportListItem
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
      // Fetch the coop term from the backend
      this.fetchCoop();

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
      coop: {
        type: Object
      },
      orderedReports: {
        type: Array
      },
      bgColor: "",
      textColor: ""
    };
  },
  methods: {
    // Checks if the coop term has any reports
    hasReports: function(coop) {
      if (coop.reports != null && coop.reports.length > 0) {
        return true;
      }
      return false;
    },
    fetchCoop: function() {
      // Get coop ID from current URL
      var coopId = parseInt(Router.currentRoute.path.split("/")[2]);

      // Fetch coop from backend
      AXIOS.get(`/coop/` + coopId)
        .then(response => {
          this.coop = response.data;
          this.orderedReports = _.sortBy(this.coop.reports, "dueDate");
        })
        .catch(e => {
          console.log(e.message);
        });
    },
    // Removes a report from the coop
    removeReport: function(report) {
      var index = this.orderedReports.indexOf(report);
      if (index > -1) {
        this.orderedReports.splice(index, 1);
      }

      // Remove report in backend
      AXIOS.delete("/report/delete?id=" + report.id)
        .then(response => {
          this.fetchCoop();
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
    // Listens to the setDarkModeState event emitted from the LogoBar component
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style>
.container {
  align-items: center;
}
#report-table {
  width: 65%;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  background-color: rgb(53, 58, 62);
}
.td-report-type {
  color: white;
  width: 20%;
  text-align: left;
  padding-left: 15px;
}
.td-report-status {
  color: white;
  width: 20%;
  text-align: left;
  padding-left: 15px;
}
.td-due-date {
  color: white;
  width: 20%;
  text-align: left;
  padding-left: 15px;
}
.td-view {
  width: 20%;
  text-align: center;
}
.td-remove {
  width: 20%;
  text-align: center;
}
#no-reports {
  text-align: center;
}
</style>