<template>
  <div id="coop-container" class="card" @click="goToCoopPage">
    <span class="badge badge-info" v-if="coop.status === 'NotStarted'">Not Started</span>
    <span class="badge badge-warning" v-else-if="coop.status === 'InProgress'">In Progress</span>
    <span class="badge badge-success" v-else-if="coop.status === 'Completed'">Complete</span>
    <span class="badge badge-danger" v-else>Incomplete</span>
    <h5></h5>
    <h5><b>Student:</b> {{ coop.student.name }}</h5>
    <h6><b>Title:</b> {{ coop.title }}</h6>
    <p><b>Dates:</b> {{ coop.startDate }} - {{ coop.endDate }}</p>
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
          id: this.coop.id,
          coop: this.coop // Pass as a prop to the CoopPage
        }
      });
    }
  }
}
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
h4,
h5,
h6,
p {
  color: rgb(241, 240, 240);
}
</style>
