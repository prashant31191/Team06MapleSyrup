package ca.ualberta.app.adapter;

import java.util.ArrayList;

import ca.ualberta.app.activity.CreateAnswerReplyActivity;
import ca.ualberta.app.activity.R;
import ca.ualberta.app.models.Answer;
import ca.ualberta.app.models.Question;
import ca.ualberta.app.models.Reply;
import ca.ualberta.app.models.User;
import ca.ualberta.app.thread.UpdateAnswerThread;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class ReplyListAdapter_v2 extends BaseExpandableListAdapter {
	private ArrayList<Answer> answerList = null;
	private Question question;
	private Context context;

	public ReplyListAdapter_v2(Context context, int singleAnswer,
			int singleReply, ArrayList<Answer> answers, Question question) {
		this.context = context;
		this.answerList = answers;
		this.question = question;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return answerList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return answerList.get(groupPosition).getReplyArrayList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return answerList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return answerList.get(groupPosition).getReplyArrayList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolder_answer holder = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			holder = new ViewHolder_answer();
			convertView = inflater.inflate(R.layout.single_answer, null);
		} else {
			holder = (ViewHolder_answer) convertView.getTag();
		}
		holder.authorPic = (ImageView) convertView.findViewById(R.id.authorPic);
		holder.image = (ImageView) convertView.findViewById(R.id.answerImage);
		holder.authorName = (TextView) convertView
				.findViewById(R.id.authorNameTextView);
		holder.answerContent = (TextView) convertView
				.findViewById(R.id.answerContentTextView);
		holder.upvoteState = (TextView) convertView
				.findViewById(R.id.upvoteStateTextView);
		holder.upvote_Rb = (RadioButton) convertView
				.findViewById(R.id.upvote_button);
		holder.timestamp = (TextView) convertView
				.findViewById(R.id.answer_time_textView);
		holder.replyList = (ExpandableListView) convertView
				.findViewById(R.id.answer_reply_expandableListView);
		holder.image.setVisibility(View.GONE);
		holder.reply_Rb = (RadioButton) convertView
				.findViewById(R.id.answer_reply_button);
		if (User.loginStatus == false) {
			holder.reply_Rb.setVisibility(View.GONE);
		} else {
			holder.reply_Rb.setVisibility(View.VISIBLE);
		}
		convertView.setTag(holder);
		Answer answer = answerList.get(groupPosition);

		if (answer != null) {
			holder.answerContent.setText(answer.getContent());
			holder.authorName.setText(answer.getAuthor());
			holder.timestamp.setText(answer.getTimestamp().toString());
			holder.upvoteState.setText("Upvote: "
					+ answer.getAnswerUpvoteCount());
			if (answer.hasImage()) {
				holder.image.setVisibility(View.VISIBLE);
				holder.image.setImageBitmap(answer.getImage());
			}
		}
		holder.upvote_Rb
				.setOnClickListener(new upvoteOnClickListener(groupPosition));
		holder.reply_Rb
				.setOnClickListener(new AddReplyOnClickListener(groupPosition));

		return convertView;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder_reply holder = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			holder = new ViewHolder_reply();
			convertView = inflater.inflate(R.layout.single_reply, null);
		} else {
			holder = (ViewHolder_reply) convertView.getTag();
		}
		holder.authorName = (TextView) convertView
				.findViewById(R.id.replyAuthor_textView);
		holder.replyContent = (TextView) convertView
				.findViewById(R.id.reply_textView);
		holder.timestamp = (TextView) convertView
				.findViewById(R.id.reply_time_textView);
		convertView.setTag(holder);
		Reply reply = answerList.get(groupPosition).getReplyArrayList().get(childPosition);
		if (reply != null) {
			holder.replyContent.setText(reply.getContent());
			holder.authorName.setText(reply.getAuthor());
			holder.timestamp.setText(reply.getTimestamp().toString());
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	private class upvoteOnClickListener implements OnClickListener {

		int position;

		public upvoteOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Answer answer = answerList.get(position);
			answer.upvoteAnswer();
			question.calcCurrentTotalScore();
			Thread thread = new UpdateAnswerThread(question, answer);
			thread.start();

			notifyDataSetChanged();
		}
	}

	private class AddReplyOnClickListener implements OnClickListener {

		int position;

		public AddReplyOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context,
					CreateAnswerReplyActivity.class);
			intent.putExtra(CreateAnswerReplyActivity.QUESTION_ID,
					question.getID());
			intent.putExtra(CreateAnswerReplyActivity.ANSWER_POS, position);
			context.startActivity(intent);
			notifyDataSetChanged();
		}
	}
	class ViewHolder_reply {
		TextView authorName;
		TextView replyContent;
		TextView timestamp;
	}
	
	class ViewHolder_answer {
		ImageView authorPic;
		TextView authorName;
		TextView answerContent;
		RadioButton upvote_Rb;
		TextView timestamp;
		TextView upvoteState;
		ImageView image;
		ExpandableListView replyList;
		RadioButton reply_Rb;
	}

}