package mobidev.geowall;


import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

public class WallActivity extends Activity implements OnClickListener{
	/** Called when the activity is first created. */

	final static int CUSTOM_DIALOG=1;
	private String USER_PREFERENCES = "UserPreferncer";
	SharedPreferences setting;
	ImageView accountImage, messageImage;
	String imageMessageBase64=null;//immagine da passare al server
	
	Button insert;
	ImageButton upload;
	TextView mesText;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wall_layout);
		addElementResult();
		accountImage=(ImageView) findViewById(R.id.accountImage);		
		insert=(Button) findViewById(R.id.insertMessageButton);
		insert.setOnClickListener(this);
		
	
		
	}

	public void addElementResult() {
		TableLayout messages = (TableLayout) findViewById(R.id.tableMessages);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < 20; i++) {
			View itemView = inflater.inflate(R.layout.wall_layout_item, null);

			TextView t1 = (TextView) itemView.findViewById(R.id.title);
			t1.setText("Messaggio " + i);
			TextView t2 = (TextView) itemView.findViewById(R.id.date);
			t2.setText((i+1) + "/05/2012");

			messages.addView(itemView);
		}
	}
	
	public void onResume() {
		super.onResume();
		setting = getSharedPreferences(USER_PREFERENCES, 0);
		String photo=setting.getString("IMG", null);

		if (photo!=null) {
			accountImage.setAdjustViewBounds(true);
			accountImage.setMaxHeight(40);
			accountImage.setMaxWidth(40);
			accountImage.setImageBitmap(MediaController.decodeBase64toBitmap(photo));
			
		}

	}
	
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog=new CustomDialog(this);
	    switch(id) {
	    case CUSTOM_DIALOG:

	    	dialog.setContentView(R.layout.custom_account_dialog);
	    	dialog.setTitle(R.string.dialogTitle);
	    	mesText=(TextView)dialog.findViewById(R.id.messageText);
			mesText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		
	    	break;
	    }
	    return dialog;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.insertMessageButton:
			Context mContext = getApplicationContext();
			Dialog dialog = new Dialog(mContext);
			dialog.setContentView(R.layout.custom_account_dialog);
			showDialog(CUSTOM_DIALOG);
			
			break;
		
	
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater= getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.logoutMenu:
			SharedPreferences settings = getSharedPreferences(USER_PREFERENCES, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.clear();
			editor.commit();
			finish();

			
			break;
		case R.id.settingMenu:
			Intent i = new Intent(this, RegistrationActivity.class);
			this.startActivity(i);
			break;
		default :
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	
	//hide class
	class CustomDialog extends Dialog {
		
		EditText etName;

		public CustomDialog(Context context) {
			super(context);
			
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.custom_account_dialog);
			setTitle(R.string.dialogTitle);
			ImageButton upload = (ImageButton) findViewById(R.id.uploadButton);
			upload.setOnClickListener(new buttonListener());
			etName = (EditText) findViewById(R.id.messageText);
		}

		private class buttonListener implements android.view.View.OnClickListener {
			@Override
	        public void onClick(View v) {
	        	switch(v.getId()){
	        	case R.id.uploadButton:

	        		Intent imageIntent = MediaController.getMedia(MediaController.TAKE_PHOTO);
	        		startActivityForResult(imageIntent, MediaController.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	        		//CustomDialog.this.dismiss();
	            break;
	        	}
	        }
		}
		
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == MediaController.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
				if (resultCode == RESULT_OK) {

					try {
						messageImage.setAdjustViewBounds(true);
						messageImage.setMaxHeight(40);
						messageImage.setMaxWidth(40);
						// user take photo
						File imageMessageFile = MediaController.getImage();
						Bitmap temp = MediaController.decodeFile(imageMessageFile);
						MediaController.saveMedia(temp,
								MediaController.MEDIA_TYPE_IMAGE);
					
						messageImage.setImageBitmap(temp);
						temp = null;
						imageMessageFile = null;

						imageMessageBase64 = MediaController
								.encodeBase64toString(MediaController.getImage());
						ImageSpan imagespan=new ImageSpan(messageImage.getDrawable());
						Drawable imgDraw=messageImage.getDrawable();
						imgDraw.setBounds(0, 0, 40, 40);
						etName.setCompoundDrawables(messageImage.getDrawable(), null, null, null);
					}catch(Exception e){
						e.printStackTrace();
					}
					
	}

			}
		}
}
}
