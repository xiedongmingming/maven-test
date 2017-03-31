package org.xiem.com.curator.extend;

import org.codehaus.jackson.map.annotate.JsonRootName;


@JsonRootName("details")
public class InstanceDetails {// 服务类--定义了服务实例的基本信息(实际中可能会定义更详细的信息)

	// curator-x-discovery: 这是一个服务发现的RECIPE
	// curator-x-rpc

	private String description;

	public InstanceDetails() {
		this("");
	}
	public InstanceDetails(String description) {
		this.description = description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
}