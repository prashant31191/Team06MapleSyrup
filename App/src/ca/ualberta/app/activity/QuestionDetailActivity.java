package ca.ualberta.app.activity;

import ca.ualberta.app.models.Question;
import ca.ualberta.app.models.QuestionListManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionDetailActivity extends Activity {

	public static String QUESTION_ID = "QUESTION_ID";
	// private View detail;
	// private ListView listv;
	private QuestionListManager questionManager;
	private Question question;

	private Runnable doUpdateGUIDetails = new Runnable() {
		public void run() {
			// TextView title = (TextView) findViewById(R.id.detailsTitle);
			// TextView director = (TextView)
			// findViewById(R.id.detailsDirector);
			// TextView year = (TextView) findViewById(R.id.detailsYear);
			// //TextView genre = (TextView) findViewById(R.id.detailsGenre);
			//
			// title.setText(movie.getTitle());
			// director.setText(movie.getDirector());
			// year.setText(String.valueOf(movie.getYear()));
			// genre.setText(movie.getGenres().toString());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_detail);
	}

	@Override
	protected void onStart() {
		super.onStart();

		questionManager = new QuestionListManager();
		Intent intent = getIntent();

		if (intent != null) {
			Bundle extras = intent.getExtras();

			if (extras != null) {
				long questionId = extras.getInt(QUESTION_ID);

				Thread thread = new GetThread(questionId);
				thread.start();
			}
		}
	}

	class GetThread extends Thread {
		private long id;

		public GetThread(long id) {
			this.id = id;
		}

		@Override
		public void run() {
			question = questionManager.getQuestion(id);

			runOnUiThread(doUpdateGUIDetails);
		}
	}

}