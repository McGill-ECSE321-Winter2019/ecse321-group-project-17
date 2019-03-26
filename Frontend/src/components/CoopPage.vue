<!--- This component acts as a page for a specific coop --->
<template>
  <div class="container">
    <CoopInfo :coop="coop"/>
    <div id="report-table" class="card">
      <table v-cloak>
        <tr id="tr-heading">
          <th class="td-report-type">
            <h5>Report Type</h5>
          </th>
          <th class="td-report-status">
            <h5>Report Status</h5>
          </th>
          <th class="td-due-date">
            <h5>Due Date</h5>
          </th>
          <th class="td-view"/>
          <th class="td-remove"/>
        </tr>
        <CoopReportListItem
          v-for="report in this.orderedReports"
          :key="report.id"
          :report="report"
        />
      </table>
      <h4 v-if="!hasReports(coop)" id="no-reports">
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

// Axios config
var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  components: {
    CoopInfo,
    CoopReportListItem
  },
  created: function() {
    this.fetchCoop();
  },
  data() {
    return {
      coop: {
        type: Object
      },
      orderedReports: {
        type: Array
      }
    };
  },
  methods: {
    hasReports: function(coop) {
      if (coop.reports != null && coop.reports.length > 0) {
        return true;
      }
      return false;
    },
    fetchCoop: function() {
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
    }
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