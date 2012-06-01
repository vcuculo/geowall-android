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
		 */
		public static String marshallNoticeBoard(NoticeBoard bd){
			JSONObject board=new JSONObject();
			
			try{
				board.put("positionX", bd.getPositionX());
				board.put("positionY", bd.getPositionY());
				board.put("date", bd.getDate());
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
				return new NoticeBoard(px, py, date);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		
	}
	

