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
		db.execSQL("create table Bacheca(idBacheca int primary key,posizioneX numeric,posizioneY numeric,ultimaData datetime);");
		db.execSQL("create table Messaggio(idMessaggio int(10) unique, idBacheca int(10)not null,testo  varchar(140) default null,"
				+"img blob (255) default null, video blob (255) default null, primary key(idMessaggio),"
				+"  foreign key(idBacheca) references Bacheca (idBacheca) on update cascade on delete set null);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	

}
