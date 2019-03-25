<! --- This component acts as a page to create a notification -->
<template>
    <div class="container">
        <div class="card" id="edit">
            <h4><b>Modify Report Info</b></h4>
            <span style="color:white"><b>Set Report Type:</b></span>
            <select id="report-type">
                <option value="Contract">Employer Contract</option>
                <option value="Technical">Technical Report</option>
                <option value="StudentEval">Student Evaluation</option>
                <option value="EmployerEval">Employer Evaluation</option>
                <option value="TwoWeek">Biweekly Report</option>
            </select>
            <button
            id="report-type-button"
            v-on:click="setReportType">
            Set
            </button>
            <br/>
            <span style="color:white"><b>Set Report Status:</b></span>
            <select id="report-status">
                <option value="Unsubmitted">Unsubmitted</option>
                <option value="Submitted">Submitted</option>
                <option value="Late">Late</option>
                <option value="Reviewed">Reviewed</option>
            </select>
            <button
            id="report-status-button"
            v-on:click="setReportStatus">
            Set
            </button>
            <br/>
            <span style="color:white"><b>Set Due Date:</b></span>
            <input type="date" name="due-date">
            <button
            id="due-date-button"
            v-on:click="setDueDate">
            Set
            </button>
        </div>
        <div class="card" id="view">
            <h1>Pretend there's a file here</h1>
        </div>
    </div>
</template>

<script>
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
    }
};
</script>

<style>
.container {
    align-items: center;
}
</style>