<template>
  <div id="info-container" class="card" v-bind:style="{ backgroundColor: bgColor }">
    <h3 v-bind:style="{ color: textColor }">
      <span class="badge badge-success">Employer</span> &nbsp; &nbsp;
      <strong>{{ employer.name }}</strong>
      <small>from</small>
      {{ employer.company }}
    </h3>
    <h3></h3>
    <p v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Email:</b>
      {{ employer.email }}
    </p>
    <p v-bind:style="{ color: textColor }">
      <b v-bind:style="{ color: textColor }">Phone:</b>
      {{ employer.phone}}
    </p>
  </div>
</template>
<script>
export default {
  props: {
    employer: {
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
#info-container {
  width: 65%;
  margin: auto;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}
</style>
