package org.xiem.com.curator.extend;

import java.io.Closeable;
import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

public class ExampleServer implements Closeable {// 相当与你在分布式环境中的服务应用(每个服务应用实例都类似这个类(应用启动时调用START、关闭时调用CLOSE))

	private final ServiceDiscovery<InstanceDetails> serviceDiscovery;

	private final ServiceInstance<InstanceDetails> thisInstance;

	public ExampleServer(CuratorFramework client, String path, String serviceName, String description)
			throws Exception {

		UriSpec uriSpec = new UriSpec("{scheme}://foo.com:{port}");
		
		thisInstance = ServiceInstance.<InstanceDetails>builder()
				.name(serviceName)
				.payload(new InstanceDetails(description))
				.port((int) (65535 * Math.random())) 
				.uriSpec(uriSpec)
				.build();

		JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<InstanceDetails>(
				InstanceDetails.class);
		
		serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class).client(client).basePath(path)
				.serializer(serializer).thisInstance(thisInstance).build();
	}

	public ServiceInstance<InstanceDetails> getThisInstance() {
		return thisInstance;
	}

	public void start() throws Exception {
		serviceDiscovery.start();
	}

	@Override
	public void close() throws IOException {
		CloseableUtils.closeQuietly(serviceDiscovery);
	}
}
