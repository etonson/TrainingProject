package com.practice_01.rest;

import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.practice_01.support.ejb.IDataController;
import com.practice_01.support.model.MachineResourceUsageSetting;
import com.practice_01.support.model.MachineX;
import com.practice_01.support.model.ProcessStage;
import com.practice_01.support.model.Resource;
import com.practice_01.support.model.ResourceUsageSettingPK;
import com.tool.JsoupWrapper;

@Path("httpmethod")
public class Q00_HttpMethod {

	JsoupWrapper jsoup = new JsoupWrapper();
	@EJB
	IDataController datacontrol;

	@Path("example01")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String example01() {
		JSONObject result = new JSONObject();
		try {
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}

	
	@Path("testget01")
	/**TODO**/
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String testGet01() {
		JSONObject result = new JSONObject();
		try {
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}
	
	@GET
	@Path("testget02")
	@Produces({ MediaType.APPLICATION_JSON })
	public String testGet02(@QueryParam("name") String name) {
		JSONObject result = new JSONObject();
		/**TODO**/
		//請將要回傳的結果放到此變數內
		String res =name;
		try {
			result.put("status", "success");
			result.put("msg", res);
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}

	
	@Path("testget03/{arg1}/{arg2}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String testGet03(@PathParam("arg1") String arg1,@PathParam("arg2") String arg2) {
		JSONObject result = new JSONObject();
		/**TODO**/
		//請將要回傳的結果放到此變數內
		String res =arg1+"/"+arg2;
		try {
			result.put("status", "success");
			result.put("msg", res);
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}

	@Path("testpost01")
	/**TODO**/
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public String testPost01() {
		JSONObject result = new JSONObject();
		try {
			result.put("status", "success");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}

	@Path("testpost02")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public String testPost02(String animal) {
		JSONObject result = new JSONObject();
		/**TODO**/
		String res = animal;
		try {
			result.put("status", "success");
			result.put("msg", res);
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}

	@Path("testpost03")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public String testPost03(ResourceUsageSettingPK obj) {
		JSONObject result = new JSONObject();
		String res = obj.getId() + obj.getVersion();
		try {
			result.put("status", "success");
			result.put("msg", res);
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}

	@Path("testdatacontrol01")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String testDataControl01(@QueryParam("machinexname")String machinexname) {
		JSONObject result = new JSONObject();

		try {
			MachineX res = datacontrol.retriveControl(machinexname);
			result.put("status", "success");
			result.put("msg", res.getBrand());
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}
	
	@Path("testdatacontrol02")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public String testDataControl02(String strFilter) {
		JSONObject objJSON = new JSONObject(strFilter);
		Map<String,Object> filter = objJSON.toMap();
		JSONObject result = new JSONObject(strFilter);
		try {
			ProcessStage res = datacontrol.retriveControl(filter);
			result.put("status", "success");
			result.put("msg", res.getId());
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}
	
	@Path("testdatacontrol03")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public String testDataControl03(String strResult) {
		JSONObject objJSON = new JSONObject(strResult);
		ResourceUsageSettingPK resourceUsageSettingPK = new ResourceUsageSettingPK();
		resourceUsageSettingPK.setId(objJSON.optString("id"));
		resourceUsageSettingPK.setVersion(objJSON.optInt("version"));
		
		MachineResourceUsageSetting mrs = new MachineResourceUsageSetting();
		mrs.setPk(resourceUsageSettingPK);
		JSONObject result = new JSONObject();
		try {
			datacontrol.addControl(mrs);
			MachineResourceUsageSetting res = datacontrol.validateAddControl();
			result.put("status", "success");
			result.put("msg", res != null ? true : false);
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}
	
	@Path("testdatacontrol04")
	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	public String testDataControl04(String strObj) {
		JSONObject objJSON = new JSONObject(strObj);
		Resource rs = new Resource(objJSON.optString("id"), objJSON.optString("name"));
		JSONObject result = new JSONObject();
		try {
			datacontrol.updateControl(rs);
			Resource res = datacontrol.validateUpdateControl();
			result.put("status", "success");
			result.put("msg", res.getName());
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}
	
	@Path("testdatacontrol05")
	@DELETE
	public String testDataControl05(@QueryParam("name")String machinename) {
		JSONObject result = new JSONObject();
		System.out.println("machinename="+machinename);
		try {
			 datacontrol.deleteControl(new MachineX(machinename)) ; 
			 result.put("status", "success");
			 result.put("msg", "ok");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	
	}
	
	@Path("validatetestdatacontrol05")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	public String validateTestDataControl05(@QueryParam("name")String machinename) {
		JSONObject result = new JSONObject();
		try { 
			 MachineX res = datacontrol.validateDeleteControl();
			result.put("status", "success");
			result.put("msg", res==null?true:false);
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", e);
		}
		return result.toString();
	}

}
