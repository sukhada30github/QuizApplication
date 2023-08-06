package com.velocity.quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class UserOperations {
	
	Scanner scanner = new Scanner(System.in);
	ConnectionTest test = new ConnectionTest();
	Connection con = null;
	PreparedStatement prs = null;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String city;
	private String emailId;
	private long phoneNo;

	public void addStudent() {
		con = test.getconnectionDetails();

		System.out.println("Enter First Name : ");
		this.firstName = scanner.next();

		System.out.println("Enter Last Name : ");
		this.lastName = scanner.next();

		System.out.println("Enter User Name : ");
		this.userName = scanner.next();

		try {
			ArrayList<String> listName = new ArrayList<String>();
			prs = con.prepareStatement("select userName from student");
			ResultSet resultset = prs.executeQuery();
			while (resultset.next()) {
				String a = resultset.getString("userName");
				listName.add(a);
			}
			while (listName.contains(userName) == true) {
				System.out.println("User Name Already Exists...\n Please Try Another User name");
				System.out.println("Enter User Name : ");
				this.userName = scanner.next();
			}
			System.out.println("Enter Password : ");
			this.password = scanner.next();

			System.out.println("Enter City :  ");
			this.city = scanner.next();

			System.out.println("Enter Mail Id :  ");
			this.emailId = scanner.next();

			System.out.println("Enter Phone No : ");
			this.phoneNo = scanner.nextLong();

		} catch (Exception e) {
			System.out.println("Please enter numbers only");
			System.out.println("Enter Phone No : ");
			this.phoneNo = scanner.nextLong();
		} finally {
			try {
				con.close();
				prs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		insertStudent(firstName, lastName, userName, password, city, emailId, phoneNo);
	}

	public void insertStudent(String firstName, String lastName, String userName, String password, String city,
			String emailId, long phoneNo) {

		try {
			con = test.getconnectionDetails();
			prs = con.prepareStatement(
					"insert into student(firstName,lastName,userName,password,city,mailId,phoneNo)values(?,?,?,?,?,?,?)");

			prs.setString(1, firstName);
			prs.setString(2, lastName);
			prs.setString(3, userName);
			prs.setString(4, password);
			prs.setString(5, city);
			prs.setString(6, emailId);
			prs.setLong(7, phoneNo);

			prs.execute();

			System.out.println("\nRecord inserted successfully..!!!");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				prs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void userLoginPage() {
		ArrayList<String> listName = new ArrayList<String>();
		ArrayList<String> listPass = new ArrayList<String>();
		try {
			con = test.getconnectionDetails();
			prs = con.prepareStatement("select userName, password from student");

			ResultSet resultset = prs.executeQuery();
			while (resultset.next()) {
				String a = resultset.getString("userName");
				String pass = resultset.getString("password");

				listName.add(a);
				listPass.add(pass);//
			}

			System.out.println("Username: ");
			String name = scanner.next();
			System.out.println("Password:");
			String password = scanner.next();

			if (listName.contains(name) == true) {
				if (listPass.contains(password) == true) {
					System.out.println("Login successfully..!!" + "\nWecome " + name + "...!!!");
					userOperations(name);
				} else {
					System.out.println("Invalid password");
					userLoginPage();

				}
			} else {
				System.out.println("Invalid username");
				userLoginPage();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				prs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void frontPage() {
		System.out.println("\nWelcome to Quiz based application\n");

		System.out.println("1. Admin ");
		System.out.println("2. Student ");
		int input = scanner.nextInt();
		if (input == 2) {
			userInterface();
		} else if (input == 1) {
			AdminOperations admin = new AdminOperations();
			admin.admin_login();
		}
	}

	public void userInterface() {

		System.out.println(
				"\n Welcome To Student Portal " + "\n 1. Student Registration \n " + "2. Log In \n " + "3. Exit ");

		int choice = scanner.nextInt();

		switch (choice) {

		case 1:
			addStudent();
			userInterface();
			break;

		case 2:
			userLoginPage();
			userInterface();
			break;

		case 3:
			System.out.println("Thank you..!!!");
			break;

		default:
			System.out.println("Invalid Input.. Please select between 1-4 options");
			userInterface();
			break;
		}
	}

	public void startQuiz(String name) {
		int count = 0;
		try {
			con = test.getconnectionDetails();
			prs = con.prepareStatement(
					"select question,option1,option2,option3,option4,correctAnswer from question order by rand() ");

			ResultSet resultset = prs.executeQuery();
			int i = 0;
			while (resultset.next()) {
				i++;
				System.out.println("Question:" + i + "  " + resultset.getString("question"));
				System.out.println("Option 1:" + resultset.getString("option1"));
				System.out.println("Option 2: " + resultset.getString("option2"));
				System.out.println("Option 3: " + resultset.getString("option3"));
				System.out.println("Option 4: " + resultset.getString("option4"));
				int dataBaseAns = resultset.getInt("correctAnswer");
				System.out.println("Choose Correct Option:  ");
				int userInput = scanner.nextInt();

				int a = Integer.compare(dataBaseAns, userInput);

				if (a == 0) {
					count++;
					System.out.println("Correct");
				} else {
					System.out.println("Incorrect");
				}
				System.out.println("Correct Answer: " + dataBaseAns);
				System.out.println("=====================\n");
				if (i == 10)
					break;
			}
			insertResult(count, name);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				prs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void insertResult(int count, String name) {
		String grade = null;
		System.out.println("Your Score is : " + count + " Out Of 10 ");
		if (count >= 8 && count <= 10) {
			grade = "A";
			System.out.println("Grade A");
		} else if (count >= 6 && count <= 8) {
			grade = "B";
			System.out.println("Grade B");
		} else if (count >= 3 && count <= 5) {
			grade = "C";
			System.out.println("Grade C");
		} else if (count >= 1 && count <= 2) {
			grade = "D";
			System.out.println("Grade D");
		} else {
			grade = "F";
			System.out.println("Grade F");

		}

		try {
			con = test.getconnectionDetails();

			prs = con.prepareStatement("insert into result (userName,result,grade) values (?,?,?)");
			prs.setString(1, name);
			prs.setInt(2, count);
			prs.setString(3, grade);
			prs.execute();
			System.out.println("Result Stored Successfully..!!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void displayResult() {

		try {
			con = test.getconnectionDetails();
			prs = con.prepareStatement("select userName,result,grade from result");
			prs.execute();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				prs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void userOperations(String name) {
		System.out.println("\n 1. Quiz \n 2. Exit  ");
		int choice = scanner.nextInt();
		switch (choice) {

		case 1:
			startQuiz(name);
			userOperations(name);
			break;

		case 2:
			System.out.println("Thank you..!!!");
			break;

		default:
			System.out.println("Invalid Input.. Please select between 1-4 options");
			break;
		}

		frontPage();

	}

	@Override
	public String toString() {
		return "UserInput [firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName + ", password="
				+ password + ", city=" + city + ", emailId=" + emailId + ", phoneNo=" + phoneNo + "]";
	}
public static void main(String[] args) 
{
	UserOperations us = new UserOperations();
	us.displayResult();
}
}
