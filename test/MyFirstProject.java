import java.util.Scanner;
public class MyFirstProject
{
	void main()
	{

	Scanner sc=new Scanner(System.in);
	System.out.print("Enter Your Name:");
	String name = sc.nextLine();
	System.out.print("Enter Your Roll:");
	int r=sc.nextInt();
	System.out.println("Welcome "+name);
	}
}
