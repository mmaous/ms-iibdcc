import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartOptions } from 'chart.js';
import { AnalyticsService } from '../services/analytics';
import { map } from 'rxjs/operators';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [CommonModule, BaseChartDirective],
    templateUrl: './dashboard.html'
})
export class DashboardComponent {
    private analyticsService = inject(AnalyticsService);

    // Clean, modern chart options
    public commonOptions: ChartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: { position: 'bottom', labels: { usePointStyle: true, boxWidth: 8 } }
        },
        scales: {
            x: { grid: { display: false } }, // Hides vertical grid lines
            y: { border: { dash: [4, 4] }, grid: { color: '#e5e7eb' } } // Subtle dashed horizontal lines
        }
    };

    // Pie/Doughnut charts shouldn't have x/y axes
    public doughnutOptions: ChartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: { position: 'bottom', labels: { usePointStyle: true, boxWidth: 8 } }
        },
    };

    public horizontalBarOptions: ChartOptions = {
        ...this.commonOptions,
        indexAxis: 'y',
        scales: {
            x: { border: { dash: [4, 4] }, grid: { color: '#e5e7eb' } },
            y: { grid: { display: false } }
        }
    };

    dashboardData$ = this.analyticsService.getStats().pipe(
        map(stats => ({
            monthlyChartData: {
                labels: stats.monthlyOperations.map(op => op.month),
                datasets: [
                    { data: stats.monthlyOperations.map(op => op.debitSum), label: 'Debit', backgroundColor: '#ef4444', borderRadius: 4 },
                    { data: stats.monthlyOperations.map(op => op.creditSum), label: 'Credit', backgroundColor: '#22c55e', borderRadius: 4 }
                ]
            } as ChartConfiguration<'bar'>['data'],

            pieChartData: {
                labels: Object.keys(stats.accountTypeDistribution),
                datasets: [{
                    data: Object.values(stats.accountTypeDistribution),
                    backgroundColor: ['#3b82f6', '#f59e0b'],
                    borderWidth: 0,
                    hoverOffset: 4
                }]
            } as ChartConfiguration<'doughnut'>['data'],

            lineChartData: {
                labels: stats.recentTransactionVolume.map((_, i) => `Tx ${i + 1}`),
                datasets: [{
                    data: stats.recentTransactionVolume,
                    label: 'Transaction Amount',
                    borderColor: '#8b5cf6',
                    tension: 0.4, // Smooth curves
                    fill: true,
                    backgroundColor: 'rgba(139, 92, 246, 0.1)',
                    pointBackgroundColor: '#8b5cf6',
                    pointBorderWidth: 2
                }]
            } as ChartConfiguration<'line'>['data'],

            topCustomersData: {
                labels: stats.balancePerCustomer.map(c => c.customerName),
                datasets: [{
                    data: stats.balancePerCustomer.map(c => c.totalBalance),
                    label: 'Total Balance',
                    backgroundColor: '#14b8a6',
                    borderRadius: 4
                }]
            } as ChartConfiguration<'bar'>['data']
        }))
    );
}