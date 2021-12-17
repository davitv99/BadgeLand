package io.project.app.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntOfWeekday implements Serializable {
    private HashMap<String,Integer> intDayOfWeek = new HashMap<>();

    public HashMap<String,Integer> weekdayToInt(){
    intDayOfWeek.put("Sunday",0);
        intDayOfWeek.put("Monday",1);
        intDayOfWeek.put("Tuesday",2);
        intDayOfWeek.put("Wednesday",3);
        intDayOfWeek.put("Thursday",4);
        intDayOfWeek.put("Friday",5);
        intDayOfWeek.put("Saturday",6);
        return intDayOfWeek;
    }
}
