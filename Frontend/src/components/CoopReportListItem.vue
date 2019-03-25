<template>
    <tr>
        <td class="td-report-type">
            <span v-if="report.type === 'Contract'">Employer Contract</span>
            <span v-else-if="report.type === 'Technical'">Technical Report</span>
            <span v-else-if="report.type === 'StudentEval'">Student Evaluation</span>
            <span v-else-if="report.type === 'EmployerEval'">Employer Evaluation</span>
            <span v-else>Biweekly Report</span>
        </td>
        <td class="td-report-status">
            <span style="color:yellow" v-if="report.status === 'Unsubmitted'">Unsubmitted</span>
            <span style="color:lightblue" v-else-if="report.status === 'Submitted'">Submitted</span>
            <span style="color:red" v-else-if="report.status === 'Late'">Late</span>
            <span style="color:lightgreen" v-else>Reviewed</span>
        </td>
        <td class="td-due-date">
            <span>{{ report.dueDate }}</span>
        </td>
    </tr>
</template>

<script>
export default {
  props: {
    report: {
      type: Object,
      required: true
    }
  }
}
</script>

<style>
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
</style>
