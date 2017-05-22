package kr.ac.cnu.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rokim on 2017. 5. 21..
 */
@Configuration
public class HttpClientConfig {
    private int httpTimeoutMillis = 3000;
    private int httpConnectionPoolSize = 100;

    @Bean
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(httpConnectionPoolSize);
        connectionManager.setDefaultMaxPerRoute(httpConnectionPoolSize);
        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();
        return httpClient;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient());
        factory.setReadTimeout(httpTimeoutMillis);
        factory.setConnectTimeout(httpTimeoutMillis);
        return factory;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

}
