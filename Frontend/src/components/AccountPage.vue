<!--- This component acts as a page to create an account -->
<template>
  <div id="account" class="card" v-bind:style="{ backgroundColor : bgColor }">
    <span id="title" v-bind:style="{ color : textColor }">Create an Administrator Account:</span>
    <div>
      <span id="title1"></span>
    </div>
    <b-container fluid>
      <input class="loginField" type="text" id="username" v-model="email" placeholder="Enter Email">
      <input class="loginField" type="text" id="username" v-model="name" placeholder="Enter Name">
      <input
        class="loginField"
        type="text"
        id="phone"
        v-model="num"
        placeholder="Enter Phone Number"
      >
      <input
        class="loginField"
        type="password"
        id="password"
        v-model="pw"
        placeholder="Enter Password"
      >
      <button
        type="button"
        v-on:click="create(email, pw, name, num)"
        class="btn btn-light btn-lg, loginField"
        v-b-tooltip.hover
        title="Click to create!"
      >Create</button>
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
      email: "",
      num: "",
      name: ""
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
    // Send get request to find admin
    create: function(email, pw, name, num) {
      AXIOS.post(
        `/admin/create?email=` +
          email +
          `&password=` +
          pw +
          `&name=` +
          name +
          `&phone=` +
          num
      )
        .then(response => {
          this.admin = response.data;
          this.goToHomePage();
        })
        .catch(e => {
          console.log(e.message);
          document.getElementById("title1").innerText =
            "Please enter missing account information!";
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
  }
};
</script>

<style>
#title {
  text-align: left;
  color: white;
  font-size: 26px;
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

#account {
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
</style>