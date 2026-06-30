export interface AccountOperation {
    id: number;
    operationDate: Date;
    amount: number;
    type: 'DEBIT' | 'CREDIT';
    description: string;
}