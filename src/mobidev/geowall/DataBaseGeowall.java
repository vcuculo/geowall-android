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
		db.execSQL("create table Messaggio(idMessaggio int(10) unique, nick varchar, posizioneX numeric not null,posizioneY numeric, ultimaData datetime, testo  varchar(140) default null,"
				+"img blob (255) default null, video blob (255) default null, primary key(idMessaggio));");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	

}
