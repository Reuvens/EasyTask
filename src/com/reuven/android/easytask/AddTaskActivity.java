package com.reuven.android.easytask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
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
