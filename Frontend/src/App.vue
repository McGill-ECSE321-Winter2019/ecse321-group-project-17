<template>
  <div id="app" v-bind:style="{ backgroundColor: bgColor }">
    <LogoBar @setDarkModeState="setBackgroundColor"/>
    <router-view></router-view>
  </div>
</template>

<script>
import LogoBar from "./components/LogoBar.vue";

export default {
  name: "app",
  components: {
    LogoBar
  },
  data() {
    return {
      // rgb(88, 96, 102)
      bgColor: ""
    };
  },
  created: function() {
    if (!localStorage.getItem("DarkModeOn")) {
      // Set local storage value if none exists
      localStorage.setItem("DarkModeOn", "false");
    } else {
      // Local storage value exists
      var darkModeOn = localStorage.getItem("DarkModeOn");
      if (darkModeOn === "true") this.bgColor = "rgb(88, 96, 102)";
      else this.bgColor = "white";
    }
  },
  methods: {
    setBackgroundColor: function(darkModeOn) {
      if (darkModeOn == true) this.bgColor = "rgb(88, 96, 102)";
      else this.bgColor = "white";
      this.$root.$emit("setDarkModeState", darkModeOn); // Emit to all children
    }
  }
};
</script>

<style>
body,
html {
  margin: 0;
  height: 100%;
}

#app {
  /* background-color: rgb(88, 96, 102) !important; */
  height: 100%;
  overflow: auto;
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
