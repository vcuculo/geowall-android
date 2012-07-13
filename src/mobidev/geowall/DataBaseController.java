package mobidev.geowall;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;

public class DataBaseController {

	public static void writeMessage(DataBaseGeowall db,RequestNoticeBoard nb, Message m){
		SQLiteDatabase sql = db.getWritableDatabase();
		ContentValues raw = new ContentValues();
		raw.put("idMessaggio", m.getid());
		raw.put("testo", m.gettext());
		raw.put("img", m.getimg());
		raw.put("video", m.getvideo());
		raw.put("nick", m.getnick());
		raw.put("posizioneX", nb.getPx());
		raw.put("posizioneY", nb.getPy());
		raw.put("ultimaData", nb.getDate());
		sql.insert("Messaggio", null, raw);
	}

	public static void writeAllMessage(DataBaseGeowall db,NoticeBoard nb) {
		SQLiteDatabase sql = db.getWritableDatabase();
		if (nb != null) {
			ArrayList<Message> messages = nb.getMessages();
			for (Message m : messages) {
				ContentValues raw = new ContentValues();
				raw.put("idMessaggio", m.getid());
				raw.put("testo", m.gettext());
				raw.put("img", m.getimg());
				raw.put("video", m.getvideo());
				raw.put("nick", m.getnick());
				raw.put("posizioneX", nb.getPositionX());
				raw.put("posizioneY", nb.getPositionY());
				raw.put("ultimaData", nb.getDate());
				sql.insert("Messaggio", null, raw);
			}
		}
		db.close();
	}
	
}
