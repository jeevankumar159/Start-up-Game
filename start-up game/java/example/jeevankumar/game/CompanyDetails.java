package example.jeevankumar.game;

public class CompanyDetails {
    public String companyname;
    public long revenue;
    public long profit;
    public long evaluation;
    public long cash;
    public CompanyDetails(){

    }
    public  CompanyDetails(String companyname,long revenue,long profit,long evaluation,long cash){
        this.companyname=companyname;
        this.revenue=revenue;
        this.profit=profit;
        this.evaluation=evaluation;
        this.cash=cash;
    }
}
