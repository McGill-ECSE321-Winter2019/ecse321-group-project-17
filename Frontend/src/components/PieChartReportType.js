import { Doughnut } from './BaseCharts'
import { reactiveProp } from './mixins'

export default {
  extends: Doughnut,
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
        'Contract ',
        'Technical',
        'Student Evaluation',
        'Employer Evaluation',
        'Two Week'
    ],
      datasets: [
        {
          backgroundColor: [
            '#f9d5e5',
            '#eeac99',
            '#ff6f69',
            '#e06377',
            '#c83349'
          ],
          data: [
            this.chartData.contractReports,
            this.chartData.technicalReports,
            this.chartData.studentEvalReports,
            this.chartData.employerEvalReports,
            this.chartData.twoWeekReports
          ]
        }
      ]
    }, this.options)
  }
}