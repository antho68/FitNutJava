package org.aba.rest.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aba.rest.dto.HubConnectorUpdatedRowsResponse;
import org.aba.rest.dto.ResponseDataDto;
import org.aba.rest.exeption.FitNutHttpException;
import org.aba.web.exeption.ServiceException;
import org.aba.web.utils.CommonContextHolder;
import org.aba.web.utils.CommonUtils;
import org.aba.web.utils.ConstantsWeb;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;


/**
 * REST-Client for Mago Connector interface
 *
 * @author mala
 */
@Component
public class HubConnectorWsClient implements Serializable
{
    private static final String HUB_API_URL_TEXT = "Use hubConnectorApiUrl >";

    public enum MagicLinkPostEntities
    {
        INGREDIENT;
    }

    private static final long serialVersionUID = 8527612060790164162L;
    private static final Logger logger = LoggerFactory.getLogger(HubConnectorWsClient.class);

    @Value("${hubConnector.rest.uri:#{null}}")
    private String restServiceUri;

    private RestTemplate restTemplate;

    public HubConnectorWsClient(RestTemplate restTemplate, CloseableHttpClient hubConnectorHttpClient)
    {
        this.restTemplate = restTemplate;
        init(hubConnectorHttpClient);
    }

    /**
     * init client with given http client and uri
     *
     * @param hubConnectorHttpClient
     */
    private void init(CloseableHttpClient hubConnectorHttpClient)
    {
        long start = System.currentTimeMillis();

        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG, "Init HubConnector client");

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(hubConnectorHttpClient);
        restTemplate.setRequestFactory(requestFactory);

        CommonUtils.logDebug(ConstantsWeb.DEBUG_LOG
                , "Init HubConnector client done - execution time: " + (System.currentTimeMillis() - start) + " ms.");
    }

    /**
     * get HubConnectorUpdatedRowsResponse for given hubConnectorApiUrl and body
     *
     * @param pk
     * @param entity
     * @param hubConnectorApiUrl
     * @return
     * @throws Exception
     */
    private HubConnectorUpdatedRowsResponse postModifiedEntityForUpdatedRowsResponse(Long pk, String entity
            , String hubConnectorApiUrl) throws ServiceException
    {
        CommonUtils.logDebug(logger, HUB_API_URL_TEXT + hubConnectorApiUrl + "<.");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode node = JsonNodeFactory.instance.objectNode();

        String userId = "userId"; //(String) CommonContextHolder.getUserId();
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.USER_ID, userId);
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.GUI_LOG_ID, CommonContextHolder.getId());
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.ENTITY_NAME, entity);
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.PK, pk);

        HttpEntity<ObjectNode> requestEntity = new HttpEntity<>(node, requestHeaders);

        try
        {
            HubConnectorUpdatedRowsResponse HubConnectorResponse = postForEntity(restServiceUri + hubConnectorApiUrl
                    , requestEntity, HubConnectorUpdatedRowsResponse.class).getBody();
            if (HubConnectorResponse != null)
            {
                CommonUtils.logTrace(logger, HubConnectorResponse.toString());
            }

            return HubConnectorResponse;
        }
        catch (ResourceAccessException e)
        {
            CommonUtils.logError(logger, e.getMessage());
            throw new ServiceException("communication", ConstantsWeb.Error.HTTP, restServiceUri);
        }
        catch (Exception e)
        {
            CommonUtils.logError(logger, e.getMessage());

            if (e instanceof FitNutHttpException dcaExeption)
            {
                if (HttpStatus.UNAUTHORIZED.equals(dcaExeption.getHttpStatus()))
                {
                    throw new ServiceException("UNAUTHORIZED", ConstantsWeb.Error.CONNECT, e);
                }
            }

            throw new ServiceException("common", ConstantsWeb.Error.UNKNOWN, e);
        }
    }

    private HubConnectorUpdatedRowsResponse postModifiedEntityForUpdatedRowsResponse(String params
            , String entity, String hubConnectorApiUrl) throws ServiceException
    {
        CommonUtils.logDebug(logger, HUB_API_URL_TEXT + hubConnectorApiUrl + "<.");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode node = JsonNodeFactory.instance.objectNode();

        String userId = "userId"; //(String) CommonContextHolder.getUserId();
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.USER_ID, userId);
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.GUI_LOG_ID, CommonContextHolder.getId());
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.ENTITY_NAME, entity);
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.PARAMS, params);

        HttpEntity<ObjectNode> requestEntity = new HttpEntity<>(node, requestHeaders);

        try
        {
            HubConnectorUpdatedRowsResponse HubConnectorResponse = postForEntity(restServiceUri + hubConnectorApiUrl
                    , requestEntity, HubConnectorUpdatedRowsResponse.class).getBody();
            if (HubConnectorResponse != null)
            {
                CommonUtils.logTrace(logger, HubConnectorResponse.toString());
            }

            return HubConnectorResponse;
        }
        catch (ResourceAccessException e)
        {
            CommonUtils.logError(logger, e.getMessage());
            throw new ServiceException("communication", ConstantsWeb.Error.HTTP, restServiceUri);
        }
        catch (Exception e)
        {
            CommonUtils.logError(logger, e.getMessage());

            if (e instanceof FitNutHttpException fitNutHttpExeption)
            {
                if (HttpStatus.UNAUTHORIZED.equals(fitNutHttpExeption.getHttpStatus()))
                {
                    throw new ServiceException("UNAUTHORIZED", ConstantsWeb.Error.CONNECT, e);
                }
            }

            throw new ServiceException("common", ConstantsWeb.Error.UNKNOWN, e);
        }
    }

    /**
     * post with entity
     *
     * @param <T>
     * @param url
     * @param request
     * @param responseType
     * @return
     * @throws Exception
     */
    protected <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType) throws Exception
    {
        HttpEntity<?> requestEntity = new HttpEntity<>(request);
        return postForEntity(url, requestEntity, responseType);
    }

    /**
     * post http entity
     *
     * @param <T>
     * @param url
     * @param requestEntity
     * @param responseType
     * @return
     * @throws Exception
     */
    protected <T> ResponseEntity<T> postForEntity(String url, HttpEntity<?> requestEntity, Class<T> responseType) throws Exception
    {
        CommonUtils.logDebug(logger, "Start HTTP Post!");
        ResponseEntity<T> response = restTemplate.postForEntity(url, requestEntity, responseType);
        CommonUtils.logDebug(logger, "Done HTTP post with status code " + response.getStatusCode().toString() + "!");

        return response;
    }

    private ResponseDataDto getEntitiesFromHub(String params, String hubEndPointAndFilter) throws ServiceException
    {
        String restUri = restServiceUri + hubEndPointAndFilter;

        CommonUtils.logDebug(logger, HUB_API_URL_TEXT + restUri + "<.");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode node = JsonNodeFactory.instance.objectNode();

        String userId = "userId"; //(String) CommonContextHolder.getUserId();
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.USER_ID, userId);
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.GUI_LOG_ID, CommonContextHolder.getId());
        node.put(ConstantsWeb.Endpoints.HubConnector.Properties.PARAMS, params);

        try
        {
            ResponseDataDto HubConnectorResponse =
                    getForEntity(restUri, ResponseDataDto.class).getBody();
            if (HubConnectorResponse != null)
            {
                CommonUtils.logTrace(logger, HubConnectorResponse.toString());
            }

            return HubConnectorResponse;
        }
        catch (ResourceAccessException e)
        {
            CommonUtils.logError(logger, e.getMessage());
            throw new ServiceException("communication", ConstantsWeb.Error.HTTP, restServiceUri);
        }
        catch (Exception e)
        {
            CommonUtils.logError(logger, e.getMessage());

            if (e instanceof FitNutHttpException fitNutExeption)
            {
                if (HttpStatus.UNAUTHORIZED.equals(fitNutExeption.getHttpStatus()))
                {
                    throw new ServiceException("UNAUTHORIZED", ConstantsWeb.Error.CONNECT, e);
                }
            }

            throw new ServiceException("common", ConstantsWeb.Error.UNKNOWN, e);
        }
    }

    protected <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) throws Exception
    {
        CommonUtils.logDebug(logger, "Start HTTP Get!");
        ResponseEntity<T> response = restTemplate.getForEntity(url, responseType);
        CommonUtils.logDebug(logger, "Done HTTP get with status code " + response.getStatusCode().toString() + "!");

        return response;
    }

    public <T> ResponseEntity<T> postRequest(String url, T object, Class<T> responseType) throws Exception
    {
        CommonUtils.logDebug(logger, "Start HTTP Post!");

        URI uri = new URI(restServiceUri + url);
        ResponseEntity<T> response = restTemplate.postForEntity(uri, object, responseType);
        CommonUtils.logDebug(logger, "Done HTTP post with status code "
                + response.getStatusCode().toString() + "!");

        return response;
    }
}