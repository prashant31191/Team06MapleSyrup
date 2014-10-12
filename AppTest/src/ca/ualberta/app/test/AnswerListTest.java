package ca.ualberta.app.test;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.app.Answer;
import ca.ualberta.app.AnswerList;
import junit.framework.TestCase;


public class AnswerListTest extends TestCase {
	public void testAnswerList(){
		AnswerList answerList = new AnswerList();
		List<Answer> ansList = answerList.getList();
		ArrayList<Answer> ansArrayList = answerList.getArrayList();
		assertTrue("Empty Answer List", ansList.size() == 0);
		assertTrue("Empty Answer Array List", ansArrayList.size() == 0);
	}
	
	public void testaddAnswer(){
		String answerString = "A answer";
		Answer answer = new Answer(answerString);
		AnswerList answerList = new AnswerList();
		answerList.addAnswer(answer);
		ArrayList<Answer> ansArrayList = answerList.getArrayList();
		assertTrue("Answer List Size", ansArrayList.size() == 1);
		assertTrue("Answer List contains answer", ansArrayList.contains(answer));
	}
	
	public void testremoveAnswer(){
		String answerString = "A answer";
		Answer answer = new Answer(answerString);
		AnswerList answerList = new AnswerList();
		answerList.addAnswer(answer);
		answerList.removeAnswer(0);
		ArrayList<Answer> ansArrayList = answerList.getArrayList();
		assertTrue("Answer List Size", ansArrayList.size() == 0);
	}
}