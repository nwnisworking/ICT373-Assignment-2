package ict373.assignment2.controllers.customer.tabs;

import ict373.assignment2.TabRecord;
import ict373.assignment2.models.payments.CreditCard;
import ict373.assignment2.models.payments.DirectDebit;
import ict373.assignment2.models.payments.Method;
import ict373.assignment2.ui.inputs.*;
import java.util.List;

public record PaymentTab(
  SelectInputField<String> method,
  TextInputField card_number,
  DateInputField expiry_date,
  TextInputField account_number,
  TextInputField bank_name
) implements TabRecord<Method>{
  @Override
  public List<InputField<?, ?>> fields(){
    return List.of(method, card_number, expiry_date, account_number, bank_name);
  }

  @Override
  public void save(Method data){
    switch(data){
      case CreditCard cc -> {
        cc.setCardNumber(card_number.getValue());
        cc.setExpiryDate(expiry_date.getValue());
      }
      case DirectDebit dd -> {
        dd.setAccountNumber(account_number.getValue());
        dd.setBankName(bank_name.getValue());
      }
      default -> {}
    }
  }

  @Override
  public void load(Method data){
    switch(data){
      case CreditCard cc -> {
        method.setValue("Credit Card");
        card_number.setValue(cc.getCardNumber());
        expiry_date.setValue(cc.getExpiryDate());
      }
      case DirectDebit dd ->{
        method.setValue("Direct Debit");
        account_number.setValue(dd.getAccountNumber());
        bank_name.setValue(dd.getBankName());
      }
      default -> {}
    }
  }
}