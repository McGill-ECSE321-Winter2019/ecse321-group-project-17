<template>
  <div
    id="coop-container"
    class="card"
    @click="goToCoopPage"
    v-bind:style="{ backgroundColor: bgColor }"
  >
    <span class="badge badge-info" v-if="coop.status === 'NotStarted'">Not Started</span>
    <span class="badge badge-warning" v-else-if="coop.status === 'InProgress'">In Progress</span>
    <span class="badge badge-success" v-else-if="coop.status === 'Completed'">Complete</span>
    <span class="badge badge-danger" v-else>Incomplete</span>
    <h5></h5>
    <h5 v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Coop Title:</b>
      {{ coop.title }}
    </h5>
    <h6 v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Company:</b>
      {{ coop.employer.company }}
    </h6>
    <p v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Date:</b>
      {{ coop.startDate }}, End Date: {{ coop.endDate }}
    </p>
  </div>
</template>

<script>
import Router from "../router";

export default {
  props: {
    coop: {
      type: Object,
      required: true
    }
  },
  methods: {
    goToCoopPage: function() {
      // Go to the coop page
      Router.push({
        path: "/coop/",
        name: "CoopPage",
        params: {
          id: this.coop.id
        }
      });
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
  created() {
    var darkModeOn = localStorage.getItem("DarkModeOn");
    if (darkModeOn === "true") {
      this.bgColor = "rgb(53, 58, 62)";
      this.textColor = "white";
    } else {
      this.bgColor = "rgb(248, 249, 251)";
      this.textColor = "black";
    }
  },
  data() {
    return {
      bgColor: "",
      textColor: ""
    };
  },
  mounted() {
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style>
#coop-container {
  width: 65%;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}

#coop-container:hover {
  background-color: rgb(66, 71, 75);
}

h4,
h5,
h6,
p {
  color: white;
}
</style>
