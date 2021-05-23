package com.kog.mypage.gateway.filter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kog.mypage.gateway.util.UserInfo;
import com.kog.mypage.gateway.util.TokenProvider;
import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.*;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;


@Component
public class TokenAuthenticationFilter extends AbstractGatewayFilterFactory<TokenAuthenticationFilter.Config> {

    @Value("${app.auth.tokenHeaderName}")
    private String tokenHeaderName;

    private final TokenProvider tokenProvider;

    public TokenAuthenticationFilter(TokenProvider tokenProvider) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
    }

    @Getter
    @AllArgsConstructor
    public static class Config {
        private String role;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token;
            try {
                token = tokenProvider.getToken(exchange.getRequest());
            } catch (NullPointerException e) {
                return failureAuthorized(exchange);
            }
            UserInfo userInfo = tokenProvider.getInfo(token);

            if (userInfo.isExpired() || !userInfo.getRoles().contains(config.getRole()))
                return failureAuthorized(exchange);

            ServerHttpRequest request = exchange.getRequest();
            HttpMethod method = request.getMethod();

            removeDataToHeader(exchange, chain, tokenHeaderName);

            if (method == HttpMethod.GET || method == HttpMethod.DELETE)
                return addDataToParameter(exchange, chain, userInfo);
            else if(method == HttpMethod.POST || method == HttpMethod.PUT)
                return addDataToBody(exchange, chain, "userInfo", userInfo);

            return chain.filter(exchange);
        };
    }

    private Mono<Void> removeDataToHeader(ServerWebExchange exchange, GatewayFilterChain chain, String headerName){
        RemoveRequestHeaderGatewayFilterFactory filterFactory =
                new RemoveRequestHeaderGatewayFilterFactory();
        NameConfig config = new  NameConfig();
        config.setName(headerName);

        return filterFactory.apply(config).filter(exchange, chain);
    }


    private Mono<Void> addDataToParameter(ServerWebExchange exchange, GatewayFilterChain chain, Object data){
        URI uri = exchange.getRequest().getURI();
        StringBuilder query = new StringBuilder();
        String originalQuery = uri.getRawQuery();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> datas = mapper.convertValue(data, Map.class);

        if (StringUtils.hasText(originalQuery))
            query.append(originalQuery);

        for (Map.Entry<String, Object> entry : datas.entrySet()){

            if (StringUtils.hasText(query)) {
                if (query.charAt(query.length() - 1) != '&')
                    query.append('&');
            }

            Object tmp = entry.getValue();
            String value;
            if(tmp instanceof Collection)
                value = convertCollectionToValue((Collection) tmp);
            else
                value = ServerWebExchangeUtils.expand(exchange, String.valueOf(tmp));

            query.append(entry.getKey());
            query.append('=');
            query.append(value);
        }
        System.out.println(query.toString());
        try {
            URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(query.toString()).build(true).toUri();
            ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
            return chain.filter(exchange.mutate().request(request).build());
        } catch (RuntimeException var9) {
            throw new IllegalStateException("Invalid URI query: \"" + query.toString() + "\"");
        }
    }

    private Mono<Void> addDataToBody(ServerWebExchange exchange, GatewayFilterChain chain, String key, Object data){
        ModifyRequestBodyGatewayFilterFactory filterFactory =
                new ModifyRequestBodyGatewayFilterFactory();

        ModifyRequestBodyGatewayFilterFactory.Config config =
                new ModifyRequestBodyGatewayFilterFactory.Config()
                .setContentType(ContentType.APPLICATION_JSON.getMimeType())
                .setRewriteFunction(Map.class, Map.class,
                        (exchange1, RequestBody) -> {
                            RequestBody.put(key,data);
                            return Mono.just(RequestBody);
                        });

        return filterFactory.apply(config).filter(exchange, chain);
    }

    private Mono<Void> failureAuthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private String convertCollectionToValue(Collection collection){
        if (collection instanceof List) //기본타입 가진 1차원리스트만 됨
            return collection.toString().replaceAll("\\]|\\[|\\\\s","");
        else
            return null;
    }
}



