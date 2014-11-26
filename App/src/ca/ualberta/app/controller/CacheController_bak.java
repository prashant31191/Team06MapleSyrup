///*
// * Copyright 2014 Anni Dai
// * Copyright 2014 Bicheng Yan
// * Copyright 2014 Liwen Chen
// * Copyright 2014 Liang Jingjing
// * Copyright 2014 Xiaocong Zhou
// * 
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * 
// *     http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package ca.ualberta.app.controller;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.content.Context;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import ca.ualberta.app.models.Question;
//import ca.ualberta.app.models.QuestionList;
//
///**
// * load question map from local file
// */
//public class CacheController {
//	public Map<Long, Question> favoriteMap;
//	public Map<Long, Question> localCacheMap;
//	public Map<Long, Question> waitingListMap;
//	public ArrayList<Long> favoriteId;
//	public ArrayList<Long> localCacheId;
//	public ArrayList<Long> waitingListId;
//	private String FAVMAP = "favMap.sav";
//	private String LOCALMAP = "localMap.sav";
//	private String WAITMAP = "waitMap.sav";
//	private String FAVID = "favId.sav";
//	private String LOCALID = "localId.sav";
//	private String WAITID = "waitId.sav";
//
//	/**
//	 * The constructor of the class load question map from local file
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 */
//	public CacheController(Context mcontext) {
//		favoriteMap = loadMapFromFile(mcontext, FAVMAP);
//		localCacheMap = loadMapFromFile(mcontext, LOCALMAP);
//		waitingListMap = loadMapFromFile(mcontext, WAITMAP);
//		favoriteId = loadIdFromFile(mcontext, FAVID);
//		localCacheId = loadIdFromFile(mcontext, LOCALID);
//		waitingListId = loadIdFromFile(mcontext, WAITID);
//	}
//
//	/**
//	 * Return the question map of the favorite question from local file
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * 
//	 * @return the question map of the favorite question from the local file.
//	 */
//	public Map<Long, Question> getFavoriteMap(Context mcontext) {
//		favoriteMap = loadMapFromFile(mcontext, FAVMAP);
//		return favoriteMap;
//	}
//
//	/**
//	 * Return the question map of the local question from local file
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * 
//	 * @return the question map of the local question from the local file.
//	 */
//	public Map<Long, Question> getLocalCacheMap(Context mcontext) {
//		localCacheMap = loadMapFromFile(mcontext, LOCALMAP);
//		return localCacheMap;
//
//	}
//
//	/**
//	 * Return the question map of the waitingList question from local file
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * 
//	 * @return the question map of the waitingList question from the local file.
//	 */
//	public Map<Long, Question> getWaitingListMap(Context mcontext) {
//		waitingListMap = loadMapFromFile(mcontext, WAITMAP);
//		return waitingListMap;
//
//	}
//
//	/**
//	 * Return the question ID of the favorite question from local file
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * 
//	 * @return the question ID of the favorite question from the local file.
//	 */
//	public ArrayList<Long> getFavoriteId(Context mcontext) {
//		favoriteId = loadIdFromFile(mcontext, FAVID);
//		return favoriteId;
//
//	}
//
//	/**
//	 * Return the question ID of the local question from local file
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * 
//	 * @return the question ID of the local question from the local file.
//	 */
//	public ArrayList<Long> getLocalCacheId(Context mcontext) {
//		localCacheId = loadIdFromFile(mcontext, LOCALID);
//		return localCacheId;
//
//	}
//
//	/**
//	 * Return the question ID of the waitingList question from local file
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * 
//	 * @return the question ID of the waitingList question from the local file.
//	 */
//	public ArrayList<Long> getWaitingListId(Context mcontext) {
//		waitingListId = loadIdFromFile(mcontext, WAITID);
//		return waitingListId;
//
//	}
//	/**
//	 * Return whether the local file for favorite question contain the question.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 * 
//	 * @return true if the local file for favorite question has the question,
//	 *         false if not.
//	 */
//	public boolean hasFavorited(Context mcontext, Question question) {
//		favoriteMap = loadMapFromFile(mcontext, FAVMAP);
//		if (favoriteMap.get(question.getID()) == null)
//			return false;
//		return true;
//	}
//
//	/**
//	 * Return whether the local file for local question contain the question.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 * 
//	 * @return true if the local file for favorite question has the question,
//	 *         false if not.
//	 */
//	public boolean hasSaved(Context mcontext, Question question) {
//		localCacheMap = loadMapFromFile(mcontext, LOCALMAP);
//		if (localCacheMap.get(question.getID()) == null)
//			return false;
//		return true;
//	}
//
//	/**
//	 * Return whether the local file for local question contain the question.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 * 
//	 * @return true if the local file for favorite question has the question,
//	 *         false if not.
//	 */
//	public boolean hasWaited(Context mcontext, Question question) {
//		waitingListMap = loadMapFromFile(mcontext, WAITMAP);
//		if (waitingListMap.get(question.getID()) == null)
//			return false;
//		return true;
//	}
//	
//	/**
//	 * Save a question into the the local file of the favorite questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void addFavQuestions(Context mcontext, Question question) {
//		favoriteMap = loadMapFromFile(mcontext, FAVMAP);
//		favoriteId = loadIdFromFile(mcontext, FAVID);
//		if (!hasFavorited(mcontext, question)) {
//			favoriteMap.put(question.getID(), question);
//			favoriteId.add(question.getID());
//			saveInFile(mcontext, favoriteMap, FAVMAP);
//			saveInFile(mcontext, favoriteId, FAVID);
//		}
//	}
//
//	/**
//	 * Save a question into the the local file of the local questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void addLocalQuestions(Context mcontext, Question question) {
//		localCacheMap = loadMapFromFile(mcontext, LOCALMAP);
//		localCacheId = loadIdFromFile(mcontext, LOCALID);
//		if (!hasSaved(mcontext, question)) {
//			localCacheMap.put(question.getID(), question);
//			localCacheId.add(question.getID());
//			saveInFile(mcontext, localCacheMap, LOCALMAP);
//			saveInFile(mcontext, localCacheId, LOCALID);
//		}
//	}
//
//	/**
//	 * Save a question into the the local file of the WaitingList questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void addWaitngListQuestions(Context mcontext, Question question) {
//		waitingListMap = loadMapFromFile(mcontext, WAITMAP);
//		waitingListId = loadIdFromFile(mcontext, WAITID);
//		if (!hasWaited(mcontext, question)) {
//			waitingListMap.put(question.getID(), question);
//			waitingListId.add(question.getID());
//			saveInFile(mcontext, waitingListMap, WAITMAP);
//			saveInFile(mcontext, waitingListId, WAITID);
//		}
//	}
//	
//	/**
//	 * Remove a question into the the local file of the favorite questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void removeFavQuestions(Context mcontext, Question question) {
//		favoriteMap = loadMapFromFile(mcontext, FAVMAP);
//		favoriteId = loadIdFromFile(mcontext, FAVID);
//		favoriteMap.remove(question.getID());
//		for (int i = 0; i < favoriteId.size(); i++) {
//			if (favoriteId.get(i) == question.getID()) {
//				favoriteId.remove(i);
//				break;
//			}
//		}
//		saveInFile(mcontext, favoriteMap, FAVMAP);
//		saveInFile(mcontext, favoriteId, FAVID);
//	}
//
//	/**
//	 * Remove a question into the the local file of the local questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void removeLocalQuestions(Context mcontext, Question question) {
//		localCacheMap = loadMapFromFile(mcontext, LOCALMAP);
//		localCacheId = loadIdFromFile(mcontext, LOCALID);
//		localCacheMap.remove(question.getID());
//		for (int i = 0; i < localCacheId.size(); i++) {
//			if (localCacheId.get(i) == question.getID()) {
//				localCacheId.remove(i);
//				break;
//			}
//		}
//		saveInFile(mcontext, localCacheMap, LOCALMAP);
//		saveInFile(mcontext, localCacheId, LOCALID);
//	}
//
//	/**
//	 * Remove a question into the the local file of the WaitingList questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void removeWaitingListQuestions(Context mcontext, Question question) {
//		waitingListMap = loadMapFromFile(mcontext, WAITMAP);
//		waitingListId = loadIdFromFile(mcontext, WAITID);
//		waitingListMap.remove(question.getID());
//		for (int i = 0; i < waitingListId.size(); i++) {
//			if (waitingListId.get(i) == question.getID()) {
//				waitingListId.remove(i);
//				break;
//			}
//		}
//		saveInFile(mcontext, waitingListMap, WAITMAP);
//		saveInFile(mcontext, waitingListId, WAITID);
//	}
//	
//	/**
//	 * Update a question in the the local file of the favorite questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void updateFavQuestions(Context mcontext, Question question) {
//		favoriteMap = loadMapFromFile(mcontext, FAVMAP);
//		if (favoriteMap.get(question.getID()) != null) {
//			favoriteMap.remove(question.getID());
//			favoriteMap.put(question.getID(), question);
//			saveInFile(mcontext, favoriteMap, FAVMAP);
//		}
//	}
//	
//	/**
//	 * Update a question in the the local file of the local questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void updateLocalQuestions(Context mcontext, Question question) {
//		localCacheMap = loadMapFromFile(mcontext, LOCALMAP);
//		if (localCacheMap.get(question.getID()) != null) {
//			localCacheMap.remove(question.getID());
//			localCacheMap.put(question.getID(), question);
//			saveInFile(mcontext, localCacheMap, LOCALMAP);
//		}
//	}
//
//	/**
//	 * Update a question in the the local file of the WaitingList questions.
//	 * 
//	 * @param mcontext
//	 *            the context.
//	 * @param question
//	 *            the question.
//	 */
//	public void updateWaitingListQuestions(Context mcontext, Question question) {
//		waitingListMap = loadMapFromFile(mcontext, WAITMAP);
//		if (waitingListMap.get(question.getID()) != null) {
//			waitingListMap.remove(question.getID());
//			waitingListMap.put(question.getID(), question);
//			saveInFile(mcontext, waitingListMap, WAITMAP);
//		}
//	}
//	
//	/**
//	 * Load and return the question list in the the local file of the favorite
//	 * questions.
//	 * 
//	 * @return the favorite question list.
//	 */
//	public QuestionList getFavoriteQuestionList(Context mcontext) {
//		favoriteMap = loadMapFromFile(mcontext, FAVMAP);
//		QuestionList questionList = new QuestionList();
//		questionList.getCollection().addAll(this.favoriteMap.values());
//		return questionList;
//	}
//	
//	/**
//	 * Load and return the local question list
//	 * 
//	 * @return the local question list.
//	 */
//	public QuestionList getLocalQuestionsList(Context mcontext) {
//		localCacheMap = loadMapFromFile(mcontext, LOCALMAP);
//		QuestionList questionList = new QuestionList();
//		questionList.getCollection().addAll(this.localCacheMap.values());
//		return questionList;
//	}
//	
//	/**
//	 * Load and return the WaitingList question list
//	 * 
//	 * @return the local question list.
//	 */
//	public QuestionList getWaitingQuestionList(Context mcontext) {
//		waitingListMap = loadMapFromFile(mcontext, WAITMAP);
//		QuestionList questionList = new QuestionList();
//		questionList.getCollection().addAll(this.waitingListMap.values());
//		return questionList;
//	}
//	
//	/**
//	 * Clear all MAP's in the local files
//	 */
//	public void clear() {
//		favoriteMap.clear();
//		localCacheMap.clear();
//		waitingListMap.clear();
//	}
//
//	/**
//	 * Save all MAP's into the local files
//	 * 
//	 * @param mcontext
//	 *            The context.
//	 * @param tempFav
//	 *            the buffer of the Map of the favorite question and it's ID.
//	 * @param tempSav
//	 *            the buffer of the Map of the local question and it's ID.
//	 */
//	public void addAll(Context mcontext, Map<Long, Question> tempFav,
//			Map<Long, Question> tempSav, Map<Long,Question> tempWait) {
//		favoriteMap.putAll(tempFav);
//		localCacheMap.putAll(tempSav);
//		waitingListMap.putAll(tempWait);
//		saveInFile(mcontext, favoriteMap, FAVMAP);
//		saveInFile(mcontext, localCacheMap, LOCALMAP);
//		saveInFile(mcontext, waitingListMap, WAITMAP);
//	}
//
//
//
//	/**
//	 * Load the question ID's from the file with given name.
//	 * 
//	 * @param context
//	 *            The context.
//	 * @param FILENAME
//	 *            The name of the local file.
//	 * 
//	 * @return the ID of the question(s).
//	 */
//	public ArrayList<Long> loadIdFromFile(Context context, String FILENAME) {
//		ArrayList<Long> questionId = null;
//		try {
//			FileInputStream fis = context.openFileInput(FILENAME);
//			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//			Gson gson = new Gson();
//			// Following line from
//			// https://sites.google.com/site/gson/gson-user-guide 2014-09-23
//			Type listType = new TypeToken<ArrayList<Long>>() {
//			}.getType();
//			questionId = gson.fromJson(in, listType);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		if (questionId == null)
//			return questionId = new ArrayList<Long>();
//		return questionId;
//	}
//
//	/**
//	 * Load the question Map's from the file with given name.
//	 * 
//	 * @param context
//	 *            The context.
//	 * @param FILENAME
//	 *            The name of the local file.
//	 * 
//	 * @return the Map of the question(s).
//	 */
//	public Map<Long, Question> loadMapFromFile(Context context, String FILENAME) {
//		Map<Long, Question> questionMap = null;
//		try {
//			FileInputStream fis = context.openFileInput(FILENAME);
//			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//			Gson gson = new Gson();
//			// Following line from
//			// https://sites.google.com/site/gson/gson-user-guide 2014-09-23
//			Type listType = new TypeToken<Map<Long, Question>>() {
//			}.getType();
//			questionMap = gson.fromJson(in, listType);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		if (questionMap == null)
//			return questionMap = new HashMap<Long, Question>();
//		return questionMap;
//	}
//
//	/**
//	 * save question map to local file
//	 * 
//	 * @param context
//	 *            The context.
//	 * @param questionMap
//	 *            The question map.
//	 * @param FILENAME
//	 *            The name of the file.
//	 */
//	public void saveInFile(Context context, Map<Long, Question> questionMap,
//			String FILENAME) {
//		try {
//			FileOutputStream fos = context.openFileOutput(FILENAME, 0);
//			Gson gson = new Gson();
//			OutputStreamWriter osw = new OutputStreamWriter(fos);
//			gson.toJson(questionMap, osw);
//			osw.flush();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * save question id to local file
//	 * 
//	 * @param context
//	 *            The context.
//	 * @param questionMap
//	 *            The question id.
//	 * @param FILENAME
//	 *            The name of the file.
//	 */
//	public void saveInFile(Context context, ArrayList<Long> questionId,
//			String FILENAME) {
//		try {
//			FileOutputStream fos = context.openFileOutput(FILENAME, 0);
//			Gson gson = new Gson();
//			OutputStreamWriter osw = new OutputStreamWriter(fos);
//			gson.toJson(questionId, osw);
//			osw.flush();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//}