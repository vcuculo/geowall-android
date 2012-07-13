package mobidev.geowall;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class WallActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	final static int CUSTOM_DIALOG = 1;
	private String USER_PREFERENCES = "UserPreference";
	SharedPreferences setting;
	ImageView accountImage, messageImage;
	String imageMessageBase64 = null;// immagine da passare al server

	Button insert;
	ImageButton upload;
	TextView mesText;
	RequestNoticeBoard rNB;
	DataBaseGeowall db;
	SQLiteDatabase sql;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wall_layout);
		int i = getIntent().getIntExtra("ID", 5);
		int pxNb = getIntent().getIntExtra("pxNb", Integer.MAX_VALUE);
		int pyNb = getIntent().getIntExtra("pyNb", Integer.MAX_VALUE);
		boolean noticeboardRequest = getIntent().getBooleanExtra("NoRequest",
				false);
		String dateNb = getIntent().getStringExtra("dateNb");

		accountImage = (ImageView) findViewById(R.id.accountImage);
		insert = (Button) findViewById(R.id.insertMessageButton);
		db = new DataBaseGeowall(this);
		sql = db.getWritableDatabase();
		if (i == 4) {
			insert.setVisibility(0);
			insert.setOnClickListener(this);
		}
		if (pxNb != Integer.MAX_VALUE && pyNb != Integer.MAX_VALUE)
			rNB = new RequestNoticeBoard(pxNb, pyNb, dateNb);
		setting = getSharedPreferences(USER_PREFERENCES, 0);

		if (setting.contains("SESSION") && !noticeboardRequest) {
			new NoticeboardController(rNB).execute(this);
			addElementResult();

		} else if (noticeboardRequest) {
			addElementResult();
		} else {
			Log.e("SharePreferences", "Session non è nelle pre");
		}
		db.close();
	}

	public void addElementResult() {
		TableLayout messages = (TableLayout) findViewById(R.id.tableMessages);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		ArrayList<Message> messaggio = getMessage();
		if (messaggio == null)
			return;
		for (int i = 0; i < messaggio.size(); i++) {
			View itemView = inflater.inflate(R.layout.wall_layout_item, null);

			TextView t2 = (TextView) itemView.findViewById(R.id.title);
			t2.setText(messaggio.get(i).gettext());
			String imgBase64 = messaggio.get(i).getimg();
			if (imgBase64 != null) {
				Bitmap temp=MediaController.decodeBase64toBitmap(imgBase64);
				temp.setDensity(0);
				Drawable img = new BitmapDrawable(temp);
				img.setBounds(0, 0, 40, 40);
				t2.setCompoundDrawables(null, null, img, null);
				t2.setPadding(2, 2, 2, 2);
			}
			TextView t1 = (TextView) itemView.findViewById(R.id.date);
			 t1.setText((i + 1) + "/05/2012");

			messages.addView(itemView);
		}
	}

	public void onResume() {
		super.onResume();
		setting = getSharedPreferences(USER_PREFERENCES, 0);
		String photo = setting.getString("IMG", null);

		if (photo != null) {
			accountImage.setAdjustViewBounds(true);
			accountImage.setMaxHeight(40);
			accountImage.setMaxWidth(40);
			accountImage.setImageBitmap(MediaController
					.decodeBase64toBitmap(photo));

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.insertMessageButton:

			Intent intent = new Intent(this,
					mobidev.geowall.InsertMessageActivity.class);
			intent.putExtra("positionX", rNB.getPx());
			intent.putExtra("positionY", rNB.getPy());
			intent.putExtra("date", rNB.getDate());
			startActivity(intent);
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.logoutMenu:
			new LogoutController().execute(this);
			break;
		case R.id.settingMenu:
			i = new Intent(this, GeoMapActivity.class);
			this.startActivity(i);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private ArrayList<Message> getMessage() {
		int pxNb = getIntent().getIntExtra("pxNb", Integer.MAX_VALUE);
		int pyNb = getIntent().getIntExtra("pyNb", Integer.MAX_VALUE);
		ArrayList<Message> m = new ArrayList<Message>();
		String where = "Messaggio.posizioneX = " + pxNb
				+ " and Messaggio.posizioneY = " + pyNb
				+ " order by ultimaData desc";
		try {
			Cursor c = sql.rawQuery(
					"select testo, img, nick from Messaggio where "
							+ where, null);
			while (c.moveToNext()) {
				String text = c.getString(c.getColumnIndex("testo"));
				String img = c.getString(c.getColumnIndex("img"));
				String nick = c.getString(c.getColumnIndex("nick"));
				m.add(new Message(nick, text, img, null, null));
			}
			Log.i("message", "messaggio");

		} catch (NullPointerException e) {
			return null;
		}
		return m;
	}

}
