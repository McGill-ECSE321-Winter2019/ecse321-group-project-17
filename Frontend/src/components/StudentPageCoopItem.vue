<template>
  <div id="coop-container" class="card">
    <span class="badge badge-info" v-if="coop.status === 'NotStarted'">Not Started</span>
    <span class="badge badge-warning" v-else-if="coop.status === 'InProgress'">In Progress</span>
    <span class="badge badge-success" v-else-if="coop.status === 'Complete'">Complete</span>
    <span class="badge badge-danger" v-else>Incomplete</span>
    <h5> </h5>
    <h5>Coop Title: {{ coop.title }}</h5>
    <h6>Company: {{ coop.employer.name }}</h6>
    <p>Start Date: {{ coop.startDate }}, End Date: {{ coop.endDate }}</p>
  </div>
</template>

<script>

export default {
  props: {
    coop: {
      type: Object,
      required: true
    }
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
h4,
h5,
h6,
p {
  color: white;
}
</style>
