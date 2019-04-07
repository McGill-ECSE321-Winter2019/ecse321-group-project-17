<!--- This component is the nav bar that appears on every page -->
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
      <button type="button" v-bind:class="buttonClass" @click="logOut" v-show="isLoggedIn">Log Out</button>
      <button type="button" v-bind:class="buttonClass" @click="toggleDarkLight">{{ buttonText }}</button>
    </span>
  </nav>
</template>

<script>
import Router from "../router";

export default {
  data() {
    return {
      navBarClass: "",
      buttonClass: "",
      buttonText: "",
      titleColor: "",
      isLoggedIn: false
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
    var isLoggedIn = localStorage.getItem("isLoggedIn");
    if (isLoggedIn === "true") this.isLoggedIn = true;
    else this.isLoggedIn = false;
  },
  methods: {
    goToHomePage: function() {
      var isLoggedIn = localStorage.getItem("isLoggedIn");
      if (isLoggedIn === "true") {
        // User is logged in, allow access to Home page
        Router.push({
          path: "/home/",
          name: "Home"
        });
      } else {
        // Do not allow user to go to the Home page if they are not logged in
        Router.push({
          path: "/login/",
          name: "LoginPage"
        });
      }
    },
    logOut: function() {
      // Update logged in status
      localStorage.setItem("isLoggedIn", "false");
      this.isLoggedIn = false;
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
    },
    updateLoggedInState: function(state) {
      this.isLoggedIn = state;
    }
  },
  mounted() {
    this.$loggedInEvent.$on("setLoggedInState", this.updateLoggedInState);
  }
};
</script>

<!-- The scoped attribute limits the CSS to this file only --->
<style scoped>
#container {
  margin-bottom: 50px;
}
</style>