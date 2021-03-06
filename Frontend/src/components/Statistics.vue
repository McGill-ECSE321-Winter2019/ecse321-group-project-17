<!--- This component shows statistics in charts-->
<template>
  <div class="container">
    <table class="Filter">
      <StatisticsFilters
        @updateCoopNumber="updateCoopNumber"
        @updateStartTerm="updateStartTerm"
        @updateEndTerm="updateEndTerm"
      />
    </table>
    <div class="Chart" v-if="coopstatsLoaded" v-bind:style="{ backgroundColor: bgColor }">
      <h2 style="text-align:center;" v-bind:style="{ color: textColor }">Coop Statistics</h2>
      <pie-chart-coop :chartData="this.coopstats"/>
    </div>
    <div class="Chart" v-if="reportstatsLoaded" v-bind:style="{ backgroundColor: bgColor }">
      <h2 style="text-align:center;" v-bind:style="{ color: textColor }">Report Status Statistics</h2>
      <pie-chart-report-status :chartData="this.reportstats"/>
    </div>
    <div class="Chart" v-if="reportstatsLoaded" v-bind:style="{ backgroundColor: bgColor }">
      <h2 style="text-align:center;" v-bind:style="{ color: textColor }">Report Type Statistics</h2>
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
import StatisticsFilters from "./StatisticsFilters.vue";

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
  props: {},
  created: function() {
    var isLoggedIn = localStorage.getItem("isLoggedIn");
    // Send the user back to the login page if they are not logged in
    if (isLoggedIn === "false") {
      Router.push({
        path: "/login/",
        name: "LoginPage"
      });
    } else {
      this.updateCharts();

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
  components: {
    BarChart,
    PieChartCoop,
    PieChartReportStatus,
    PieChartReportType,
    StatisticsFilters
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
      selectedCoopNumber: "",
      bgColor: "",
      textColor: ""
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
        return this.selectedEndTerm.value;
      }
    },
    getStartTerm() {
      if (!this.selectedStartTerm) {
        return "Fall2012";
      } else {
        return this.selectedStartTerm.value;
      }
    },
    getCoopNumber() {
      if (this.selectedCoopNumber == "None" || !this.selectedCoopNumber) {
        return 0;
      } else {
        return this.selectedCoopNumber;
      }
    },
    setDarkMode: function(darkModeOn) {
      if (darkModeOn) {
        this.bgColor = "rgb(53, 58, 62)";
        this.textColor = "white";
      } else {
        this.bgColor = "rgb(248, 249, 251)";
        this.textColor = "black";
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
  },
  mounted() {
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style scoped>
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
