package com.kimile.demo.zuul.conf;

public class BasicConf {

	//IP黑名单，多个用逗号分隔
	private String ipStr = "default";
	
	//降级的服务ID，多个用逗号分开
	private String downGradeServiceStr = "default";
	
	//灰度发布的服务信息，ip:port，多个用逗号分开
	private String grayPushServers = "default";
	
	//灰度发布的用户ID信息，多个用逗号分开
	private String grayPushUsers = "default";
	
	//API接口白名单，多个用逗号分开
	private String apiWhiteStr = "default";

	public String getIpStr() {
		return ipStr;
	}

	public void setIpStr(String ipStr) {
		this.ipStr = ipStr;
	}

	public String getDownGradeServiceStr() {
		return downGradeServiceStr;
	}

	public void setDownGradeServiceStr(String downGradeServiceStr) {
		this.downGradeServiceStr = downGradeServiceStr;
	}

	public String getGrayPushServers() {
		return grayPushServers;
	}

	public void setGrayPushServers(String grayPushServers) {
		this.grayPushServers = grayPushServers;
	}

	public String getGrayPushUsers() {
		return grayPushUsers;
	}

	public void setGrayPushUsers(String grayPushUsers) {
		this.grayPushUsers = grayPushUsers;
	}

	public String getApiWhiteStr() {
		return apiWhiteStr;
	}

	public void setApiWhiteStr(String apiWhiteStr) {
		this.apiWhiteStr = apiWhiteStr;
	}
	
}
