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
        'Unsubmitted',
        'Submitted',
        'Late',
        'Reviewed'
      ],
      datasets: [
        {
          backgroundColor: [
            '#92a8d1',
            '#034f84',
            '#f7cac9',
            '#f7786b'
          ],
          data: [
            this.chartData.unsubmittedReports,
            this.chartData.submittedReports,
            this.chartData.lateReports,
            this.chartData.reviewedReports
          ]
        }
      ]
    }, this.options)
  }
}