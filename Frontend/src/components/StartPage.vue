<template>
  <div>
    <h4 v-bind:style="{ color: textColor }">Access Cooperator as:</h4>
    <button
      type="button"
      v-bind:class="buttonClass"
      onclick="location.href='http://127.0.0.1:8089/#/'"
    >Student</button>
    <button
      type="button"
      v-bind:class="buttonClass"
      onclick="location.href='http://localhost:8080/ecse321/login.php/'"
    >Employer</button>
    <button
      type="button"
      v-bind:class="buttonClass"
      onclick="location.href='http://127.0.0.1:8087/#/login/'"
    >Co-op Administrator</button>
    <button
      type="button"
      v-bind:class="buttonClass"
      onclick="location.href='http://127.0.0.1:8087/#/'"
    >Academic Manager</button>
  </div>
</template>
<script>
export default {
  data() {
    return {
      textColor: "",
      buttonClass: ""
    };
  },
  created: function() {
    var darkModeOn = localStorage.getItem("DarkModeOn");
    if (darkModeOn === "true") {
      this.textColor = "white";
      this.buttonClass = "btn btn-dark btn-lg start-button";
    } else {
      this.textColor = "black";
      this.buttonClass = "btn btn-light btn-lg start-button";
    }
  },
  methods: {
    setDarkMode: function() {
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") {
        this.textColor = "white";
        this.buttonClass = "btn btn-dark btn-lg start-button";
      } else {
        this.textColor = "black";
        this.buttonClass = "btn btn-light btn-lg start-button";
      }
    }
  },
  mounted() {
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style scoped>
.start-button {
  margin: 10px;
}
</style>
