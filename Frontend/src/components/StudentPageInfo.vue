<template>
  <div id="info-container" class="card">
    <h4>Student Name:</h4>
    <p>{{ student.name }}</p>
    <h6>Student Email:</h6>
    <p>{{ student.email }}</p>
    <h6>Student ID:</h6>
    <p>{{ student.id }}</p>
  </div>
</template>
<script>
export default {
  props: {
    student: {
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
