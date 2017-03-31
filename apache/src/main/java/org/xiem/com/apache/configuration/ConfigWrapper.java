package org.xiem.com.apache.configuration;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.InvariantReloadingStrategy;
import org.apache.commons.configuration.reloading.ReloadingStrategy;

public class ConfigWrapper {

	// 注意:设计模式

	private static volatile ConfigWrapper current;// ????修饰符表示永远使用最新的配置(每次装载都会重新生成一个对象)

	@SuppressWarnings("unused")
	private final Configuration config;// 管理的配置

	// **********************************************************************************************
	ConfigWrapper(final Configuration config) {
		this.config = config;
	}
	// **********************************************************************************************

	public static ConfigWrapper getCurrent() {
		return current;
	}

	public static void setCurrent(final ConfigWrapper configWrapper) {
		assert (configWrapper != null);
		current = configWrapper;
	}

	public static void setup() {// 配置文件设置入口--设计模式

		setCurrent(new ConfigWrapper(loadConfig()));// 初始主动加载一次

		// CronJobManager.runPeriodically(CronJobManager.CronJob.RELOAD_CONFIG,
		// new Runnable() {
		// @Override
		// public void run() {
		// setCurrent(new ConfigWrapper(loadConfig()));
		// }
		// }, RELOAD_CONFIG_DELAY, RELOAD_CONFIG_INTERVAL,
		// RELOAD_CONFIG_TIMEUNIT);
	}

	private static Configuration loadConfig() {

		CompositeConfiguration config = new CompositeConfiguration();// 表示混合配置管理器

		ReloadingStrategy strategy = new InvariantReloadingStrategy();// 表示配置文件的加载策略(不需要自动加载--会在CRONJOB中加载)

		final PropertiesConfiguration propsLocal, propsServer;// 表示属性文件的配置

		try {// 本地配置文件
			propsLocal = new PropertiesConfiguration("server.config");// 加载本地配置文件
			propsLocal.setReloadingStrategy(strategy);
			config.addConfiguration(propsLocal);
		} catch (ConfigurationException e) {

		}

		try {// 服务器配置文件--文件路径有两种可能(系统属性或指定文件目录)
			propsServer = new PropertiesConfiguration(System.getProperty("server.config"));// 加载服务配置文件
			propsServer.setReloadingStrategy(strategy);
			config.addConfiguration(propsServer);
		} catch (ConfigurationException e) {

		}

		return config;
	}
}
