/*
Name: Prabagar Sivakumar
*/
import java.text.SimpleDateFormat;
import java.util.*;

public class Appointment implements Comparable<Appointment>
{
    private Calendar date;
    private String description;
    
    //Constructor Method
    public Appointment(int year, int month, int day, int hour, int minute, String description)
    {
        date = new GregorianCalendar(year, month, day, hour, minute);
        this.description = description;
    }

    public String getDescription() {return description;}   
    public void setDescription(String d){
    description = d;
    }
    
    //Print method 
    public String toString()
    {
       SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); 
	   return (sdf.format(date.getTime()) + " " + description);
    }

    //Check if appointment is on same day and time
    public boolean occursOn(int year, int month, int day, int hour, int minute)
    {
         if ((year == this.date.get(Calendar.YEAR)) && (month == this.date.get(Calendar.MONTH)) && (day == this.date.get(Calendar.DAY_OF_MONTH)) && (hour == this.date.get(Calendar.HOUR)) && (minute == this.date.get(Calendar.MINUTE)))
         return true;

         return false;
    }

    //Check if appointment is on same day
    public boolean checking(int year, int month, int day)
    {
         if ((year == this.date.get(Calendar.YEAR)) && (month == this.date.get(Calendar.MONTH)) && (day == this.date.get(Calendar.DAY_OF_MONTH)))
         return true;

         return false;
    }

    //Sorting appointments based on time
    public int compareTo(Appointment other)
    {
        if ((this.date.get(Calendar.HOUR_OF_DAY) >= other.date.get(Calendar.HOUR_OF_DAY)) && (this.date.get(Calendar.MINUTE) >= other.date.get(Calendar.MINUTE))) return 1;
        else if ((this.date.get(Calendar.HOUR_OF_DAY) <= other.date.get(Calendar.HOUR_OF_DAY)) && (this.date.get(Calendar.MINUTE) <= other.date.get(Calendar.MINUTE))) return -1;
        else if ((this.date.get(Calendar.HOUR_OF_DAY) <= other.date.get(Calendar.HOUR_OF_DAY)) && (this.date.get(Calendar.MINUTE) >= other.date.get(Calendar.MINUTE))) return -1;
        else return 0;
    }
}
