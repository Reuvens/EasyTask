package com.reuven.android.easytask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddTaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Settings");
		super.onCreateOptionsMenu(menu);
        String emailAddress = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("email", "");
        if (emailAddress.isEmpty()) {
        	// Pop up a dialog to ask users to enter email address and add it to the email field of the settings.
        	setEmailDestination();
        }
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return false;
    }


	public void SendMail(View v) {
		sendEmail();
	}

	public void ShowUnimplementedDialog(View v) {
		showUnimplementedDialog();
	}

	
	private void sendEmail() {
		EditText taskContent = (EditText) findViewById(R.id.task_text);
		EditText taskDetailsContent = (EditText) findViewById(R.id.task_details_text);
		Intent email = new Intent(Intent.ACTION_SEND);
//		email.putExtra(Intent.EXTRA_EMAIL, new String[] { "reuven+tasks@google.com" });
		
	    /** Getting an instance of shared preferences, that is being used in this context */
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String emailAddress = sp.getString("email", "");
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { emailAddress });
        
//		email.putExtra(Intent.EXTRA_CC, "reuven@google.com");
		email.putExtra(Intent.EXTRA_SUBJECT, taskContent.getText().toString());
		email.putExtra(Intent.EXTRA_TEXT, taskDetailsContent.getText().toString() + "\n\n" +
				"sent by EasyTask from Reuven Labs :-)");

		// need this to prompts email client only
		email.setType("message/rfc822");

		startActivity(email);
		taskContent.setText("");
		taskDetailsContent.setText("");
	}

	private void setEmailDestination() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle("Set destination email address");

		// set dialog
		
		alertDialogBuilder.setMessage(
				"Hi There !!! \n" +
				"  Welcome to EasyTask \n" +
				"  Send tasks to your email as easy as possible. \n" +
				"  Please set the email address to send tasks too. \n" +
				"  * this can always be changed on the settings page. \n" +
				"Love to hear feedback at: easy.task.app@gmail.com");
		final EditText input = new EditText(this);
		alertDialogBuilder.setView(input);
		alertDialogBuilder
				.setPositiveButton("Ok", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String value = input.getText().toString().trim();
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
						Editor editor = prefs.edit();
						editor.putString("email", value);
						editor.commit();
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	private void showUnimplementedDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle("Sorry, this is only version 1");

		// set dialog message
		alertDialogBuilder.setMessage("Not implemented yet, Click Ok to exit!")
				.setPositiveButton("Ok", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
}
