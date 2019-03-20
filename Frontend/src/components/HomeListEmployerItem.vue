<template>
  <!-- tr = table row, td = table data --->
  <tr>
    <td>
      <input
        class="form-check-input position-static checkbox"
        type="checkbox"
        id="blankCheckbox"
        value="option1"
        aria-label="..."
      >
    </td>
    <td>
      <span class="badge badge-success">Employer</span>
    </td>
    <td class="td-name">
      <span>{{ employer.name }}</span>
    </td>
    <td class="td-email">
      <span>{{ employer.email}}</span>
    </td>
  </tr>
</template>

<script>
export default {
  props: {
    employer: {
      type: Object,
      required: true
    }
  }
};
</script>

<style scoped>
span {
  color: rgb(241, 240, 240);
}

tr {
  background-color: rgb(53, 58, 62);
}

.td-name {
  width: 30%;
  text-align: left;
  padding-left: 15px;
}

.td-email {
  width: 70%;
  text-align: left;
}

.checkbox {
  margin-left: 20px;
  margin-right: 10px;
}
</style>