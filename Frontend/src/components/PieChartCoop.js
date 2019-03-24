import { Pie } from './BaseCharts'
import { reactiveProp } from './mixins'

export default {
  extends: Pie,
  mixins: [reactiveProp],
  data: () => ({
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  }),
  mounted () {
    this.renderChart({
      labels: [
        'Completed',
        'In Progress',
        'Not Started'
      ],
      datasets: [
        {
          backgroundColor: [
            '#96ceb4',
            '#ffeead',
            '#ffcc5c'
          ],
          data: [
            this.chartData.completedCoops,
            this.chartData.inProgressCoops,
            this.chartData.notStartedCoops
          ]
        }
      ]
    }, this.options)
  }
}