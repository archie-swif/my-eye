package com.ryabokon.myeye.capture;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientFactory {

	private static HttpClient client;
	private static PoolingHttpClientConnectionManager manager;

	public static synchronized HttpClient getHttpClient() {

		if (client == null) {
			RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
			manager = new PoolingHttpClientConnectionManager();
			client = HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(config).build();
		}

		return client;
	}

}
