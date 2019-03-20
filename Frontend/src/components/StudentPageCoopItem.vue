<template>
  <div id="info-container" class="card">
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
#info-container {
  width: 65%;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
  background-color: rgb(240, 240, 240);
}
</style>
