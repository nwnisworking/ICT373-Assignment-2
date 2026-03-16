package ict373.assignment2.controllers.publication.forms;

import ict373.assignment2.FormRecord;
import ict373.assignment2.models.publications.*;
import ict373.assignment2.ui.inputs.*;
import java.util.List;

public record DetailForm(
  TextInputField name,
  TextInputField cost,
  SelectInputField<String> type,
  SelectInputField<Publication> magazine
) implements FormRecord<Publication>{
  public List<InputField<?, ?>> fields(){
    return List.of(name, cost, type, magazine);
  }

  @Override
  public void save(Publication data){
    data.setName(name.getValue());
    data.setCost(Double.parseDouble(cost.getValue()));
    
    if(data instanceof Supplement s){
      s.setMagazine((Magazine) magazine.getValue());
    }
  }

  @Override
  public void load(Publication data){
    name.setValue(data.getName());
    cost.setValue(data.getCost() + "");
    type.setValue(data.getType());
    
    if(data instanceof Supplement){
      magazine.setValue(((Supplement) data).getMagazine());
    }
  }
}
