import jakarta.xml.ws.Endpoint;
import ws.BankAccountService;

public class WSEntrypoint {
  public static void main(String[] args) {
    String url = "http://0.0.0.0:8686/";
    Endpoint.publish(url, new BankAccountService());
    System.out.println("Web Service Deployed on : "+url);
  }
}
