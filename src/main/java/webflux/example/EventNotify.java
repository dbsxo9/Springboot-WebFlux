package webflux.example;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Setter
public class EventNotify {

    private List<String> events = new ArrayList<>();

    private boolean change = false;

    public void add(String data){
        events.add(data);
        change = true;
    }

    public boolean getChange(){
        return change;
    }

    public List<String> getEvents(){
        return events;
    }
}
