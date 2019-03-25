<template>
    <tr>
        <td class="td-report-type">
            <span v-if="report.type === 'Contract'">Employer Contract</span>
            <span v-else-if="report.type === 'Technical'">Technical Report</span>
            <span v-else-if="report.type === 'StudentEval'">Student Evaluation</span>
            <span v-else-if="report.type === 'EmployerEval'">Employer Evaluation</span>
            <span v-else>Biweekly Report</span>
        </td>
        <td class="td-report-status">
            <span style="color:yellow" v-if="report.status === 'Unsubmitted'">Unsubmitted</span>
            <span style="color:lightblue" v-else-if="report.status === 'Submitted'">Submitted</span>
            <span style="color:red" v-else-if="report.status === 'Late'">Late</span>
            <span style="color:lightgreen" v-else>Reviewed</span>
        </td>
        <td class="td-due-date">
            <span>{{ report.dueDate }}</span>
        </td>
        <td class="td-view">
            <button
            id="view-button"
            v-on:click="goToReportPage">
            View/Edit
            </button>
        </td>
        <td class="td-remove">
            <button
            id="remove-button"
            v-on:click="deleteReport">
            Remove
            </button>
        </td>
    </tr>
</template>

<script>
import Router from "../router";
import ReportPage from "./ReportPage.vue";

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
                    report: this.report
                }
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
