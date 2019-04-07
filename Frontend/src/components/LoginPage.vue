<!--- This component acts as a page to log in --->
<template>
  <div id="login" class="card" v-bind:style="{ backgroundColor : bgColor }">
    <span id="title" v-bind:style="{ color : textColor }">Administrator Login:</span>
    <div>
      <span id="title1"></span>
    </div>
    <b-container fluid>
      <input class="loginField" type="text" id="username" v-model="email" placeholder="Enter email">
      <input
        class="loginField"
        type="password"
        id="password"
        v-model="pw"
        placeholder="Enter password"
      >
      <button
        type="button"
        v-on:click="login(email, pw)"
        class="btn btn-danger btn-lg loginField button"
        v-b-tooltip.hover
        title="Click to login!"
      >Login</button>
      <button
        type="button"
        v-on:click="goToAccountPage()"
        class="btn btn-danger btn-lg loginField button"
        v-b-tooltip.hover
        title="Click to create a new account!"
      >Create an Account</button>
    </b-container>
  </div>
</template>

<script>
import axios from "axios";
import Router from "../router";

var config = require("../../config");

// Axios config
var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  data() {
    return {
      admin: {
        type: Object
      },
      bgColor: "",
      textColor: "",
      error: "",
      pw: "",
      email: ""
    };
  },
  created: function() {
    var darkModeOn = localStorage.getItem("DarkModeOn");
    if (darkModeOn === "true") {
      this.bgColor = "rgb(53, 58, 62)";
      this.textColor = "white";
    } else {
      this.bgColor = "rgb(248, 249, 251)";
      this.textColor = "black";
    }
  },
  methods: {
    // Send GET request to find admin
    login: function(email, pw) {
      AXIOS.get(`/admin/` + email)
        .then(response => {
          this.admin = response.data;
          if (this.admin.password === pw) {
            this.goToHomePage();
            localStorage.setItem("isLoggedIn", "true");
            this.$loggedInEvent.$emit("setLoggedInState", true);
          } else {
            document.getElementById("title1").innerText = "Password Incorrect";
          }
        })
        .catch(e => {
          console.log(e.message);
          document.getElementById("title1").innerText =
            "Account does not exist";
        });
    },
    goToHomePage: function() {
      Router.push({
        path: "/home/",
        name: "Home"
      });
    },
    goToAccountPage: function() {
      Router.push({
        path: "/account/",
        name: "AccountPage"
      });
    },
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
  },
  mounted() {
    this.$root.$on("setDarkModeState", this.setDarkMode);
  }
};
</script>

<style>
#title {
  text-align: left;
  color: white;
  font-size: 30px;
  padding-left: 15px;
}

#title1 {
  text-align: left;
  color: red;
  font-size: 15px;
  padding-left: 15px;
}

#send {
  align-content: right;
}

#name {
  text-align: left;
  color: white;
  font-size: 26px;
  padding-left: 15px;
}

#login {
  width: 30%;
  max-height: 480px;
  min-width: 550px;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
}

.loginField {
  width: 98%;
  border-radius: 4px;
  border: 0px;
  padding: 2%;
  margin: auto;
  margin-top: 15px;
}

.button {
  color: white;
  background-color: rgb(46, 126, 201);
}
</style>