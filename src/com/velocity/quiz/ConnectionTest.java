package com.velocity.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ConnectionTest {
	Connection connection = null;
	PreparedStatement preparedStatement = null;

	public Connection getconnectionDetails() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_details", "root",
					"root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void createDatabase() {
		Connection connection=null;
		PreparedStatement pstatement =null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
			pstatement = connection.prepareStatement("create database if not exists students_details");
			pstatement.execute();

		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			try {
				connection.close();
				pstatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}

	
	public void createQuizTable() {
		
		try {
			connection = getconnectionDetails();
			String query = "create table if not exists question "
					+ "(questionNumber int primary key  auto_increment," + " question varchar(1000) not null,"
					+ "option1 varchar(1000) not null," + "option2 varchar(1000) not null,"
					+ "option3 varchar(1000) not null," + "option4 varchar(1000) not null," + "correctAnswer int(50))";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
			System.out.println("Question Table has been created");
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertQuestionsInDataBase() {
		connection = getconnectionDetails();
		String query=" insert into question (question,option1,option2,option3,option4,correctAnswer) values ('Who invented Java Programming?',' Guido van Rossum',' James Gosling',' Dennis Ritchie',' Bjarne Stroustrup',2),"+"('Which statement is true about Java?',' Java is a sequence-dependent programming language',' Java is a code dependent programming language',' Java is a platform-dependent programming language',' Java is a platform-independent programming language',2),"+
				"('Which component is used to compile, debug and execute the java programs?',' JRE',' JIT',' JDK',' JVM',3),"+
				"('Which one of the following is not a Java feature?',' Object-oriented',' Use of pointers',' Portable',' Dynamic and Extensible',2),"+
				"('Which of these cannot be used for a variable name in Java?',' identifier & keyword',' identifier',' keyword',' none of the mentioned',3),"+
				"('What is the extension of java code files?',' .js',' .txt',' .class',' .java',4),"+
				"('Which of the following is not an OOPS concept in Java?',' Polymorphism',' Inheritance',' Compilation',' Encapsulation',3),"+
				"('What is not the use of \"this\" keyword in Java?',' Referring to the instance variable when a local variable has the same name',' Passing itself to the method of the same class',' Passing itself to another method',' Calling another constructor in constructor chaining',3),"+
				"('Which of the following is a type of polymorphism in Java Programming?',' Multiple polymorphism',' Compile time polymorphism',' Multilevel polymorphism',' Execution time polymorphism',2),"+
				"('Which of the following is a superclass of every class in Java?','ArrayList','Abstract class','Object class','String',3)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			System.out.println("Questions inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				connection.close();
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void createStudentTable() {
		try {
			connection = getconnectionDetails();

			String query = "create table if not exists student" + "(Id int primary key  auto_increment,"
					+ " firstName varchar(50) not null," + "lastName varchar(50) not null,"
					+ "userName varchar(50) not null unique," + "password varchar(50) not null,"
					+ "city varchar(50) not null," + "mailId varchar(50) not null unique," + "PhoneNo long not null)";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.executeUpdate();
			System.out.println("Database and Table has been creted..!!");
			preparedStatement.close();
			connection.close();

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

	
	public void createResultTable() {
		connection = getconnectionDetails();

		String query = "create table if not exists result" + "(ResultId int primary key auto_increment,"+"Id int,"
				+ "userName varchar(50) not null unique," + "result int(50) not null, "+"grade varchar(100)not null,"+"CONSTRAINT FK_result FOREIGN KEY (ResultId)" + 
						" REFERENCES student(Id))";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
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


