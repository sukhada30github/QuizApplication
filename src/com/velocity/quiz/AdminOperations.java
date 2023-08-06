package com.velocity.quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminOperations extends UserOperations {
	Scanner scanner = new Scanner(System.in);
	Connection connection = null;
	PreparedStatement preparedStatement = null;

	public void admin_login() {
		final String adminId = "Admin";
		final String adminPass = "Password";

		System.out.println("\nPlease Enter Login Credentials");
		System.out.println("Admin Id :  ");
		String adID = scanner.next();
		System.out.println("Admin Password :  ");
		String adPass = scanner.next();

		if (adID.equals(adminId)) {
			if (adPass.equals(adminPass)) {

				choice();

			} else {
				System.out.println("Incorrect Password.. Try again!!");
				admin_login();
			}
		} else {
			System.out.println("Incorrect User Name.. Try again!!");
			admin_login();
		}
	}

	public void addQuestion() {

		scanner.nextLine();
		System.out.println("Enter Question:-  ");
//		scanner.next();
		String question = scanner.nextLine();

		System.out.println("Option 1: ");
		// scanner.next();
		String option1 = scanner.nextLine();

		System.out.println("Option 2: ");
		// scanner.next();
		String option2 = scanner.nextLine();

		System.out.println("Option 3 : ");
		// scanner.next();
		String option3 = scanner.nextLine();

		System.out.println("Option 4 : ");
		// scanner.next();
		String option4 = scanner.nextLine();

		System.out.println("Enter CorrectOption : ");
		String correctAnswer = scanner.nextLine();

		insertQuestion(question, option1, option2, option3, option4, correctAnswer);
	}

	public void insertQuestion(String question, String option1, String option2, String option3, String option4,
			String correctAnswer) {
		{
			connection = test.getconnectionDetails();
			try {
				preparedStatement = connection.prepareStatement(
						"insert into question(question,option1,option2,option3,option4,correctAnswer)values(?,?,?,?,?,?)");

				preparedStatement.setString(1, question);
				preparedStatement.setString(2, option1);
				preparedStatement.setString(3, option2);
				preparedStatement.setString(4, option3);
				preparedStatement.setString(5, option4);
				preparedStatement.setString(6, correctAnswer);
				preparedStatement.execute();

				System.out.println("\nRecord is inserted successfully..!!!");

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void choice() {
		int input = 0;

		System.out.println("\n Admin Login successfully " + "\n 1. Student Registration \n "
				+ "2. Add questions to Quiz \n " + "3. View Student Result \n" + " 4. Exit ");
		input = scanner.nextInt();

		switch (input) {
		case 1:
			System.out.println("How many Record you want to add");
			int number = scanner.nextInt();
			for (int i = 1; i <= number; i++) {
				System.out.println("Enter Details Of Record");
				addStudent();
			}
			choice();
			break;

		case 2:
			System.out.println("How many Questions you want to add");
			int no = scanner.nextInt();
			for (int i = 1; i <= no; i++) {
				addQuestion();
			}
			choice();
			break;

		case 3:
			displayResult();
			choice();
			break;

		case 4:
			System.out.println("Thank you..!!!");
			break;

		default:
			System.out.println("Invalid Input.. Please select between 1-4 options");
			choice();
			break;
		}

		frontPage();
	}

	public void viewResult() {
		connection = test.getconnectionDetails();
		try {
			preparedStatement = connection.prepareStatement("select userName,result,grade from result");
			preparedStatement.execute();
		} catch (Exception e) {
			try {
				connection.close();
				preparedStatement.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

		}
	}
}
