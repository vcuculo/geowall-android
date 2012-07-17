package mobidev.geowall;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseController {

	public static void writeMessage(DataBaseGeowall db, RequestNoticeBoard nb,
			Message m) {
		SQLiteDatabase sql = db.getWritableDatabase();
		ContentValues raw = new ContentValues();
		raw.put("idMessaggio", m.getid());
		if (m.gettext() != "" || m.gettext() != null)
			raw.put("testo", m.gettext());
		else
			raw.put("testo", "");
		if (m.getimg() != null)
			raw.put("img", m.getimg());
		else
			raw.put("img", "null");
		if (m.getvideo() != null)
			raw.put("video", m.getvideo());
		else
			raw.put("video", "null");

		raw.put("nick", m.getnick());
		raw.put("dataBacheca", m.getdate());
		raw.put("posizioneX", nb.getPx());
		raw.put("posizioneY", nb.getPy());
		if (m.getdate() != null) {
			raw.put("dataMessaggio", m.getdate());
		} else {

			raw.put("dataMessaggio", "null");
		}
		if (nb.getDate() != null) {
			raw.put("dataBacheca", nb.getDate());
		} else {

			raw.put("dataBacheca", "null");
		}
		sql.insert("Messaggio", null, raw);
		db.close();
	}

	public static void writeAllMessage(DataBaseGeowall db, NoticeBoard nb) {
		SQLiteDatabase sql = db.getWritableDatabase();

		if (nb != null) {
			ArrayList<Message> messages = nb.getMessages();
			for (Message m : messages) {
				String[] where = { Integer.toString(m.getid()) };
				Cursor c = sql
						.rawQuery(
								"select idMessaggio from Messaggio where idMessaggio = ?",
								where);
				if (c.getCount() <= 0) {
					ContentValues raw = new ContentValues();
					raw.put("idMessaggio", m.getid());
					if (m.gettext() != "" || m.gettext() != null)
						raw.put("testo", m.gettext());
					else
						raw.put("testo", "");
					if (m.getimg() != null)
						raw.put("img", m.getimg());
					else
						raw.put("img", "null");
					if (m.getvideo() != null)
						raw.put("video", m.getvideo());
					else
						raw.put("video", "null");
					raw.put("nick", m.getnick());
					raw.put("posizioneX", nb.getPositionX());
					raw.put("posizioneY", nb.getPositionY());
					if (m.getdate() != null) {
						raw.put("dataMessaggio", m.getdate());
					} else {

						raw.put("dataMessaggio", "null");
					}
					if (nb.getDate() != null) {
						raw.put("dataBacheca", nb.getDate());
					} else {
						raw.put("dataBacheca", "null");
					}
					try {
						sql.insert("Messaggio", null, raw);
					} catch (SQLiteConstraintException e) {
						// in questo caso il messaggio è gia all'interno del
						// database locale
						return;
					} catch (Exception e) {
						Log.e("WriteAllMessage", e.toString());
					}
				}
			}
		}
		db.close();
	}

}
