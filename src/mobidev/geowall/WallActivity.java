package mobidev.geowall;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

public class WallActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wall_layout);
		addElementResult();
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
}
