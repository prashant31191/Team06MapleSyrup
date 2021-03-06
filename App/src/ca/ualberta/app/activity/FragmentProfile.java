/*
 * Copyright 2014 Anni Dai
 * Copyright 2014 Bicheng Yan
 * Copyright 2014 Liwen Chen
 * Copyright 2014 Liang Jingjing
 * Copyright 2014 Xiaocong Zhou
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ualberta.app.activity;

import ca.ualberta.app.activity.R;
import ca.ualberta.app.controller.AuthorMapController;
import ca.ualberta.app.models.User;
import ca.ualberta.app.network.InternetConnectionChecker;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the fragment activity for the mean question list, once the app is
 * started, or a user clicks the "Main" button on the bottom action bar
 * 
 * The fragment part is from this web site:
 * http://www.programering.com/a/MjNzIDMwATI.html
 * 
 */
public class FragmentProfile extends Fragment {
	private TextView titleBar;
	private ImageButton changePhotoButton;
	private TextView setAuthorName;
	private RadioButton local_cache;
	private RadioButton fav_question;
	private RadioButton my_question;
	private RadioButton waiting_list;
	private RadioButton login;
	private RadioButton logout;
	private Context mcontext;
	private String newUsername = null;
	private String loginCause = "Login";
	private AuthorMapController authorMapController;

	/**
	 * Once the fragment is active, the user interface,
	 * R.layout.fragment_profile will be load into the fragment.
	 * 
	 * @param inflater
	 *            is used to find out the layout defined in the xml file.
	 * @param container
	 *            the view container that contains all views of an single item.
	 * @param savedInstanceState
	 *            the saved instance state bundle.
	 * 
	 * @return inflater the layout of this fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_profile, container, false);
	}

	/**
	 * Once the fragment is created, this method will give each view an object
	 * to help other methods set data or listener.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state bundle.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mcontext = getActivity().getApplicationContext();
		titleBar = (TextView) getView().findViewById(R.id.titleTv);
		titleBar.setText("Profile");
		changePhotoButton = (ImageButton) getView().findViewById(
				R.id.changePhotoButton);
		setAuthorName = (TextView) getView().findViewById(R.id.setAuthorName);
		local_cache = (RadioButton) getView().findViewById(R.id.local_cache);
		fav_question = (RadioButton) getView().findViewById(R.id.fav_question);
		my_question = (RadioButton) getView().findViewById(R.id.my_question);
		waiting_list = (RadioButton) getView().findViewById(R.id.waiting_list);
		login = (RadioButton) getView().findViewById(R.id.login);
		logout = (RadioButton) getView().findViewById(R.id.logout);

		checkLoginStatus();
		setAuthorName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (InternetConnectionChecker.isNetworkAvailable()) {
					authorMapController = new AuthorMapController(mcontext);
					showDialog();
				} else {
					Toast.makeText(mcontext,
							"Internet is required to change name",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		login.setOnClickListener(new OnClickListener() {
			/**
			 * Setup the listener for the "Login" button, so that, once the
			 * button is clicked, the login window will be displayed.
			 * 
			 * @param v
			 *            The view of the button.
			 */
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				intent.putExtra(LoginActivity.LOGINCAUSE, loginCause);
				startActivity(intent);

			}
		});

		logout.setOnClickListener(new OnClickListener() {
			/**
			 * Setup the listener for the "Logout" button, so that, once the
			 * button is clicked, the activity will be set to the statues before
			 * logging in.
			 * 
			 * @param v
			 *            The view of the button.
			 */
			public void onClick(View v) {
				User.loginStatus = false;
				User.author = null;
				checkLoginStatus();
			}
		});

		my_question.setOnClickListener(new OnClickListener() {
			/**
			 * Setup the listener for the "My Questions" button, so that, once
			 * the button is clicked, the author's own question(s) can be
			 * displayed.
			 * 
			 * @param v
			 *            The view of the button.
			 */
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						MyQuestionActivity.class);
				startActivity(intent);

			}
		});

		fav_question.setOnClickListener(new OnClickListener() {
			/**
			 * Setup the listener for the "Favorite Questions" button, so that,
			 * once the button is clicked, the favorite question(s) can be
			 * displayed.
			 * 
			 * @param v
			 *            The view of the button.
			 */
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						MyFavoriteActivity.class);
				startActivity(intent);

			}
		});

		local_cache.setOnClickListener(new OnClickListener() {
			/**
			 * Setup the listener for the "My Questions" button, so that, once
			 * the button is clicked, the author's own question(s) can be
			 * displayed.
			 * 
			 * @param v
			 *            The view of the button.
			 */
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MyLocalActivity.class);
				startActivity(intent);
			}
		});

		waiting_list.setOnClickListener(new OnClickListener() {
			/**
			 * Setup the listener for the "Waiting List" button, so that, once
			 * the button is clicked, the Offline question(s),answer(s) and
			 * reply(s) can be displayed.
			 * 
			 * @param v
			 *            The view of the button.
			 */
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						WaitingListActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * onResume method. Once the activity is resumed from other activities,
	 * check the user's longing statues.
	 */
	@Override
	public void onResume() {
		super.onResume();
		checkLoginStatus();
		if (InternetConnectionChecker.isNetworkAvailable()) {
			titleBar.setText("Profile");
		} else {
			titleBar.setText("Profile(Not Connected)");
		}
	}

	/**
	 * onPause method. Once the activity is paused, check the user's longing
	 * statues.
	 */
	@Override
	public void onPause() {
		super.onPause();
		checkLoginStatus();
	}

	
	/**
	 * will be called when user want to re-set the username
	 */
	private void showDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setTitle("Change Author Name");
		alert.setMessage("Enter Your Name Here");

		final EditText input = new EditText(getActivity());
		alert.setView(input);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				newUsername = input.getEditableText().toString();
				if (newUsername != null) {
					if (!authorMapController.hasAuthor(mcontext, newUsername)) {
						User.author.setUsername(newUsername);
						authorMapController.updateAuthor(mcontext, User.author);

					} else {
						Toast.makeText(
								getActivity(),
								"The username is aready exist, please choose another one.",
								Toast.LENGTH_SHORT).show();
						showDialog();
					}
					setAuthorName.setText(User.author.getUsername());
				} else
					Toast.makeText(mcontext, "Please fill in name",
							Toast.LENGTH_SHORT).show();
			}

		});
		alert.setNegativeButton("CANCEL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}

	/**
	 * will display the buttons only when user already login
	 */
	public void checkLoginStatus() {
		if (User.loginStatus) {
			changePhotoButton.setVisibility(View.VISIBLE);
			setAuthorName.setVisibility(View.VISIBLE);
			login.setVisibility(View.GONE);
			waiting_list.setVisibility(View.VISIBLE);
			logout.setVisibility(View.VISIBLE);
			my_question.setVisibility(View.VISIBLE);
			setAuthorName.setText(User.author.getUsername());

		} else {
			changePhotoButton.setVisibility(View.GONE);
			setAuthorName.setVisibility(View.GONE);
			waiting_list.setVisibility(View.GONE);
			login.setVisibility(View.VISIBLE);
			logout.setVisibility(View.GONE);
			my_question.setVisibility(View.GONE);
		}
	}

}