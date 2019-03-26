<template>
  <tr>
    <td class="td-report-type">
      <span v-if="report.reportType === 'Contract'">Employer Contract</span>
      <span v-else-if="report.reportType === 'Technical'">Technical Report</span>
      <span v-else-if="report.reportType === 'StudentEval'">Student Evaluation</span>
      <span v-else-if="report.reportType === 'EmployerEval'">Employer Evaluation</span>
      <span v-else>Biweekly Report</span>
    </td>
    <td class="td-report-status">
      <span style="color:yellow" v-if="report.reportStatus === 'Unsubmitted'">Unsubmitted</span>
      <span style="color:lightblue" v-else-if="report.reportStatus === 'Submitted'">Submitted</span>
      <span style="color:red" v-else-if="report.reportStatus === 'Late'">Late</span>
      <span style="color:lightgreen" v-else>Reviewed</span>
    </td>
    <td class="td-due-date">
      <span>{{ report.dueDate }}</span>
    </td>
    <td class="td-view">
      <button id="view-button" class="btn btn-light btn-sm" v-on:click="goToReportPage">View/Edit</button>
    </td>
    <td class="td-remove">
      <button id="remove-button" class="btn btn-light btn-sm" v-on:click="removeReport">Remove</button>
    </td>
  </tr>
</template>

<script>
import Router from "../router";
import ReportPage from "./ReportPage.vue";
import CoopPage from "./CoopPage.vue";

import axios from "axios";

var config = require("../../config");

var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  props: {
    report: {
      type: Object,
      required: true
    }
  },
  methods: {
    goToReportPage: function() {
      Router.push({
        path: "/report/",
        name: "ReportPage",
        params: {
          id: this.report.id
        }
      });
    },
    removeReport: function() {
      AXIOS.delete("/report/delete?id=" + this.report.id)
        .then(response => {
          CoopPage.methods.fetchCoop();
        })
        .catch(e => {
          console.log(e.message);
        });
    }
  }
};
</script>

<style>
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
</style>
