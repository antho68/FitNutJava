package org.aba.rest.client;

import org.aba.rest.exeption.FitNutHttpResponseErrorHandler;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;


/**
 * Configuration for HUB Connector client
 *
 * @author aba
 */
@Configuration
public class HubConnectorWsClientConfig implements Serializable
{
    private static final long serialVersionUID = 1252492060408955079L;

    @Value("${hubConnector.rest.timeout:15}")
    private int hubConnectorTimeout; // timeout in seconds
    @Value("${hubConnector.rest.user:#{null}}")
    private String hubConnectorTechUser;
    @Value("${hubConnector.rest.password:#{null}}")
    private String hubConnectorTechPassword;

    @Bean
    public RestTemplate hubConnectorRestTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("hubConnectorHttpClient")
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    @Lazy
    public CloseableHttpClient hubConnectorHttpClient()
    {
        long start = System.currentTimeMillis();

        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, "Creating hubConnectorHttpClient client");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(hubConnectorTimeout * 1000).setSocketTimeout(hubConnectorTimeout * 1000).build();

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(hubConnectorTechUser, hubConnectorTechPassword);
        provider.setCredentials(AuthScope.ANY, credentials);

        HttpClientBuilder builder = HttpClients.custom();

        builder.setMaxConnTotal(5).setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(provider);

        CloseableHttpClient httpClient = builder.build();

        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG
                , "Creating hubConnectorHttpClient client done - execution time: "
                        + (System.currentTimeMillis() - start) + " ms.");

        return httpClient;
    }

    @Bean
    @Qualifier("hubConnectorWsClient")
    @Lazy
    public HubConnectorWsClient hubConnectorWsClient(RestTemplate hubConnectorRestTemplate, CloseableHttpClient hubConnectorHttpClient)
    {
        long start = System.currentTimeMillis();

        if (hubConnectorHttpClient == null)
        {
            CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, "Creating magoConnector client impossible - no hubConnectorHttpClient given!");
            return null;
        }

        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, "Creating magoConnectorWsClient");

        hubConnectorRestTemplate.setErrorHandler(new FitNutHttpResponseErrorHandler());

        HubConnectorWsClient hubConnectorClient = new HubConnectorWsClient(hubConnectorRestTemplate, hubConnectorHttpClient);

        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG
                , "Creating hubConnectorHttpClient done - execution time: " + (System.currentTimeMillis() - start) + " ms.");
        return hubConnectorClient;
    }
}