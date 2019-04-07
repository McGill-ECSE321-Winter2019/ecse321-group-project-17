<!--- This component renders the information of a coop for the list on the employer page -->
<template>
  <div
    id="coop-container"
    class="card"
    @click="goToCoopPage"
    v-bind:style="{ backgroundColor: bgColor }"
    @mouseover="hoverOn"
    @mouseleave="hoverOff"
  >
    <span class="badge badge-info" v-if="coop.status === 'NotStarted'">Not Started</span>
    <span class="badge badge-warning" v-else-if="coop.status === 'InProgress'">In Progress</span>
    <span class="badge badge-success" v-else-if="coop.status === 'Completed'">Complete</span>
    <span class="badge badge-danger" v-else>Incomplete</span>
    <h5></h5>
    <h5 v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Student:</b>
      {{ coop.student.name }}
    </h5>
    <h6 v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Title:</b>
      {{ coop.title }}
    </h6>
    <p v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Dates:</b>
      {{ coop.startDate }} to {{ coop.endDate }}
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
    goToStudentPage: function() {
      let email = this.student.email;
      getStudent(this.student.email).then(function(res) {
        // Go to the student's page
        Router.push({
          path: "/student/",
          name: "StudentPage",
          params: {
            urlEmail: res.email,
            studentEmail: email // Pass as prop to the StudentPage that will be rendered
          }
        });
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
    },
    hoverOn: function() {
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") {
        this.bgColor = "rgb(96, 101, 105)";
      } else {
        this.bgColor = "rgb(224, 224, 224)";
      }
    },
    hoverOff: function() {
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") {
        this.bgColor = "rgb(53, 58, 62)";
      } else {
        this.bgColor = "rgb(248, 249, 251)";
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
  color: rgb(241, 240, 240);
}
</style>
