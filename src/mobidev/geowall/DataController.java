package mobidev.geowall;

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
	
	public static UserData unMarshallUser(String jsonUser){
		try {
			JSONObject user=new JSONObject(jsonUser);
			String nick=user.getString("nick");
			String email=user.getString("email");
			String pw=user.getString("pw");
			String image=user.optString("image",null);
			String date=user.optString("date",null);
			String country=user.optString("country",null);
			String city=user.optString("city");
			return new UserData(nick, email, pw, image, date, country, city);
			
		} catch (JSONException e) {
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
		public static String marshallNoticeBoard(NoticeBoard bd){
			JSONObject board=new JSONObject();
			
			try{
				board.put("positionX", bd.getPositionX());
				board.put("positionY", bd.getPositionY());
				board.put("date", bd.getDate());
				board.put("hour",bd.getHour());
				return board.toString();
			}catch(JSONException e){
				e.printStackTrace();
				return null;
			}
			
		}
		
		public static NoticeBoard unMarshallBoard(String jsonBoard){
			try {
				JSONObject board=new JSONObject(jsonBoard);
				int px=board.getInt("positionX");
				int py=board.getInt("positionY");
				String date= board.getString("date");
				String hour=board.getString("hour");
				return new NoticeBoard(px, py, date,hour);
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
		
		
		/*
		 * nick
		 * positionX of the notice board
		 * positionY of the notice board
		 * text
		 * img
		 * video
		 */
		public static String marshallMessage(Message m){
			JSONObject mess=new JSONObject();
			try {
				mess.put("nick", m.getnick());
				mess.put("positionX", m.getnb().getPositionX());
				mess.put("positionY", m.getnb().getPositionX());
				if(m.gettext()!=null)
					mess.put("text", m.gettext());
				if(m.getimg()!=null)
					mess.put("img", m.getimg());
				if(m.getvideo()!=null)
					mess.put("video", m.getvideo());
				return mess.toString();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		
		
		public static Message unMarshallMessage(String jsonMessage){
			try {
				JSONObject mess=new JSONObject(jsonMessage);
				String nick=mess.getString("nick");
				
				int px=mess.getInt("positionX");
				int py=mess.getInt("positionY");
				String date= mess.getString("date");
				String hour=mess.getString("hour");
				
				String text=mess.optString("text", null);
				String img=mess.optString("img",null);
				String video=mess.optString("video",null);
				
				NoticeBoard nb=new NoticeBoard(px,py,date,hour);
				return new Message(nick, nb, text, img, video);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
	}
	

