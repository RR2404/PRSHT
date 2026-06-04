//package moe.prashast.config;
//
////import java.net.http.HttpClient;
//
//import org.springframework.beans.factory.annotation.Value;
//import reactor.netty.http.client.HttpClient;
//import reactor.netty.transport.ProxyProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import org.springframework.web.reactive.function.client.WebClient;
////import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class WebClientConfig {
//
//	@Value("${proxy.host}")
//	private String proxyHost;
//
//	@Value("${proxy.port}")
//	private int proxyPort;
//	
//    @Bean
//    public WebClient webClient() {
//
//        HttpClient httpClient = HttpClient.create()
//                .proxy(proxy -> proxy
//                        .type(ProxyProvider.Proxy.HTTP)
//                        .host(proxyHost)
//                        .port(proxyPort)
//                );
//
//        return WebClient.builder()
//                .clientConnector(
//                        new ReactorClientHttpConnector(httpClient)
//                )
//                .build();
//    }
//}
