<template>
  <div id="info-container" class="card">
    <h4>Employer Name:</h4>
    <p>{{ employer.name }}</p>
    <h6>Employer Email:</h6>
    <p>{{ employer.email }}</p>
    <h6>Employer ID:</h6>
    <p>{{ employer.id }}</p>
  </div>
</template>
<script>
export default {
  props: {
    employer: {
      type: Object,
      required: true
    }
  }
};
</script>
<style>
p,
h4,
h6 {
  color: white;
}
#info-container {
  width: 65%;
  margin: auto;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}
</style>
