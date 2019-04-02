<template>
  <div class="container">
    <table class="Filter">
      <HomeFilters
      @updateCoopNumber="updateCoopNumber"
      @updateStartTerm="updateStartTerm"
      @updateEndTerm="updateEndTerm"
      />
    </table>
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
import BarChart from "./BarChart";
import PieChartCoop from "./PieChartCoop";
import PieChartReportStatus from "./PieChartReportStatus";
import PieChartReportType from "./PieChartReportType";
import axios from "axios";
import HomeFilters from "./HomeFilters.vue";


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
  props: {
  },
  created: function() {
    this.updateCharts();
  },
  components: {
    BarChart,
    PieChartCoop,
    PieChartReportStatus,
    PieChartReportType,
    HomeFilters
  },
  data() {
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
      reportstatsLoaded: false,
      selectedProfile: "",
      selectedStartTerm: "",
      selectedEndTerm: "",
      selectedCoopNumber: ""
    };
  },
  methods: {
    updateCharts: function() {
      AXIOS.get(
      `/statistics/coop/` +
        this.getStartTerm() +
        `/` +
        this.getEndTerm() +
        `/` +
        this.getCoopNumber()
      )
      .then(response => {
        this.coopstats = response.data;
        this.coopstatsLoaded = true;
      })
      .catch(e => {
        this.error = e;
      });
      AXIOS.get(
      `/statistics/report/` +
        this.getStartTerm() +
        `/` +
        this.getEndTerm() +
        `/` +
        this.getCoopNumber()
      )
      .then(response => {
        this.reportstats = response.data;
        this.reportstatsLoaded = true;
      })
      .catch(e => {
        this.error = e;
      });
    },
    updateCoopNumber: function(value) {
      this.coopstatsLoaded = false;
      this.reportstatsLoaded = false;
      this.selectedCoopNumber = value;
      this.updateCharts();
    },
    updateStartTerm: function(value) {
      this.coopstatsLoaded = false;
      this.reportstatsLoaded = false;
      this.selectedStartTerm = value;      
      this.updateCharts();
    },
    updateEndTerm: function(value) {
      this.coopstatsLoaded = false;
      this.reportstatsLoaded = false;
      this.selectedEndTerm = value;
      this.updateCharts();
    },
    increaseHeight() {
      this.height += 10;
    },
    getRandomInt() {
      return Math.floor(Math.random() * (50 - 5 + 1)) + 5;
    },
    getEndTerm() {
      if (!this.selectedEndTerm) {
        return "Winter2020";
      } else {
        return this.selectedEndTerm.text;
      }
    },
    getStartTerm() {
      if (!this.selectedStartTerm) {
        return "Fall2012";
      } else {
        return this.selectedStartTerm.text;
      }
    },
    getCoopNumber() {
      if (this.selectedCoopNumber == "None" || !this.selectedCoopNumber) {
        return 0;
      } else {
        return this.selectedCoopNumber;
      }
    }
  },
  computed: {
    myStyles() {
      return {
        height: `${this.height}px`,
        position: "relative"
      };
    }
  }
};
</script>

<style>
h1 {
  font-family: "Helvetica", Arial;
  color: #464646;
  text-transform: uppercase;
  border-bottom: 1px solid #f1f1f1;
  padding-bottom: 15px;
  font-size: 28px;
  margin-top: 0;
}

table.center {
  margin-left: auto;
  margin-right: auto;
}

.Chart h2 {
  margin-top: 0;
  padding: 15px 0;
  color: rgba(255, 255, 255, 0.5);
  border-bottom: 1px solid #323d54;
}

.container {
  max-width: 800px;
  margin: 5 auto;
  color: #b7d7e8;
}

.Chart {
  background: #212733;
  border-radius: 15px;
  box-shadow: 0 2px 15px rgba(25, 25, 25, 0.27);
  margin: 25px 0;
}

th,
td {
  width: 150px;
  text-align: center;
  padding: 20px;
}
</style>
