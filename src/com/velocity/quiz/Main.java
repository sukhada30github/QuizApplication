package com.velocity.quiz;

public class Main {
	public static void sequence() {
		ConnectionTest test = new ConnectionTest();
		UserOperations user = new UserOperations();
		test.createDatabase();
		test.getconnectionDetails();
		test.createQuizTable();
		test.insertQuestionsInDataBase();
		test.createStudentTable();
		test.createResultTable();
		user.frontPage();

	}

	public static void main(String[] args) {
		sequence();
	}
}
