package mobidev.geowall;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;

public class DataBaseController {

	public static RequestNoticeBoard request(DataBaseGeowall db) {
		SQLiteDatabase sql = db.getReadableDatabase();
		String query = "SELECT posizioneX, posizioneY, ultimaData FROM Bacheca";
		Cursor p = sql.rawQuery(query, null);
		// i know that there is one noticeboard
		p.moveToFirst();
		int x = p.getInt(p.getColumnIndex("posizioneX"));
		int y = p.getInt(p.getColumnIndex("posizioneY"));
		String date = p.getString(p.getColumnIndex("ultimaData"));
		db.close();
		return new RequestNoticeBoard(x, y, date);
		
	}

	public static void write(DataBaseGeowall db,NoticeBoard nb) {
		SQLiteDatabase sql = db.getWritableDatabase();
		int idBacheca = 1;
		if (nb != null) {
			ArrayList<Message> messages = nb.getMessages();
			for (Message m : messages) {
				ContentValues raw = new ContentValues();
				raw.put("idMessaggio", m.getid());
				raw.put("idBacheca", idBacheca);
				raw.put("testo", m.gettext());
				raw.put("img", m.getimg());
				raw.put("video", m.getvideo());
				sql.insert("Messaggio", null, raw);
			}
		}
		db.close();
	}
	
}
