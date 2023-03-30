import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Employee {
    private int ID;
    private Date startDate;
    private Date endDate;

    public Employee(int id, Date startDate, Date endDate) {
        this.ID = id;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public int getID(){
        return this.ID;
    }
    public Date getStartDate(){
        return this.startDate;
    }
    public Date getEndDate(){
        return this.endDate;
    }

    private static long calculateDays(Date firstDate, Date secondDate){
        long daysDiff = 0;
        try {
            daysDiff = Math.abs(secondDate.getTime() - firstDate.getTime())/86400000;
        }catch(Exception e){
            e.printStackTrace();
        }
        return daysDiff;

    }

    private static long compareDates(Date em1Start, Date em1End, Date em2Start, Date em2End){
        //em1End before em2Start
        if(em1End.compareTo(em2Start) < 0){
            return 0;
        }
        else{
            if(em1End.compareTo(em2End) < 0){
                return calculateDays(em2Start,em1End);
            }
            else {
                return calculateDays(em2Start,em2End);
            }
        }
    }

    public long getDaysBetween(Employee e){

        List<Date> listOfDates= new ArrayList<>();
        listOfDates.add(this.getStartDate());
        listOfDates.add(this.getEndDate());

        listOfDates.add(e.getStartDate());
        listOfDates.add(e.getEndDate());

        Date minDate = Collections.min(listOfDates);

        if(minDate.compareTo(this.getStartDate()) == 0){
            return compareDates(minDate,this.getEndDate(),e.getStartDate(),e.getEndDate());
        }
        else{
            return compareDates(minDate,e.getEndDate(),this.getStartDate(),this.getEndDate());
        }
    }
}