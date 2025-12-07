package ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.Date;
import java.util.List;

@WebService(serviceName = "BankWS")
public class BankAccountService {

  @WebMethod(operationName = "ConvertEuroToMAD")
  public double convertToMAD(@WebParam(name = "amount") double amount){
    return amount*11;
  }

  @WebMethod
  public Account getAccount(@WebParam(name = "code") int code){
    return new Account(code, 2500, new Date());
  }

  @WebMethod
  public List<Account> listAccounts() {
    return List.of(
        new Account(1, 2500, new Date()),
        new Account(2, 7800, new Date()),
        new Account(3, 1200, new Date())
    );
  }

}
