<template>
  <div id="app">
    <LogoBar/>
    <router-view></router-view>
  </div>
</template>

<script>
import LogoBar from "./components/LogoBar.vue";

export default {
  name: "app",
  components: {
    LogoBar
  }
};
</script>

<style>
body,
html {
  background-color: rgb(88, 96, 102) !important;
}

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
