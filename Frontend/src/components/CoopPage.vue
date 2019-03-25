<!--- This component acts as a page for a specific coop --->
<template>
    <div class="container">
        <CoopInfo :coop="coop"/>
        <div id="report-table" class="card">
            <div>
                <table>
                    <tr id="tr-heading">
                        <th class="td-report-type">
                            <h5>Report Type</h5>
                        </th>
                        <th class="td-report-status">
                            <h5>Report Status</h5>
                        </th>
                        <th class="td-due-date">
                            <h5>Due Date</h5>
                        </th>
                    </tr>
                </table>
            </div>
            <table v-if="coop.report != null">
                <CoopReportListItem
                    v-for="report in coop.report"
                    :key="report.id"
                    :report="report"
                />
            </table>
            <div v-else id="no-reports">
                <h4><br/>This coop has no reports.</h4>
            </div>
        </div>    
    </div>
</template>

<script>
import CoopInfo from "./CoopInfo.vue"
import CoopReportListItem from "./CoopReportListItem.vue"

export default {
    components: {
        CoopInfo,
        CoopReportListItem
    },
    props: {
        coop: {
            type: Object,
            required: true
        }
    }
}
</script>

<style>
.container {
  align-items: center;
}
#report-table {
  width: 65%;
  max-height: 380px;
  min-width: 550px;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
  background-color: rgb(53, 58, 62);
}
.td-report-type {
  width: 35%;
  text-align: left;
  padding-left: 15px;
}
.td-report-status {
  width: 35%;
  text-align: left;
  padding-left: 15px;
}
.td-due-date {
  width: 30%;
  text-align: left;
  padding-left: 15px;
}
#no-reports {
  text-align: center;
}
</style>