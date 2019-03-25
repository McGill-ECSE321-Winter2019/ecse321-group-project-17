<template>
  <div class="container">
    <div class="Chart">
      <h1 style="text-align:center;">Barchart</h1>
      <bar-chart :chartData="students.length"/>
      Total: {{students.length}}
    </div>
  </div>
</template>

<script>
  import BarChart from './BarChart'

  export default {
    props: {
      students: {
        type: Array,
        required: true
      },
      employers: {
        type: Array,
        required: true
      }
    },
    components: {
      BarChart
    },
    data () {
      return {
        dataPoints: null,
        height: 20
      }
    },
    mounted () {
      setInterval(() => {
        this.fillData()
      }, 2000)
    },
    methods: {
      increaseHeight () {
        this.height += 10
      },
      getRandomInt () {
        return Math.floor(Math.random() * (50 - 5 + 1)) + 5
      },
      fillData () {
        this.dataPoints = {
          labels: ['Number of students'],
          datasets: [
            {
              label: 'Data One',
              backgroundColor: '#f87979',
              data: [1]
            }
          ]
        }
      }
    },
    computed: {
      myStyles () {
        return {
          height: `${this.height}px`,
          position: 'relative'
        }
      }
    }
  }
</script>

<style>
  .container {
    max-width: 800px;
    margin: 0 auto;
  }
  h1 {
    font-family: 'Helvetica', Arial;
    color: #464646;
    text-transform: uppercase;
    border-bottom: 1px solid #f1f1f1;
    padding-bottom: 15px;
    font-size: 28px;
    margin-top: 0;
  }
  .Chart {
    padding: 20px;
    box-shadow: 0px 0px 20px 2px rgba(0, 0, 0, .4);
    border-radius: 20px;
    margin: 50px 0;
  }
</style>
