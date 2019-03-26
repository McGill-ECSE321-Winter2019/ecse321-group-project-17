<template>
  <div id="info-container" class="card">
    <h3>
      <span class="badge badge-primary">Student</span>
      &nbsp; &nbsp; {{ student.name }}
    </h3>
    <h3></h3>
    <p>
      <b>Email:</b>
      {{ student.email }}
    </p>
    <p>
      <b>ID:</b>
      {{ student.id }}
    </p>
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
h3,
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
