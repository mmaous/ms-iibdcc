package ma.enset.digitalbanking.dtos;

import lombok.Data;

@Data
public abstract class BankAccountDTO {
  private String type; // "CURR" or "SAVE"
}