package client;

import proxy.Account;
import proxy.BankAccountService;
import proxy.BankWS;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    BankAccountService prxyWS = new BankWS().getBankAccountServicePort();

    // convertEuroToMAD
    double amount = 146;
    double mad = prxyWS.convertEuroToMAD(amount);

    System.out.println(
        "======= convertEuroToMAD: Convert Euro to MAD (146DH) ======\n" +
            "Amount (€): " + amount + "\n" +
            "Result (MAD): " + mad + "\n" +
            "============================================================\n"
    );

    // getAccount
    Account account3 = prxyWS.getAccount(3);

    System.out.println(
        "============ getAccount: Account Info - code 3  ============\n" +
            "Code      : " + account3.getCode() + "\n" +
            "Balance   : " + account3.getBalance() + "\n" +
            "Created At: " + account3.getCreatedAt() + "\n" +
            "============================================================\n"
    );

    // listAccounts
    List<Account> accounts = prxyWS.listAccounts();

    System.out.println("=============== listAccounts: Accounts =====================");
    System.out.printf("%-10s | %-10s | %-25s%n", "Code", "Balance", "Created At");
    System.out.println("------------------------------------------------------------");

    for (Account acc : accounts) {
      System.out.printf(
          "%-10d | %-10.2f | %-25s%n",
          acc.getCode(),
          acc.getBalance(),
          acc.getCreatedAt()
      );
    }

    System.out.println("============================================================");
  }
}
