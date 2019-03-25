<! --- This component acts as a page to create a notification -->
<template>
    <div class="container">
        <div class="card" id="edit">
            <h4><b>Modify Report Info</b></h4>
            <span><b>Set Report Type</b></span>
            <select id="report-type">
                
            </select>
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

var selectedType = report.reportType;
var reportTypes = {}

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