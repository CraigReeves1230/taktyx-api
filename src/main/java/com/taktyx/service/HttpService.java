/**
 * The HttpService class
 *
 * This service has helper methods that help to make HTTP connections
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import com.taktyx.service.enums.ServiceResultType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpService extends AbstractService
{
  /**
   * Sends an HTTP response to the passed in URL
   * @param url_string
   * @param requestParams
   * @param httpMethod
   * @return
   */
  public ServiceResult sendRequest(String url_string, HashMap<String, String> requestParams, String httpMethod)
  {
    ServiceResult serviceResult = new ServiceResult();
    StringBuilder requestUrl = new StringBuilder();

    try
    {
      // Create url from request parameters
      int counter = 0;
      if (requestParams != null)
      {
        for (Map.Entry<String, String> mapEntry : requestParams.entrySet())
        {
          if (counter == 0)
          {
            requestUrl.append(url_string).append("?");
          }
          else
          {
            requestUrl.append("&");
          }

          // Add parameter
          requestUrl.append(mapEntry.getKey()).append("=").append(URLEncoder.encode(mapEntry.getValue(), "UTF-8"));

          counter++;
        }
      }

      // Create the URL object and open the connection
      String requestURLString = requestUrl.toString();
      URL url = new URL(requestURLString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      // Set the method
      connection.setRequestMethod(httpMethod);

      // Add headers
      connection.setRequestProperty("User-Agent", "USER_AGENT");

      // Create reader and get request
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      // Create response string
      String inputLine;
      StringBuilder responseString = new StringBuilder();

      // Build the response string from the stream
      while ((inputLine = bufferedReader.readLine()) != null)
      {
        responseString.append(inputLine);
      }

      // Return response in the form of a service response
      serviceResult.setSuccess(true);
      serviceResult.setData(responseString.toString());
    }
    catch (Exception ex)
    {
      // Something went wrong with making the connection
      serviceResult.setSuccess(false);
      serviceResult.setResultType(ServiceResultType.HTTP_ERROR);
      serviceResult.setData(ex.getMessage());
    }

    return serviceResult;
  }
}
