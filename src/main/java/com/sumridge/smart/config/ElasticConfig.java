package com.sumridge.smart.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by liu on 16/8/10.
 */

@Configuration
public class ElasticConfig {

    @Bean(name = "elasticClient")
    @Qualifier("elasticClient")
    public Client elasticClient(@Value("${elastic.host}") String host, @Value("${elastic.port}") int port) throws UnknownHostException {
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

        return client;
    }

}
