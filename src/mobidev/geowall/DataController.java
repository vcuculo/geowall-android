package mobidev.geowall;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataController {

	
	/*
	 * nick
	 * email
	 * pw
	 * image
	 * date
	 * country
	 * city
	 */
	
	public static String marshallUser(UserData userData){	
		JSONObject userJS= new JSONObject();
		try {
			userJS.put("nick", userData.getnick());
			userJS.put("email",userData.getemail());
			userJS.put("pw",userData.getpassword());
			if(userData.getimage()!=null)
				userJS.put("image", userData.getimage());
			if(userData.getdate()!=null)
				userJS.put("date", userData.getdate());
			if(userData.getcountry()!=null)
				userJS.put("country", userData.getcountry());
			if(userData.getcity()!=null)
				userJS.put("city",userData.getcity());
			return userJS.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	

		/*
		 * positionX
		 * positionY
		 * date
		 * hour
		 */
		public static String marshallGetNoticeBoard(String session,RequestNoticeBoard bd){
			JSONObject board=new JSONObject();
			
			try{
				board.put("sessionid", session);
				board.put("positionX", bd.getPx());
				board.put("positionY", bd.getPy());
				board.put("date", bd.getDate());
				return board.toString();
			}catch(JSONException e){
				e.printStackTrace();
				return null;
			}
			
		}
		
		public static NoticeBoard unMarshallGetNoticeBoard(String jsonBoard){
			try {
				JSONObject board=new JSONObject(jsonBoard);
				int px=board.getInt("positionX");
				int py=board.getInt("positionY");
				String date= board.getString("date");
				ArrayList<Message> m=new ArrayList<Message>();
				JSONArray messageJson=board.getJSONArray("messages");
				for(int i=0;i<messageJson.length();i++){
					JSONObject message=messageJson.getJSONObject(i);
					int id=message.getInt("id");
					String nick=message.getString("nick");
					String text=message.getString("text");
					String image=message.optString("image");
					String video=message.optString("video");
					m.add(new Message(id, nick, text, image, video,null));
				}
				return new NoticeBoard(px, py, date, m);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		
		/*
		 * nick
		 * type
		 * token
		 */
		/*
		public static String marshallSocial(Social sc){
			JSONObject social=new JSONObject();
			
			try {
				social.put("nick", sc.getnick());
				social.put("type", sc.gettype());
				social.put("token", sc.gettoken());
				return social.toString();
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
		public static Social unMarshallSocial(String jsonSocial){
			try {
				JSONObject social=new JSONObject(jsonSocial);
				String nick=social.getString("nick");
				String type=social.getString("type");
				String token=social.getString("token");
				return new Social(nick,type,token);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		*/
		
		/*
		 * sessionid
		 * positionX of the notice board
		 * positionY of the notice board
		 * text
		 * image
		 * video
		 * social
		 */
		public static String marshallMessage(String session,RequestNoticeBoard b,Message m){
			JSONObject mess=new JSONObject();
			try {
				mess.put("sessionid", session);
				mess.put("positionX", b.getPx());
				mess.put("positionY", b.getPy());
				if(m.gettext()!=null)
					mess.put("text", m.gettext());
				if(m.getimg()!=null)
					mess.put("img", m.getimg());
				if(m.getvideo()!=null)
					mess.put("video", m.getvideo());
				if(m.getsocial()!=null)
					mess.put("social", m.getsocial());
				return mess.toString();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		
		public static int unMarshallMessage(String jsonSession){
			try {
				JSONObject sess=new JSONObject(jsonSession);
				int id=sess.optInt("id", -1);
				return id;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		}
		
		

	
		/*
		 * session u
		 */
		
		public static String marshallSession(String session){
			JSONObject sess=new JSONObject();
			try {
				sess.put("sessionid", session);
				return sess.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
		public static String unMarshallSession(String jsonSession){
			try {
				JSONObject sess=new JSONObject(jsonSession);
				String session=sess.optString("sessionid");
				return session;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
		
		/*
		 * nick
		 * email
		 * pw
		 */
		public static String marshallLogin(LoginData loginData){	
			JSONObject userJS= new JSONObject();
			try {
				userJS.put("pw",loginData.getpassword());
				if(loginData.getnick()!=null)
					userJS.put("nick", loginData.getnick());
				if(loginData.getemail()!=null)
					userJS.put("email", loginData.getemail());
				return userJS.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
	
	}
	

