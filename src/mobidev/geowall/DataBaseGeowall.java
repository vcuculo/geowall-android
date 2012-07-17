package mobidev.geowall;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseGeowall extends SQLiteOpenHelper {

	public DataBaseGeowall(Context c) {
		super(c, "geowall", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table Messaggio(idMessaggio int unique, nick varchar, posizioneX numeric,posizioneY numeric, dataBacheca datetime, dataMessaggio datetime, testo  varchar default null,"
				+ "img blob, video blob, primary key(idMessaggio));");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
