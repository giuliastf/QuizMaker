package com.example.project;

import java.io.File;

public class Tema1 {
	//static int answerID = 0;
	public static int CleanupAll(){
		File u = new File("users.txt");
		File q = new File("questions.txt");
		File a = new File("answers.txt");
		File qz = new File("quizzes.txt");
		File scores = new File("scores.txt");
		Question.questionID = Quizz.answerID = Quizz.QuizzID=1;
		if(u.delete() && q.delete() && a.delete() && qz.delete() && scores.delete()) {
			return 1; // a mersSystem.out.print("{ 'status' : 'ok', 'message' : 'Cleanup finished successfully'}");
		}
		return 0;
	}
	public static void main(final String[] args) {
		if (args == null)
			System.out.print("Hello world!");
		else if (args[0].equals("-create-user"))
			User.CreateUser(args);
		else if(args[0].equals("-cleanup-all")) {
			if (CleanupAll() == 1)
				System.out.print("{ 'status' : 'ok', 'message' : 'Cleanup finished successfully'}");
		} else if(args[0].equals("-create-question"))
			Question.CreateQuestion(args);
		else if(args[0].equals("-get-question-id-by-text"))
			Question.GetQuestionIdByText(args);
		else if(args[0].equals("-get-all-questions"))
			Question.GetAllQuestions(args);
		else if(args[0].equals("-create-quizz"))
			Quizz.CreateQuizz(args);
		else if(args[0].equals("-get-quizz-by-name"))
			Quizz.GetQuizzByName(args);
		else if(args[0].equals("-get-all-quizzes"))
			Quizz.GetAllQuizzes(args);
		else if(args[0].equals("-get-quizz-details-by-id"))
			Quizz.GetQuizzDetails(args);
		else if(args[0].equals("-submit-quizz"))
			Quizz.SubmitQuizz(args);
		else if(args[0].equals("-delete-quizz-by-id"))
			Quizz.DeleteQuizz(args);
		else if(args[0].equals("-get-my-solutions"))
			Quizz.GetMySolutions(args);
	}
}
