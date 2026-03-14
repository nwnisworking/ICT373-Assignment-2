package ict373.assignment2.controllers.customer.tabs;

import ict373.assignment2.models.Address;
import ict373.assignment2.TabRecord;
import ict373.assignment2.ui.inputs.InputField;
import ict373.assignment2.ui.inputs.TextInputField;
import java.util.List;

public record AddressTab(
  TextInputField street,
  TextInputField block,
  TextInputField postal,
  TextInputField unit
) implements TabRecord<Address>{
  @Override
  public List<InputField<?, ?>> fields(){
    return List.of(street, block, postal, unit);
  }

  @Override
  public void save(Address data){
    if(data == null) return;

    data.setStreetName(street.getValue());
    data.setBlockNumber(Integer.parseInt(block.getValue()));
    data.setPostalCode(Integer.parseInt(postal.getValue()));
    data.setUnitNumber(unit.getValue());
  }

  @Override
  public void load(Address data){
    if(data == null) return;
    
    street.setValue(data.getStreetName());
    block.setValue(data.getBlockNumber() + "");
    postal.setValue(data.getPostalCode() + "");
    unit.setValue(data.getUnitNumber());
  }
}