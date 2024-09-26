import java.time.LocalDate;

public abstract class Request_Leave 
{
    private String name_of_employee;
    private String leave_status;
    private String approval;
    private LocalDate date_of_request;
    private LocalDate date_of_approval;
    public Request_Leave(String name_of_employee, LocalDate date_of_request) 
    {
        this.name_of_employee = name_of_employee;
        this.leave_status = "New";
        this.approval = null;
        this.date_of_request = date_of_request;
    }
    public String getname_of_employee() 
    {
        return name_of_employee;
    }
    public String getleave_status() 
    {
        return leave_status;
    }
    public void setleave_status(String leave_status) 
    {
        this.leave_status = leave_status;
    }
    public String getapproval() 
    {
        return approval;
    }
    public void setapproval(String approval) 
    {
        this.approval = approval;
    }
    public LocalDate getdate_of_request() 
    {
        return date_of_request;
    }
    public LocalDate getdate_of_approval() 
    {
        return date_of_approval;
    }
    public void setdate_of_approval(LocalDate date_of_approval) 
    {
        this.date_of_approval = date_of_approval;
    }
}

class sick_leave extends Request_Leave 
{
    public sick_leave(String name_of_employee, LocalDate date_of_request) 
    {
        super(name_of_employee, date_of_request);
    }
}

class casual_leave extends Request_Leave 
{
    private String reason_description;
    public casual_leave(String name_of_employee, LocalDate date_of_request, String reason_description) 
    {
        super(name_of_employee, date_of_request);
        this.reason_description = reason_description;
    }
    public String getreason_description() 
    {
        return reason_description;
    }
    public void setreason_description(String reason_description) 
    {
        this.reason_description = reason_description;
    }
}

class vacation_leave extends Request_Leave 
{
    private LocalDate starting_date;
    private LocalDate ending_date;
    public vacation_leave(String name_of_employee, LocalDate date_of_request, LocalDate starting_date, LocalDate ending_date) 
    {
        super(name_of_employee, date_of_request);
        this.starting_date = starting_date;
        this.ending_date = ending_date;
    }
    public LocalDate getstarting_date() 
    {
        return starting_date;
    }
    public LocalDate getending_date() 
    {
        return ending_date;
    }
}

interface leave_manager 
{
    void handleRequest(Request_Leave leave);
}

class tech_lead implements leave_manager 
{
    private leave_manager descendant;
    public tech_lead(leave_manager leavemanager) 
    {
        this.descendant = leavemanager;
    }
    @Override
    public void handleRequest(Request_Leave leave) 
    {
        if(leave instanceof sick_leave && leave.getleave_status().equals("New"))
        {
            leave.setleave_status("approved");
            leave.setapproval("Tech Lead");
            leave.setdate_of_approval(LocalDate.now());
            System.out.println("leave request got approved by Tech Lead for " + leave.getname_of_employee());
        }
        else
        {
            System.out.println("sending leave request to Program Manager from Tech Lead");
            this.descendant.handleRequest(leave);
        }
    }
}

class project_manager implements leave_manager 
{
    private leave_manager descendant;
    public project_manager(leave_manager leavemanager) 
    {
        this.descendant = leavemanager;
    }
    @Override
    public void handleRequest(Request_Leave leave) 
    {
        if(leave instanceof casual_leave && leave.getleave_status().equals("New"))
        {
            leave.setleave_status("approved");
            leave.setapproval("Project Manager");
            leave.setdate_of_approval(LocalDate.now());
            System.out.println("leave request got approved by Project Manager for " + leave.getname_of_employee());
        } 
        else
        {
            System.out.println("sending leave request to Director from Program Manager");
            this.descendant.handleRequest(leave);
        }
    }
}

class director implements leave_manager
{
    public void handleRequest(Request_Leave leave)
    {
        if(leave instanceof vacation_leave  && !leave.getleave_status().equals("approved"))
        {
            leave.setleave_status("approved");
            leave.setapproval("Director");
            leave.setdate_of_approval(LocalDate.now());
            System.out.println("leave request got approved by Director for "+leave.getname_of_employee());
        }
    }
}