import random
import uuid
from datetime import datetime, timedelta

# --- CONFIGURATION ---
NUM_CUSTOMERS = 150
MIN_ACCOUNTS_PER_CUSTOMER = 1
MAX_ACCOUNTS_PER_CUSTOMER = 3
MIN_OPS_PER_ACCOUNT = 10
MAX_OPS_PER_ACCOUNT = 50
OUTPUT_FILE = 'massive_data.sql'

# --- DATA POOLS (Moroccan Flavor) ---
FIRST_NAMES = ['Yassine', 'Fatima Zahra', 'Hassan', 'Karim', 'Mehdi', 'Amina', 'Khadija', 'Tariq', 'Omar', 'Nadia', 'Salma', 'Youssef', 'Ayoub', 'Meryem', 'Zakaria', 'Imane', 'Hamza', 'Sanae']
LAST_NAMES = ['Berrada', 'El Amrani', 'Tazi', 'Benali', 'Chraibi', 'El Fassi', 'Mansouri', 'Bennis', 'Bennani', 'Alaoui', 'Tahiri', 'Daoudi', 'Lahlou', 'El Idrissi']
VENDORS = ['Marjane Supermarket', 'BIM Store', 'Carrefour', 'ONEE Electricity', 'Redal', 'Amendis', 'Maroc Telecom', 'Orange', 'Inwi', 'ONCF Train', 'CTM Bus', 'Local Cafe', 'Restaurant', 'Zara', 'LC Waikiki', 'Decathlon', 'Pharmacy', 'Netflix Subscription', 'Spotify']
CREDIT_DESCRIPTIONS = ['Salary Deposit', 'Freelance Payment', 'Transfer Received', 'Dividend Payout', 'Cash Deposit']

def get_random_date(start_date, end_date):
    time_between = end_date - start_date
    days_between = time_between.days
    random_number_of_days = random.randrange(days_between)
    return start_date + timedelta(days=random_number_of_days)

def main():
    print(f"Generating data for {NUM_CUSTOMERS} customers...")

    end_date = datetime.now()
    start_date = end_date - timedelta(days=365) # 1 year of data for the dashboard

    global_op_id = 1

    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        f.write("-- ==========================================\n")
        f.write("-- GENERATED BANKING DATASET\n")
        f.write("-- ==========================================\n\n")

        for cust_id in range(1, NUM_CUSTOMERS + 1):
            # 1. Generate Customer
            first_name = random.choice(FIRST_NAMES)
            last_name = random.choice(LAST_NAMES)
            name = f"{first_name} {last_name}"
            email = f"{first_name.lower().replace(' ', '.')}.{last_name.lower()}@example.com"

            f.write(f"INSERT INTO customer (id, name, email) VALUES ({cust_id}, '{name}', '{email}');\n")

            # 2. Generate Accounts for Customer
            num_accounts = random.randint(MIN_ACCOUNTS_PER_CUSTOMER, MAX_ACCOUNTS_PER_CUSTOMER)
            for _ in range(num_accounts):
                acc_id = f"acc-{uuid.uuid4()}"
                acc_type = random.choice(['CURR', 'SAVE'])
                overdraft = round(random.uniform(1000, 10000), 2) if acc_type == 'CURR' else 'NULL'
                interest = round(random.uniform(1.5, 5.0), 2) if acc_type == 'SAVE' else 'NULL'
                acc_created_at = get_random_date(start_date, end_date - timedelta(days=30))

                # Generate operations in memory to calculate exact balance
                operations_sql = []
                current_balance = 0.0

                # Initial Deposit
                initial_deposit = round(random.uniform(5000, 50000), 2)
                current_balance += initial_deposit
                op_date_str = acc_created_at.strftime('%Y-%m-%d %H:%M:%S')
                operations_sql.append(
                    f"INSERT INTO account_operation (id, amount, description, operation_date, type, bank_account_id) "
                    f"VALUES ({global_op_id}, {initial_deposit}, 'Initial Account Funding', '{op_date_str}', 'CREDIT', '{acc_id}');\n"
                )
                global_op_id += 1

                # Subsequent Operations
                num_ops = random.randint(MIN_OPS_PER_ACCOUNT, MAX_OPS_PER_ACCOUNT)
                for _ in range(num_ops):
                    op_date = get_random_date(acc_created_at, end_date)
                    op_date_str = op_date.strftime('%Y-%m-%d %H:%M:%S')

                    # 70% chance of DEBIT, 30% chance of CREDIT
                    if random.random() < 0.7:
                        op_type = 'DEBIT'
                        amount = round(random.uniform(10, 2000), 2)
                        desc = random.choice(VENDORS)
                        current_balance -= amount
                    else:
                        op_type = 'CREDIT'
                        amount = round(random.uniform(1000, 15000), 2)
                        desc = random.choice(CREDIT_DESCRIPTIONS)
                        current_balance += amount

                    operations_sql.append(
                        f"INSERT INTO account_operation (id, amount, description, operation_date, type, bank_account_id) "
                        f"VALUES ({global_op_id}, {amount}, '{desc}', '{op_date_str}', '{op_type}', '{acc_id}');\n"
                    )
                    global_op_id += 1

                # Write Account (now that we know the exact mathematical balance)
                acc_created_str = acc_created_at.strftime('%Y-%m-%d %H:%M:%S')
                f.write(f"INSERT INTO bank_account (id, type, balance, created_at, customer_id, over_draft, interest_rate) "
                        f"VALUES ('{acc_id}', '{acc_type}', {round(current_balance, 2)}, '{acc_created_str}', {cust_id}, {overdraft}, {interest});\n")

                # Write Operations
                for op_sql in operations_sql:
                    f.write(op_sql)

            f.write("\n") # Blank line between customers

    print(f"Done! Check the '{OUTPUT_FILE}' file.")

if __name__ == '__main__':
    main()