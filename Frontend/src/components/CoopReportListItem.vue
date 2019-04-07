<template>
  <tr v-bind:style="{ backgroundColor : bgColor}">
    <td class="td-report-type">
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
    </td>
    <td class="td-report-status">
      <span style="color:orange" v-if="report.reportStatus === 'Unsubmitted'">Unsubmitted</span>
      <span style="color:lightblue" v-else-if="report.reportStatus === 'Submitted'">Submitted</span>
      <span style="color:red" v-else-if="report.reportStatus === 'Late'">Late</span>
      <span style="color:lightgreen" v-else>Reviewed</span>
    </td>
    <td class="td-due-date">
      <span v-bind:style="{ color : textColor}">{{ report.dueDate }}</span>
    </td>
    <td class="td-view" v-b-tooltip.hover title="Click to see this view or edit this report">
      <button id="view-button" class="btn btn-light btn-sm" v-on:click="goToReportPage">View/Edit</button>
    </td>
    <td class="td-remove" v-b-tooltip.hover title="Click to remove this report">
      <button id="remove-button" class="btn btn-light btn-sm" v-on:click="removeReport">Remove</button>
    </td>
  </tr>
</template>

<script>
import Router from "../router";
import ReportPage from "./ReportPage.vue";
import CoopPage from "./CoopPage.vue";

export default {
  props: {
    report: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      bgColor: "",
      textColor: ""
    };
  },
  created() {
    // Fetches the user's selected UI mode from browser local storage
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
      this.$emit("remove-report", this.report);
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
