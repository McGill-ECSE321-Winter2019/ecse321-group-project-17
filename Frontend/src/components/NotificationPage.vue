<! --- This component acts as a page to create a notification -->
<template>
    <div id="home-container">
        <span 
            id="title">Notification:
        </span>
        <div>
        <span 
            id="title1">Send to:
        </span>
        </div>
        <b-container fluid>
            <b-form-textarea 
                id="textarea-default"
                size="lg"
                placeholder="Enter notification message" />
            <button
                id="send"
                type="button"
                class="btn btn-light btn-lg">Send
            </button>
        </b-container>
    </div>
</template>

<script>
import axios from "axios";

var config = require("../../config");

var frontendUrl = "http://" + config.build.host + ":" + config.build.port;
var backendUrl =
  "https://" + config.build.backendHost + ":" + config.build.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});
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
    align-content: right
}
</style>