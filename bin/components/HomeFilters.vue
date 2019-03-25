<template>
  <div id="filter-container" class="card">
    <div class="form-group row">
      <div class="col-md-3">
        <select class="mr-sm-2 custom-select filter-box">
          <option selected value="1">Students &amp; Employers</option>
          <option value="2">Students</option>
          <option value="3">Employers</option>
        </select>
      </div>
      <div class="col-md-3">
        <select class="mr-sm-2 custom-select filter-box">
          <option selected value="1">Filter 1</option>
          <option value="2">Filter 1 Option</option>
        </select>
      </div>
      <div class="col-md-3">
        <select class="mr-sm-2 custom-select filter-box">
          <option selected value="1">Filter 2</option>
          <option value="2">Filter 2 Option</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
#filter-container {
  width: 70%;
  min-width: 550px;
  margin: auto;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}

.filter-box {
  margin: 10px;
  background-color: rgb(195, 201, 206);
  border-color: rgb(129, 133, 136);
}
</style>
