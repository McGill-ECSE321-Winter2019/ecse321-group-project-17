<template>
  <div id="filter-container" class="card">
    <div class="form-group row">
      <div class="col-md-3">
        <select
          v-model="selectedProfile"
          class="mr-sm-2 custom-select filter-box"
          @change="updateProfile"
        >
          <option disabled value>Profiles</option>
          <option>Students &amp; Employers</option>
          <option>Students</option>
          <option>Employers</option>
        </select>
      </div>
      <div class="col-md-3">
        <select
          v-model="selectedStartTerm"
          class="mr-sm-2 custom-select filter-box"
          @change="updateStartTerm"
        >
          <option disabled value>Start Term</option>
          <option>None</option>
          <option>Fall2012</option>
          <option>Summer2012</option>
          <option>Winter2012</option>
          <option>Fall2013</option>
          <option>Summer2013</option>
          <option>Winter2013</option>
          <option>Fall2014</option>
          <option>Summer2014</option>
          <option>Winter2014</option>
          <option>Fall2015</option>
          <option>Summer2015</option>
          <option>Winter2015</option>
          <option>Fall2016</option>
          <option>Summer2016</option>
          <option>Winter2016</option>
          <option>Fall2017</option>
          <option>Summer2017</option>
          <option>Winter2017</option>
          <option>Fall2018</option>
          <option>Summer2018</option>
          <option>Winter2018</option>
          <option>Fall2019</option>
          <option>Summer2019</option>
          <option>Winter2019</option>
          <option>Fall2020</option>
          <option>Summer2020</option>
          <option>Winter2020</option>
        </select>
      </div>
      <div class="col-md-3">
        <select
          v-model="selectedEndTerm"
          class="mr-sm-2 custom-select filter-box"
          @change="updateEndTerm"
        >
          <option disabled value>End Term</option>
          <option>None</option>
          <option>Fall2012</option>
          <option>Summer2012</option>
          <option>Winter2012</option>
          <option>Fall2013</option>
          <option>Summer2013</option>
          <option>Winter2013</option>
          <option>Fall2014</option>
          <option>Summer2014</option>
          <option>Winter2014</option>
          <option>Fall2015</option>
          <option>Summer2015</option>
          <option>Winter2015</option>
          <option>Fall2016</option>
          <option>Summer2016</option>
          <option>Winter2016</option>
          <option>Fall2017</option>
          <option>Summer2017</option>
          <option>Winter2017</option>
          <option>Fall2018</option>
          <option>Summer2018</option>
          <option>Winter2018</option>
          <option>Fall2019</option>
          <option>Summer2019</option>
          <option>Winter2019</option>
          <option>Fall2020</option>
          <option>Summer2020</option>
          <option>Winter2020</option>
        </select>
      </div>
      <div class="col-md-3">
        <select
          v-model="selectedCoopNumber"
          class="mr-sm-2 custom-select filter-box"
          @change="updateCoopNumber"
        >
          <option disabled value>Coop Number</option>
          <option>None</option>
          <option>1</option>
          <option>2</option>
          <option>3</option>
          <option>4</option>
          <option>5</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedProfile: "",
      selectedStartTerm: "",
      selectedEndTerm: "",
      selectedCoopNumber: ""
    };
  },
  methods: {
    updateProfile: function(event) {
      this.selectedProfile = event.target.value;
      this.$emit("updateProfile", this.selectedProfile);
    },
    updateStartTerm: function() {
      this.$emit("updateStartTerm", this.selectedStartTerm);
    },
    updateEndTerm: function() {
      this.$emit("updateEndTerm", this.selectedEndTerm);
    },
    updateCoopNumber: function() {
      this.$emit("updateCoopNumber", this.selectedCoopNumber);
    }
  }
};
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
  margin: auto;
  background-color: rgb(195, 201, 206);
  border-color: rgb(129, 133, 136);
}
</style>
