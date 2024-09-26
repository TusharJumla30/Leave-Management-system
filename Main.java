import java.time.LocalDate;
import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name of the employee:");
        String name = in.nextLine();
        System.out.println("Enter leave type 1: Sick Leave, 2: Casual Leave, 3: Vacation Leave.. ");
        int leave_type = in.nextInt();
        LocalDate start_date = null;
        LocalDate end_date = null;
        String reason = null;
        switch(leave_type) 
        {
            case 1: break;
            case 2: in.nextLine(); 
                    System.out.println("Enter reason:");
                    reason = in.nextLine();
                    break;
            case 3: System.out.println("Enter starting date:");
                    start_date = LocalDate.parse(in.next());
                    System.out.println("Enter ending date:");
                    end_date = LocalDate.parse(in.next());
                    break;
            default: System.out.println("Invalid leave type entered");
                     return;
        }
        Request_Leave leave;
        switch(leave_type) 
        {
            case 1: leave = new sick_leave(name, LocalDate.now());
                    break;
            case 2: leave = new casual_leave(name, LocalDate.now(), reason);
                    break;
            case 3: leave = new vacation_leave(name, LocalDate.now(), start_date, end_date);
                    break;
            default: return;
        }
        director director = new director();
        project_manager projectmanager = new project_manager(director);
        tech_lead techlead = new tech_lead(projectmanager); 
        techlead.handleRequest(leave);
        in.close();
    }
}