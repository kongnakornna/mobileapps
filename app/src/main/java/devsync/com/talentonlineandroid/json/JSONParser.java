package devsync.com.talentonlineandroid.json;

import java.util.List;
import java.util.HashMap;
import org.json.JSONArray;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by Thanisak Piyasaksiri on 9/16/15 AD.
 */
public class JSONParser {
	
	private String data = null;
	private JSONArray jArray = null;
	private JSONObject jObject = null;
	
	public JSONParser(String data) throws JSONException {
		
		this.data = data;
		
		try {
			jObject = new JSONObject(this.data);
		} catch(JSONException e) {
			
			try {
				jArray = new JSONArray(this.data);
			} catch(JSONException e2) {
				jObject = null;
				jArray = null;
			}
		}
	}
	
	public boolean isData() {
		
		if(jObject == null && jArray == null)
			return false;
		else
			return true;
	}
	
	public HashMap<String, Object> convertJson2HashMap() {
		
		HashMap<String, Object> result = null;
		
		try {
			
			result = new HashMap<String, Object>();
			result.put("res-code", "200");
			result.put("res-description", "success");
			
			JSONArray jName = jObject.names();
			for(int i=0; i<jName.length(); i++) {
				
				try {
					JSONArray jArray = jObject.getJSONArray(jName.getString(i));
					result.put(jName.getString(i), convertJsonArray2List(jArray));
				} catch(JSONException e) {
					
					try {
						JSONObject jDataObj = jObject.getJSONObject(jName.getString(i));
						result.put(jName.getString(i), convertJsonObject2HashMap(jDataObj));
						
					} catch(JSONException e2) {
						result.put(jName.getString(i), jObject.getString(jName.getString(i)));
					}
				}
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			result = new HashMap<String, Object>();
			result.put("res-code", "500");
			result.put("res-description", e.getMessage());
		}
		
		return result;
	}
	
	private HashMap<String, Object> convertJsonObject2HashMap(JSONObject jDataObj) throws JSONException {
		
		HashMap<String, Object> jDataMap = new HashMap<String, Object>();
		
		JSONArray jDataObjName = jDataObj.names();
		for(int i=0; i<jDataObjName.length(); i++) {
			
			try {
				
				JSONArray jArray = jDataObj.getJSONArray(jDataObjName.getString(i));
				jDataMap.put(jDataObjName.getString(i), convertJsonArray2List(jArray));
				
			} catch(JSONException e) {
				
				try {
					JSONObject jData = jDataObj.getJSONObject(jDataObjName.getString(i));
					jDataMap.put(jDataObjName.getString(i), (HashMap<String, Object>) convertJsonObject2HashMap(jData));
					
				} catch(JSONException e2) {

					jDataMap.put(jDataObjName.getString(i), String.valueOf(jDataObj.getString(jDataObjName.getString(i))));
				}
			}
		}
		
		return jDataMap;
	}
	
	private List<HashMap<String, Object>> convertJsonArray2List(JSONArray jDataArr) throws JSONException {
		
		List<HashMap<String, Object>> jDataList = new ArrayList<HashMap<String, Object>>();
		
		for(int j=0; j<jDataArr.length(); j++) {
			
			JSONObject jDataObj = jDataArr.getJSONObject(j);
			jDataList.add((HashMap<String, Object>)convertJsonObject2HashMap(jDataObj));
		}

		return jDataList;
	}

}
