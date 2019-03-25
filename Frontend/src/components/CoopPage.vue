<!--- This component acts as a page for a specific coop --->
<template>
    <div class="container">
        <CoopInfo :coop="coop"/>
        <div id="report-table" class="card">
            <table>
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
                    v-for="report in coop.reports"
                    :key="report.id"
                    :report="report"
                    :coopId="coop.id"
                />
            </table>
            <h4 v-if="!hasReports(coop)" id="no-reports"><br/>This coop has no reports</h4>
        </div>
    </div>
</template>

<script>
import CoopInfo from "./CoopInfo.vue"
import CoopReportListItem from "./CoopReportListItem.vue"
import Router from "../router";
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
    components: {
        CoopInfo,
        CoopReportListItem
    },
    created: function() {
        var coopId = parseInt(Router.currentRoute.path.split("/")[2]);
        // Fetch coop from backend
        AXIOS.get(`/coop/` + coopId)
        .then(response => {
            // JSON responses are automatically parsed.
            this.coop = response.data;
        })  
        .catch(e => {
            console.log(e.message);
        });
    },
    data() {
        return {
            coop: {
                type: Object
            }
        }
    },
    methods: {
        hasReports: function(coop) {
            if(coop.reports != null && coop.reports.length > 0) {
                return true;
            }
            return false;
        }
    }
}
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