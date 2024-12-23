import java.util.*;
import java.time.*;
import java.time.format.*;

class info{
    public String Time;
    public String Date;
    public int amount = 0;

    info(String tm,String dt,int mnt){
        this.Date = dt;
        this.Time = tm;
        this.amount = mnt;
    }
}

class Account{
    public String userName;
    public String ID;
    public String BirthDate;
    private String password;
    public String NID;
    public int amount;
    public ArrayList<info> depositHistory = new ArrayList<>();
    public ArrayList<info> withdrawHistory = new ArrayList<>();

    Account(String userName,String Id,String BirthDate,String NID,String pas){
        this.BirthDate = BirthDate;
        this.ID = Id;
        this.NID = NID;
        this.userName = userName;
        this.password = pas;
    }


    String getPassword(){
        return this.password;
    }

    void cngPassword(String s){
        this.password = s;
    }

    void printHistory(ArrayList<info> a,String s){
        System.out.println("Here is your "+ s + " history: ");
        int k=1;
        for(info i:a){
            System.out.println("\nNumber "+ k++ +": ");
            System.out.println(s+": "+i.amount);
            System.out.println("Date: "+i.Date);
            System.out.println("Time: "+i.Time);
        }
    }

    void Info(){
        System.out.println("1-> Your User-Name: "+this.userName);
        System.out.println("2-> Your account ID-Number: "+this.ID);
        System.out.println("3-> Your Birth-Date: "+this.BirthDate);
        System.out.println("4-> Your NID-Number: "+this.NID);
    }
}


class Banking_System{

    HashMap<String,Account> store = new HashMap<>();
    Scanner take = new Scanner(System.in);

    public void wellcome(){
        System.out.println("\nWellcome to our ATM System.");
        System.out.println("1. Balance Check.");
        System.out.println("2. Cash Withdraw.");
        System.out.println("3. Cash Deposit.");
        System.out.println("4. Fund Transfer.");
        System.out.println("5. About Your Account.");
        System.out.println("6. Logout.");
        System.out.print("Enter your Choice: ");
    }

    public void varify(String id,int k){
        if(k==0)
        return;
        if(store.containsKey(id)){
            System.out.print("Enter your Password: ");
            String pass = take.nextLine();
            if(store.get(id).getPassword().equals(pass)){
                Open(id);
                return;
            }
            else {
                System.out.println("Password is wrong.");
                varify(id,k-1);
            }
        }
        else {
            System.out.println("Invalid ID.");
            return;
        }
    }

    public void Open(String Id){
        while(true){
            wellcome();
            int n = take.nextInt();
            if(n==1){
                balanceChack(store.get(Id));
            }
            else if(n==2){
                withdrawMoney(store.get(Id));
            }
            else if(n==3){
                addMoney(store.get(Id));
            }
            else if(n==4){
                fundTrunsfer(store.get(Id));
            }
            else if(n==5){
                aboutAccount(store.get(Id));
            }
            else if(n==6){
                break;
            }
        }
    }

    public void balanceChack(Account ac){
        System.out.println("Here is your current amount(BDT): "+ac.amount+" TK.");
        System.out.println("Do your want to check your More: ");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
        int n = take.nextInt();
        take.nextLine();
        if(n==1){
            System.out.println("1-> Withdraw History.");
            System.out.println("2-> Deposit History.");
            System.out.print("Enter your choice: ");
            n = take.nextInt();
            if(n==1){
                ac.printHistory(ac.withdrawHistory, "Withdraw");
            }
            else if(n==2){
                ac.printHistory(ac.depositHistory, "Deposit");
            }
            else if(n==0){
                Open(ac.ID);
            }
            else {
                System.out.println("Please enter a valid number.");
                balanceChack(ac);
            }
        }
    }

    public void addMoney(Account ac){
        System.out.print("Please enter your amount: ");
        int tk = take.nextInt();
        ac.amount += tk;
        System.out.println("Add your amount successfully.\n");
        LocalTime tm = LocalTime.now();
        LocalDate dt = LocalDate.now();

        DateTimeFormatter ftm = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter fdt = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

        String ntm = tm.format(ftm);
        String ndt = dt.format(fdt);

        ac.depositHistory.add(new info(ntm, ndt, tk));

    }

    public void withdrawMoney(Account ac){
        System.out.print("Please enter your amount: ");
        int tk = take.nextInt();
        ac.amount = ac.amount-tk;
        System.out.println("Here is your money.\n");       
        LocalTime tm = LocalTime.now();
        LocalDate dt = LocalDate.now();

        DateTimeFormatter ftm = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter fdt = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

        String ntm = tm.format(ftm);
        String ndt = dt.format(fdt);

        ac.withdrawHistory.add(new info(ntm, ndt, tk));
    }

    public void fundTrunsfer(Account ac){
        System.out.print("Please enter your amount: ");
        int tk = take.nextInt();
        ac.amount -= tk;
        System.out.println("Transfer your amount successfully.\n");
        LocalTime tm = LocalTime.now();
        LocalDate dt = LocalDate.now();

        DateTimeFormatter ftm = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter fdt = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

        String ntm = tm.format(ftm);
        String ndt = dt.format(fdt);

        ac.withdrawHistory.add(new info(ntm, ndt, tk));        

    }

    public void aboutAccount(Account ac){
        System.out.println("\nHere is your account Information: ");
        ac.Info();
    }


    public void createAccount(){
        System.out.println("Please enter your information.\n");
        System.out.println("Enter your Name: ");
        String name = take.nextLine();
        System.out.println("Enter your ID: ");
        String id = take.nextLine();
        System.out.println("Enter your Birth Date(xx-xx-xxxx): ");
        String date = take.nextLine();
        System.out.println("Enter your NID Number: ");
        String nid = take.nextLine();
        System.out.println("Enter your Password: ");    
        String pswrd = take.nextLine();
        store.put(id,new Account(name, id, date, nid,pswrd));
        // store.get(id).putPassword(pswrd);
        }
}



public class ATM {
    public static void main(String[] args) {
        Scanner take = new Scanner(System.in);
        Banking_System bnk = new Banking_System();
        while(true){
            System.out.println("Do you have any account.");
            System.out.println("1. Insert your card.");
            System.out.println("2. Create an account.");
            System.out.println("3. Exit.\n");
            System.out.print("Enter your choice: ");
            int n = take.nextInt();
            take.nextLine();
            if(n==1){
                System.out.print("Please enter your ID Number: ");
                String id = take.nextLine();
                bnk.varify(id, 4);
            }
            else if(n==2){
                bnk.createAccount();
            }
            else if(n==3){
                break;
            }
            else {
                System.out.println("Enter a valid number.\n");
            }
        }
    }
}