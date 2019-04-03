<! --- This component acts as a page to create a notification -->
<template>
  <div id="login">
    <span id="title">Login:</span>
   
    <b-container fluid>
      <b-form-textarea
        id="username"
        size="lg"
        rows="1"
        v-model="email"
        placeholder="Enter email"
      />
      <b-form-textarea
        id="password"
        size="lg"
        rows="1"
        v-model="pw"
        placeholder="Enter password"
      />
      <button
        id="login"
        type="button"
        v-on:click="login(convertMessage(email), convertMessage(pw))"
        class="btn btn-light btn-lg"
      >Send</button>
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
      admin:{
          type: Object
      },
      error: ""
    };
  },
  methods: {
    // Send get request to find admin
    login: function(email, pw) {
      AXIOS.get(
            `/admin/` +
              email
          )
        .then(response => {
            alert("Success!");
            this.admin = response.data;
        })
        .catch(e => {
            this.error = e;
        });
      if(this.admin.password === 'pw'){
          this.goToHomePage();
      }
      else{
          //clear 
      }

    },
    goToHomePage: function(){
        Router.push({
          path: "/",
          name: "HomePage",
        });
    } 
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
  color: white;
  font-size: 26px;
  padding-left: 15px;
}

#send {
  margin-top: 10px;
  align-content: right;
}

#name {
  text-align: left;
  color: white;
  font-size: 26px;
  padding-left: 15px;
}

#login {
  width: 70%;
  max-height: 380px;
  min-width: 550px;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}
</style>