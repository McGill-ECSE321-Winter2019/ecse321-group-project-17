import { Bar } from './BaseCharts'
import { reactiveProp } from './mixins'

export default {
  extends: Bar,
  mixins: [reactiveProp],
  data: () => ({
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  }),

  mounted () {
    // Overwriting base render method with actual data.
    this.renderChart({
      labels: ['Students'],
      datasets: [
        {
          label: 'Number',
          backgroundColor: '#f87979', //color of bars when hovering
          data: [this.chartData]
        }
      ]
    }, this.options)
    // this.renderChart(this.students, this.options)
  }
}