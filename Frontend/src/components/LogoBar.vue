<template>
  <nav v-bind:class="navBarClass" id="container">
    <a
      class="navbar-brand mb-0 h1"
      v-b-tooltip.hover
      title="Get back to the Front Page!"
      v-bind:style="{color: titleColor}"
      @click="goToHomePage()"
    >Cooperator</a>
    <span style="float:left;">
      <button type="button" v-bind:class="buttonClass" @click="logOut">Log Out</button>
      <button type="button" v-bind:class="buttonClass" @click="toggleDarkLight">{{ buttonText }}</button>
    </span>
  </nav>
  <!-- <nav class="navbar navbar-dark bg-light" id="container">
      <a class="navbar-brand mb-0 h1" href="/" style="color: black">Cooperator</a>
      <button type="button" class="btn btn-light" @click="toggleDarkLight">Light Mode</button>
  </nav>-->
</template>

<script>
import Router from "../router";

export default {
  data() {
    return {
      navBarClass: "",
      buttonClass: "",
      buttonText: "",
      titleColor: ""
    };
  },
  created() {
    var darkModeOn = localStorage.getItem("DarkModeOn");
    if (darkModeOn === "true") {
      this.navBarClass = "navbar navbar-dark bg-dark";
      this.buttonClass = "btn btn-dark";
      this.buttonText = "🌙";
      this.titleColor = "white";
    } else {
      this.navBarClass = "navbar navbar-dark bg-light";
      this.buttonClass = "btn btn-light";
      this.buttonText = "☀️";
      this.titleColor = "black";
    }
  },
  methods: {
    goToHomePage: function() {
      Router.push({
        path: "/home/",
        name: "Home"
      });
    },
    logOut: function() {
      Router.push({
        path: "/login/",
        name: "LoginPage"
      });
    },
    toggleDarkLight: function() {
      // Local storage only stores strings
      var darkModeOn = localStorage.getItem("DarkModeOn");

      if (darkModeOn === "true") {
        localStorage.setItem("DarkModeOn", "false");
        darkModeOn = "false";
      } else {
        localStorage.setItem("DarkModeOn", "true");
        darkModeOn = "true";
      }

      var darkModeOnBool = false;
      if (darkModeOn === "true") darkModeOnBool = true;
      this.$emit("setDarkModeState", darkModeOnBool); // Emit to parent

      if (darkModeOnBool == true) {
        this.navBarClass = "navbar navbar-dark bg-dark";
        this.buttonClass = "btn btn-dark";
        this.buttonText = "🌙";
        this.titleColor = "white";
      } else {
        this.navBarClass = "navbar navbar-dark bg-light";
        this.buttonClass = "btn btn-light";
        this.buttonText = "☀️";
        this.titleColor = "black";
      }
    }
  }
};
</script>

<!-- The scoped attribute limits the CSS to this file only --->
<style scoped>
#container {
  margin-bottom: 50px;
  /* font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
    "Lucida Sans", Arial, sans-serif; */
}
</style>