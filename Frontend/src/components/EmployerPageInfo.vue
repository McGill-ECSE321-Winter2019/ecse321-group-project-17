<template>
  <div id="info-container" class="card">
    <h3><span class="badge badge-success" >Employer</span> &nbsp; &nbsp; <strong>{{ employer.name }} </strong><small>from</small>  {{ employer.company }}</h3>
    <h3></h3>
    <p><b>Email:</b> {{ employer.email }}</p>
    <p><b>Phone:</b> {{ employer.phone}}</p>
   
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
