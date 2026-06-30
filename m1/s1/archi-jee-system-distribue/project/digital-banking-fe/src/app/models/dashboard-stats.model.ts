export interface MonthlyOperation {
    month: string;
    debitSum: number;
    creditSum: number;
}

export interface CustomerBalance {
    customerName: string;
    totalBalance: number;
}

export interface DashboardStats {
    monthlyOperations: MonthlyOperation[];
    accountTypeDistribution: Record<string, number>;
    recentTransactionVolume: number[];
    balancePerCustomer: CustomerBalance[];
}