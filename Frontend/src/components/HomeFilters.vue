<template>
  <div id="filter-container" class="card">
    <p>List Filters:</p>
    <div class="form-group row">
      <div class="col-md-4">
        <select
          v-model="selectedProfile"
          class="mr-sm-2 custom-select filter-box"
          @change="updateProfile"
        >
          <option>Students &amp; Employers</option>
          <option>Students</option>
          <option>Employers</option>
        </select>
      </div>
      <!-- <div class="col-md-4">
        <select v-model="selectedProblematicStatus" class="mr-sm-2 custom-select filter-box">
          <option disabled value>Problematic/Not Problematic</option>
          <option>All Profiles</option>
          <option>Problematic</option>
          <option>Not Problematic</option>
        </select>
      </div>
      <div class="col-md-4">
        <select v-model="selectedCoopStatus" class="mr-sm-2 custom-select filter-box">
          <option disabled value>Coop Status</option>
          <option>All Coops</option>
          <option>NotStarted</option>
          <option>InProgress</option>
          <option>Incomplete</option>
          <option>Complete</option>
        </select>
      </div>-->
    </div>
    <p>Statistics Filters:</p>
    <div class="form-group row">
      <div class="col-md-4">
        <select
          v-model="selectedStartTerm"
          class="mr-sm-2 custom-select filter-box"
          @change="updateStartTerm"
        >
          <option disabled value>Start Term</option>
          <option
            v-show="startTerm.number<=selectedEndTerm.number || !selectedEndTerm"
            v-for="startTerm in terms"
            v-bind:value="startTerm"
            :key="startTerm.value"
            :term="startTerm"
          >{{ startTerm.text }}</option>
        </select>
      </div>
      <div class="col-md-4">
        <select
          v-model="selectedEndTerm"
          class="mr-sm-2 custom-select filter-box"
          @change="updateEndTerm"
        >
          <option disabled value>End Term</option>
          <option
            v-show="endTerm.number>=selectedStartTerm.number || !selectedStartTerm"
            v-for="endTerm in terms"
            v-bind:value="endTerm"
            :key="endTerm.value"
            :term="endTerm"
          >{{ endTerm.text }}</option>
        </select>
      </div>
      <div class="col-md-4">
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
      selectedProfile: "Students & Employers",
      selectedProblematicStatus: "",
      selectedCoopStatus: "",
      selectedStartTerm: "",
      selectedEndTerm: "",
      selectedCoopNumber: "",
      terms: [
        { text: "None", value: "None", number: 0 },
        { text: "Fall 2012", value: "Fall2012", number: 1 },
        { text: "Summer 2012", value: "Summer2012", number: 2 },
        { text: "Winter 2012", value: "Winter2012", number: 3 },
        { text: "Fall 2013", value: "Fall2013", number: 4 },
        { text: "Summer 2013", value: "Summer2013", number: 5 },
        { text: "Winter 2013", value: "Winter2013", number: 6 },
        { text: "Fall 2014", value: "Fall2014", number: 7 },
        { text: "Summer 2014", value: "Summer2014", number: 8 },
        { text: "Winter 2014", value: "Winter2014", number: 9 },
        { text: "Fall 2015", value: "Fall2015", number: 10 },
        { text: "Summer 2015", value: "Summer2015", number: 11 },
        { text: "Winter 2015", value: "Winter2015", number: 12 },
        { text: "Fall 2016", value: "Fall2016", number: 13 },
        { text: "Summer 2016", value: "Summer2016", number: 14 },
        { text: "Winter 2016", value: "Winter2016", number: 15 },
        { text: "Fall 2017", value: "Fall2017", number: 16 },
        { text: "Summer 2017", value: "Summer2017", number: 17 },
        { text: "Winter 2017", value: "Winter2017", number: 18 },
        { text: "Fall 2018", value: "Fall2018", number: 19 },
        { text: "Summer 2018", value: "Summer2018", number: 20 },
        { text: "Winter 2018", value: "Winter2018", number: 21 },
        { text: "Fall 2019", value: "Fall2019", number: 22 },
        { text: "Summer 2019", value: "Summer2019", number: 23 },
        { text: "Winter 2019", value: "Winter2019", number: 24 },
        { text: "Fall 2020", value: "Fall2020", number: 25 },
        { text: "Summer 2020", value: "Summer2020", number: 26 },
        { text: "Winter 2020", value: "Winter2020", number: 27 }
      ]
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
