package ca.ualberta.app.activity;

import java.util.ArrayList;
import java.util.Date;
import ca.ualberta.app.ESmanager.QuestionListManager;
import ca.ualberta.app.adapter.AnswerWaitingListAdapter;
import ca.ualberta.app.adapter.QuestionWaitingListAdapter;
import ca.ualberta.app.adapter.ReplyWaitingListAdapter;
import ca.ualberta.app.controller.AnswerListController;
import ca.ualberta.app.controller.PushController;
import ca.ualberta.app.controller.QuestionListController;
import ca.ualberta.app.controller.ReplyListController;
import ca.ualberta.app.models.Question;
import ca.ualberta.app.models.QuestionList;
import ca.ualberta.app.models.User;
import ca.ualberta.app.network.InternetConnectionChecker;
import ca.ualberta.app.view.ScrollListView;
import ca.ualberta.app.view.ScrollListView.IXListViewListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class WaitingListActivity extends Activity {

	private QuestionWaitingListAdapter questionAdapter;
	private AnswerWaitingListAdapter answerAdapter;
	private ReplyWaitingListAdapter replyAdapter;
	private QuestionListController waitingQuestionListController;
	private AnswerListController waitingAnswerListController;
	private ReplyListController waitingReplyListController;
	private QuestionListManager waitingQuestionListManager;
	private QuestionList waitingQuestionList;
	private PushController pushController;
	private Spinner typeOptionSpinner;
	private Context mcontext;
	private ArrayAdapter<String> spinAdapter;
	private static long categoryID;
	public String sortString = "Sort By Date";
	private Date timestamp;
	private ScrollListView mListView;
	private Handler mHandler;
	private String QuestionType;
	private String AnswerType;
	private String ReplyType;
	private ArrayList<String> typeOption;
	private Runnable doUpdateGUIList = new Runnable() {
		public void run() {
			questionAdapter.notifyDataSetChanged();
			answerAdapter.notifyDataSetChanged();
			replyAdapter.notifyDataSetChanged();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting_list);
		typeOptionSpinner = (Spinner) findViewById(R.id.waiting_List_type_spinner);
		mListView = (ScrollListView) findViewById(R.id.waiting_List_ListView);
		mListView.setPullLoadEnable(false);
		mHandler = new Handler();
		mcontext = this;
	}

	@Override
	public void onStart() {
		super.onStart();
		typeOption = new ArrayList<String>();
		pushController = new PushController(mcontext);
		waitingQuestionListController = new QuestionListController();
		waitingAnswerListController = new AnswerListController();
		waitingReplyListController = new ReplyListController();
		waitingQuestionListManager = new QuestionListManager();
		questionAdapter = new QuestionWaitingListAdapter(mcontext,
				R.layout.single_waiting_question,
				waitingQuestionListController.getQuestionArrayList());
		answerAdapter = new AnswerWaitingListAdapter(mcontext,
				R.layout.single_waiting_answer,
				waitingAnswerListController.getAnswerList());
		replyAdapter = new ReplyWaitingListAdapter(mcontext,
				R.layout.single_waiting_reply,
				waitingReplyListController.getReplyList());

		mListView.setAdapter(questionAdapter);

		updateCounter();
		spinAdapter = new ArrayAdapter<String>(mcontext, R.layout.spinner_item,
				typeOption);

		typeOptionSpinner.setAdapter(spinAdapter);
		typeOptionSpinner
				.setOnItemSelectedListener(new change_category_click());

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				if (categoryID == 0) {
					Intent intent = new Intent(mcontext,
							CreateQuestionActivity.class);
					intent.putExtra(CreateQuestionActivity.QUESTION_ID,
							waitingQuestionListController.getQuestion(pos - 1)
									.getID());
					intent.putExtra(CreateQuestionActivity.QUESTION_TITLE,
							waitingQuestionListController.getQuestion(pos - 1)
									.getTitle());
					intent.putExtra(CreateQuestionActivity.QUESTION_CONTENT,
							waitingQuestionListController.getQuestion(pos - 1)
									.getContent());
					startActivity(intent);
				} else if (categoryID == 1) {
					Intent intent = new Intent(mcontext,
							CreateAnswerActivity.class);
					intent.putExtra(CreateAnswerActivity.QUESTION_ID,
							waitingAnswerListController.getAnswer(pos - 1)
									.getQuestionID());
					intent.putExtra(CreateAnswerActivity.QUESTION_TITLE,
							waitingAnswerListController.getAnswer(pos - 1)
									.getQuestionTitle());
					intent.putExtra(CreateAnswerActivity.ANSWER_ID,
							waitingAnswerListController.getAnswer(pos - 1)
									.getID());
					intent.putExtra(CreateAnswerActivity.ANSWER_CONTENT,
							waitingAnswerListController.getAnswer(pos - 1)
									.getContent());
					intent.putExtra(CreateAnswerActivity.EDIT_MODE, true);
					startActivity(intent);
				} else if (categoryID == 2) {
					if (waitingReplyListController.getReply(pos).getAnswerID() == 0) {
						Intent intent = new Intent(mcontext,
								CreateQuestionReplyActivity.class);
						startActivity(intent);
					} else {
						Intent intent = new Intent(mcontext,
								CreateAnswerReplyActivity.class);
						startActivity(intent);
					}
				}
			}
		});

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question question = waitingQuestionListController
						.getQuestion(position - 1);
				if (User.author != null
						&& User.author.getUsername().equals(
								question.getAuthor())) {
					Toast.makeText(mcontext,
							"Deleting the Question: " + question.getTitle(),
							Toast.LENGTH_LONG).show();
					Thread thread = new DeleteThread(question.getID());
					thread.start();
				} else {
					Toast.makeText(mcontext,
							"Only Author to the Question can delete",
							Toast.LENGTH_LONG).show();
				}
				return true;
			}
		});

		mListView.setScrollListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						questionAdapter.notifyDataSetChanged();
						onLoad();
					}
				}, 2000);
			}

			@Override
			public void onLoadMore() {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						questionAdapter.notifyDataSetChanged();
						onLoad();
					}
				}, 2000);
			}
		});
	}

	private void onLoad() {
		timestamp = new Date();
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(timestamp.toString());
	}

	private void updateCounter() {
		typeOption.clear();
		QuestionType = "Questions (" + waitingQuestionListController.size()
				+ ")";
		AnswerType = "Answers (" + waitingAnswerListController.size() + ")";
		ReplyType = "Replies (" + waitingReplyListController.size() + ")";
		typeOption.add(QuestionType);
		typeOption.add(AnswerType);
		typeOption.add(ReplyType);
	}

	private void updateList(long categoryID) {
		if (InternetConnectionChecker.isNetworkAvailable(this)) {
			Thread thread = new postListThread();
			thread.start();
		} else {
			waitingQuestionListController.clear();
			waitingAnswerListController.clear();
			waitingReplyListController.clear();

			waitingQuestionList = pushController
					.getWaitingQuestionList(mcontext);
			waitingQuestionListController.addAll(waitingQuestionList);
			waitingAnswerListController.addAll(pushController
					.getWaitingAnswerList(mcontext));
			waitingReplyListController.addAll(pushController
					.getWaitingReplyList(mcontext));

			questionAdapter.notifyDataSetChanged();
			answerAdapter.notifyDataSetChanged();
			replyAdapter.notifyDataSetChanged();
		}
	}

	private class change_category_click implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			categoryID = position;
			// Question
			if (categoryID == 0) {
				mListView.setAdapter(questionAdapter);
			}

			// Answer
			if (categoryID == 1) {
				mListView.setAdapter(answerAdapter);
			}

			// Reply
			if (categoryID == 2) {
				mListView.setAdapter(replyAdapter);
			}
			updateList(categoryID);
			updateCounter();
			spinAdapter.notifyDataSetChanged();
		}

		public void onNothingSelected(AdapterView<?> parent) {
			typeOptionSpinner.setSelection(0);
		}
	}

	class postListThread extends Thread {

		@Override
		public void run() {
			waitingQuestionListController.clear();
			waitingAnswerListController.clear();
			waitingReplyListController.clear();

			waitingQuestionListManager.addQuestionList(pushController
					.getWaitingQuestionList(mcontext));
			waitingQuestionListManager.addAnswerList(pushController
					.getWaitingAnswerList(mcontext));
			waitingQuestionListManager.addReplyList(pushController
					.getWaitingReplyList(mcontext));

			pushController.removeWaitingListQuestionList(mcontext);
			pushController.removeWaitingListAnswerList(mcontext);
			pushController.removeWaitingListReplyList(mcontext);
			runOnUiThread(doUpdateGUIList);
		}
	}

	class DeleteThread extends Thread {
		private long questionID;

		public DeleteThread(long questionID) {
			this.questionID = questionID;
		}

		@Override
		public void run() {
			waitingQuestionListManager.deleteQuestion(questionID);
			// Remove movie from local list
			for (int i = 0; i < waitingQuestionListController.size(); i++) {
				Question q = waitingQuestionListController.getQuestion(i);
				if (q.getID() == questionID) {
					waitingQuestionListController.removeQuestion(i);
					break;
				}
			}
			runOnUiThread(doUpdateGUIList);
		}
	}

	/**
	 * Handle action bar item clicks here. The action bar will automatically
	 * handle clicks on the Home/Up button, so long as you specify a parent
	 * activity in AndroidManifest.xml.
	 * 
	 * @param item
	 *            The menu item.
	 * @return true if the item is selected.
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
