<template>
  <div class="container">
    <table class="center">
      <tr>
        <th>Selected Start Term: {{ this.getStartTerm() }}</th>
        <th>Selected End Term: {{ this.getEndTerm() }}</th>
        <th>Selected Coop Number: {{ this.getCoopNumber() }}</th>
        </tr>
    </table>
    <div class="Chart">
      <h2 style="text-align:center;">Profiles</h2>
      <bar-chart :chartData="this.profiles"/>
    </div>
    <div class="Chart" v-if="coopstatsLoaded">
      <h2 style="text-align:center;">Coop Statistics</h2>
      <pie-chart-coop :chartData="this.coopstats"/>
    </div>
    <div class="Chart" v-if="reportstatsLoaded">
      <h2 style="text-align:center;">Report Status Statistics</h2>
      <pie-chart-report-status :chartData="this.reportstats"/>
    </div>
    <div class="Chart" v-if="reportstatsLoaded">
      <h2 style="text-align:center;">Report Type Statistics</h2>
      <pie-chart-report-type :chartData="this.reportstats"/>
    </div>
  </div>
</template>

<script>
  import BarChart from './BarChart'
  import PieChartCoop from './PieChartCoop'
  import PieChartReportStatus from './PieChartReportStatus'
  import PieChartReportType from './PieChartReportType'
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
      students: {
        type: Array,
        required: true
      },
      employers: {
        type: Array,
        required: true
      },
      selectedProfile: "",
      selectedStartTerm: "",
      selectedEndTerm: "",
      selectedCoopNumber: ""
    },
    components: {
      BarChart,
      PieChartCoop,
      PieChartReportStatus,
      PieChartReportType
    },
    created: function() {
      AXIOS.get(`/statistics/coop/`+this.getStartTerm()+`/`+this.getEndTerm()+`/`+this.getCoopNumber())
        .then(response => {
            this.coopstats = response.data;
            this.coopstatsLoaded = true;
        })
        .catch(e => {
            this.error = e;
        });
      AXIOS.get(`/statistics/report/`+this.getStartTerm()+`/`+this.getEndTerm()+`/`+this.getCoopNumber())
        .then(response => {
        this.reportstats = response.data;
        this.reportstatsLoaded = true;
      })
      .catch(e => {
        this.error = e;
      });
    },
    data () {
      return {
        dataPoints: null,
        height: 20,
        coopstats: {
          type: Object
        },
        coopstatsLoaded: false,
        reportstats: {
          type: Object
        },
        reportstatsLoaded: false
      }
    },
    methods: {
      increaseHeight () {
        this.height += 10
      },
      getRandomInt () {
        return Math.floor(Math.random() * (50 - 5 + 1)) + 5
      },
      getEndTerm() {
        if (!this.selectedEndTerm) {
          return "Winter2020"
        } else {
          return this.selectedEndTerm
        }
      },
      getStartTerm() {
        if (!this.selectedStartTerm) {
          return "Fall2012"
        } else {
          return this.selectedStartTerm
        }
      },
      getCoopNumber () {
          if (this.selectedCoopNumber == 'None' || !this.selectedCoopNumber) {
            return 0 ;
          } else {
            return this.selectedCoopNumber;
          }
      }
    },
    computed: {
      myStyles () {
        return {
          height: `${this.height}px`,
          position: 'relative'
        }
      },
      profiles () {
        return {
            students: this.students,
            employers: this.employers
        } 
      }
    }
  }
</script>

<style>
  h1 {
    font-family: 'Helvetica', Arial;
    color: #464646;
    text-transform: uppercase;
    border-bottom: 1px solid #f1f1f1;
    padding-bottom: 15px;
    font-size: 28px;
    margin-top: 0;
  }

  table.center {
    margin-left:auto; 
    margin-right:auto;
  }

  .Chart h2 {
    margin-top:0;
    padding:15px 0;
    color:rgba(255,255,255,.5);
    border-bottom:1px solid #323d54
  }

  .container { 
      max-width:800px;
      margin:5 auto;
      color:#b7d7e8
  }

  .Chart { 
    background:#212733;
    border-radius:15px;
    box-shadow:0 2px 15px rgba(25,25,25,.27);
    margin:25px 0
  }
  
  th, td { 
    width:150px; 
    text-align:center; 
    padding:20px   
  } 

</style>
