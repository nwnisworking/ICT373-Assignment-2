package ict373.assignment2.controllers.customer.tabs;

import ict373.assignment2.TabRecord;
import ict373.assignment2.ui.inputs.*;
import ict373.assignment2.models.customers.*;
import java.util.List;


public record ProfileTab(
  TextInputField name,
  TextInputField email,
  SelectInputField<String> type,
  SelectInputField<Customer> payer
) implements TabRecord<Customer>{
  @Override
  public List<InputField<?, ?>> fields(){
    return List.of(name, email, type, payer);
  }

  @Override
  public void save(Customer customer){
    if(customer == null) return;

    if(customer instanceof AssociateCustomer ac){
      if(payer.getValue() != null){
        ((PayingCustomer) payer.getValue()).addAssociate(ac);
      }
    }
    
    customer.setName(name.getValue());
    customer.setEmail(email.getValue());
  }

  @Override
  public void load(Customer customer){
    if(customer == null) return;
    
    if(customer instanceof AssociateCustomer ac){
      if(ac.getPayer() != null){
        payer.setValue(ac.getPayer());
      }
      
      type.setValue("Associate Customer");
    }
    else{
      type.setValue("Paying Customer");
    }
    
    name.setValue(customer.getName());
    email.setValue(customer.getEmail());
  }
}