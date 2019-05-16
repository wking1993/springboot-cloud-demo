package com.kimile.web.eureka.component;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaServerStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.InstanceInfo;

/**
 * 某些特定的需求，我们需要对服务的上下线等信息进行监控，针对不同行为做一些业务处理，比如进行邮件通知运维人员等；
 * 对此，Eureka中提供了事件监听的方式来扩展，目前支持的时间如下：
 * ☆EurekaInstanceCanceledEvent 服务下线事件
 * ☆EurekaInstanceRegisteredEvent 服务注册事件
 * ☆EurekaInstanceRenewedEvent 服务续约事件（服务端每次想Server发送心跳检测，即会触发续约事件）
 * ☆EurekaRegistryAvailableEvent Eureka注册中心启动事件
 * ☆EurekaServerStartedEvent Server启动事件
 * 在Eureka集群环境下，每个节点都会触发事件，这个时候需要控制下发送通知的行为，不控制的话，每个节点都会发送通知。
 */
@Component
public class EurekaStateChangeListener {
	
	@EventListener
	public void listen(EurekaInstanceCanceledEvent event) {
		System.err.println(event.getServerId() + "\t" + event.getAppName() + "服务下线！");
	}
	
	@EventListener
	public void listen(EurekaInstanceRegisteredEvent event) {
		InstanceInfo instanceInfo = event.getInstanceInfo();
		System.err.println(instanceInfo.getAppName() + "进行注册！");
	}

	@EventListener
	public void listen(EurekaInstanceRenewedEvent event) {
		System.err.println(event.getServerId() + "\t" + event.getAppName() + "服务进行续约！");
	}
	
	@EventListener
	public void listen(EurekaRegistryAvailableEvent event) {
		System.err.println("注册中心，启动！");
	}
	
	@EventListener
	public void listen(EurekaServerStartedEvent event) {
		System.err.println("Eureka Server 启动！");
	}
	
}
