<! --- This component acts as a page to create a notification -->
<template>
  <div id="notif-container">
    <span id="title">Notification:</span>
    <div>
      <span id="title1">Send To: {{ getProfiles(selected) }}</span>
    </div>
    <b-container fluid>
      <b-form-textarea
        id="textarea-default"
        size="lg"
        rows="6"
        v-model="message"
        placeholder="Enter notification message"
      />
      <button
        id="send"
        type="button"
        v-on:click="sendNotification(selected, convertMessage(message))"
        class="btn btn-light btn-lg"
      >Send</button>
    </b-container>
  </div>
</template>

<script>
import axios from "axios";
import HomeListStudentItemVue from "./HomeListStudentItem.vue";

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
  props: {
    selected: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      message: ""
    };
  },
  methods: {
    // Display profile names in "send to:"
    getProfiles: function(profiles) {
      var length = profiles.length;
      var result = "";
      for (var i = 0; i < length - 1; i++) {
        result = result + profiles[i].name + ", ";
      }
      if (length > 1) {
        result += profiles[length - 1].name;
      } else {
        result += profiles[0].name;
      }
      return result;
    },
    // Send post request to create notification
    sendNotification: function(profiles, message) {
      var length = profiles.length;
      if (length == 1) {
        if (profiles[0].company == null) {
          AXIOS.post(
            `/notification/create?text=` +
              message +
              `&senderEmail=admin@gmail.com&stuEmail=` +
              profiles[0].email
          )
            .then(response => {
              alert("Success!");
            })
            .catch(e => {
              this.error = e;
            });
        } else {
          AXIOS.post(
            `/notification/create?text=` +
              message +
              `&senderEmail=admin@gmail.com&empEmail=` +
              profiles[0].email
          )
            .then(response => {
              alert("Success!");
            })
            .catch(e => {
              this.error = e;
            });
        }
      } else {
        var students = this.getStudents(profiles);
        var employers = this.getEmployers(profiles);
        if (employers.length == 0) {
          var studentEmails = this.getProfilesEmails(students);
          AXIOS.post(
            `/notification/create-many?text=` +
              message +
              `&senderEmail=admin@gmail.com&stuEmail=` +
              studentEmails
          )
            .then(response => {
              alert("Success!");
            })
            .catch(e => {
              this.error = e;
            });
        } else if (students.length == 0) {
          var employerEmails = this.getProfilesEmails(employers);
          AXIOS.post(
            `/notification/create-many?text=` +
              message +
              `&senderEmail=admin@gmail.com&empEmail=` +
              employerEmails
          )
            .then(response => {
              alert("Success!");
            })
            .catch(e => {
              this.error = e;
            });
        } else {
          var studentEmails = this.getProfilesEmails(students);
          var employerEmails = this.getProfilesEmails(employers);
          AXIOS.post(
            `/notification/create-many?text=` +
              message +
              `&senderEmail=admin@gmail.com&stuEmail=` +
              studentEmails +
              `&empEmail=` +
              employerEmails
          )
            .then(response => {
              alert("Success!");
            })
            .catch(e => {
              this.error = e;
            });
        }
      }
    },
    // Get list of student emails for post request
    getStudents: function(profiles) {
      var students = new Array();
      for (var i = 0; i < profiles.length; i++) {
        if (profiles[i].company == null) {
          students.push(profiles[i]);
        }
      }
      return students;
    },
    getEmployers: function(profiles) {
      var employers = new Array();
      for (var i = 0; i < profiles.length; i++) {
        if (profiles[i].company != null) {
          employers.push(profiles[i]);
        }
      }
      return employers;
    },
    getProfilesEmails: function(profiles) {
      var result = "";
      var length = profiles.length;
      for (var i = 0; i < length - 1; i++) {
        result = result + profiles[i].email + ",";
      }
      if (length > 1) {
        result += profiles[length - 1].email;
      } else {
        result += profiles[0].email;
      }
      return result;
    },
    convertMessage: function(message) {
      return message.replace(/ /g, "+");
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

#notif-container {
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