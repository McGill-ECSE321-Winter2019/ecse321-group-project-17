<template>
  <div id="info-container" class="card">
    <h3>
      <span class="badge badge-warning">Coop</span> &nbsp; &nbsp;
      <strong style="color:white">Coop Information</strong>
    </h3>
    <br>
    <span style="color:white">
      <b>Status:</b>
    </span>
    <span style="color:lightblue" v-if="coop.status === 'NotStarted'">Not Started</span>
    <span style="color:yellow" v-else-if="coop.status === 'InProgress'">In Progress</span>
    <span style="color:lightgreen" v-else-if="coop.status === 'Completed'">Complete</span>
    <span style="color:red" v-else>Incomplete</span>
    <p/>
    <p>
      <b>Employer:</b>
      {{ coop.employer.name }}
    </p>
    <p>
      <b>Student:</b>
      {{ coop.student.name }}
    </p>
    <p>
      <b>Salary per hour:</b>
      {{ coop.salaryPerHour }}
    </p>
    <p>
      <b>Hours per week:</b>
      {{ coop.hoursPerWeek }}
    </p>
    <p>
      <b>Title:</b>
      {{ coop.title }}
    </p>
    <p>
      <b>Dates:</b>
      {{ coop.startDate }} to {{ coop.endDate }}
    </p>
    <p>
      <b>Location:</b>
      {{ coop.address }}
    </p>
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
  background-color: rgb(53, 58, 62);
  display: inline-block;
}
h4,
p {
  color: rgb(241, 240, 240);
}
</style>