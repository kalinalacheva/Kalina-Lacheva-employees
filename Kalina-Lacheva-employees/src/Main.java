import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
public class Main {
    public static Date parseDate(String strDate) throws Exception
    {
        if (strDate != null && !strDate.isEmpty())
        {
            SimpleDateFormat[] formats =
                    new SimpleDateFormat[] {
                            new SimpleDateFormat("yyyy-MM-dd"),
                            new SimpleDateFormat("MM-dd-yyyy"),
                            new SimpleDateFormat("yyyyMMdd"),
                            new SimpleDateFormat("MM/dd/yyyy"),
                            new SimpleDateFormat("yyyy/MM/dd"),
                            new SimpleDateFormat("dd MMM yyyy"),
                            new SimpleDateFormat("dd MMMM yyyy"),
                            new SimpleDateFormat("MMM dd yyyy"),
                            new SimpleDateFormat("MMMM dd yyyy")
                    };

            Date parsedDate = null;

            for (int i = 0; i < formats.length; i++)
            {
                try
                {
                    parsedDate = formats[i].parse(strDate);
                    return parsedDate;
                }
                catch (ParseException e)
                {
                    continue;
                }
            }
        }
        throw new Exception("Unknown date format: '" + strDate + "'");
    }

    public static void main(String[] args){

        MyFrame frame = new MyFrame();
        String file_path=frame.getPath();
        Scanner sc = null;

        try {
            sc = new Scanner(new File(file_path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sc.useDelimiter(",");
        SecondFrame frame2=new SecondFrame();


        Map<Integer, List<Employee>> projects = new HashMap<>();

        while (sc.hasNext())
        {
           String [] s = sc.nextLine().split(", ");
            int id=Integer.parseInt(s[0]);
            int project=Integer.parseInt(s[1]);
            Date date1 = new Date();
            Date date2 = new Date();
            LocalDate localDate2;

            try {
                date1 = parseDate(s[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(s[3].equals("NULL")){
                localDate2 = LocalDate.now();
                date2 =Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
            else{
                try {
                    date2 = parseDate(s[3]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(projects.containsKey(project)){
                projects.get(project).add(new Employee(id,date1,date2));
            }
            else {
                projects.put(project, new ArrayList<Employee>(Collections.singleton(new Employee(id, date1, date2))));
            }

        }
        sc.close();
        long maxDays = 0;
        int maxID1=0;
        int maxID2=0;
        int maxProject=0;
        int k=0;
        ArrayList<String[]> dataList = new ArrayList<String[]>();
        String columns []={"Employee ID #1", "Employee ID #2", "Project ID", "Days worked"};

        for(Map.Entry<Integer, List<Employee>> entry:projects.entrySet()){
            int key=entry.getKey();
            List<Employee> employees=entry.getValue();
            for (int i=0;i<employees.size();i++) {
                Employee em1 = employees.get(i);
                for(int j=i+1;j<employees.size();j++){
                    Employee em2 = employees.get(j);
                    long days = em1.getDaysBetween(em2);
                    if(days > maxDays){
                        maxProject = key;
                        maxID1 = em1.getID();
                        maxID2 = em2.getID();
                        maxDays=days;
                    }
                    dataList.add(new String[] {Integer.toString(em1.getID()), Integer.toString(em2.getID()), Integer.toString(key),Long.toString(days)});
                }
            }
        }
        String[][] data = new String[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
       frame2.showTable(data,columns);
        System.out.printf("Maximum days worked together: Employee ID #1: %d Employee ID #2: %d Project ID: %d Days worked: %d\n",maxID1,maxID2,maxProject,maxDays);
    }
}
