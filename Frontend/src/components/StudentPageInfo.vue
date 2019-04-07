<!--- This component renders the information of a specific employer for an employer page -->
<template>
  <div id="student-info-container" class="card" v-bind:style="{ backgroundColor: bgColor }">
    <h3 v-bind:style="{ color: textColor }">
      <span class="badge badge-primary">Student</span>
      &nbsp; &nbsp; {{ student.name }}
    </h3>
    <h3></h3>
    <p v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Email:</b>
      {{ student.email }}
    </p>
    <p v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">ID:</b>
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
  },
  created() {
    var darkModeOn = localStorage.getItem("DarkModeOn");
    if (darkModeOn === "true") {
      this.bgColor = "rgb(53, 58, 62)";
      this.textColor = "white";
    } else {
      this.bgColor = "rgb(248, 249, 251)";
      this.textColor = "black";
    }
  },
  data() {
    return {
      bgColor: "",
      textColor: ""
    };
  },
  mounted() {
    this.$root.$on("setDarkModeState", this.setDarkMode);
  },
  methods: {
    setDarkMode: function() {
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") {
        this.bgColor = "rgb(53, 58, 62)";
        this.textColor = "white";
      } else {
        this.bgColor = "rgb(248, 249, 251)";
        this.textColor = "black";
      }
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

#student-info-container {
  width: 65%;
  margin: auto;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}
</style>
