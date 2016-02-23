/**
 * The AddressService class
 *
 * This class handles various tasks for addresses
 * @author: Christopher Reeves <chrisreeves12@yahoo.com>
 */

package com.taktyx.service;

import com.taktyx.model.Address;
import com.taktyx.model.Location;
import com.taktyx.service.enums.ServiceResultType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;

public class AddressService extends AbstractService
{
  private final String api_endpoint = "http://maps.google.com/maps/api/geocode/json";

  private HttpService httpService;

  public void init(ServiceLocator serviceLocator)
  {
    httpService = (HttpService) serviceLocator.get(HttpService.class);
  }

  /**
   * Creates a location with coordinates from an address
   * @param address
   * @return
   */
  public ServiceResult createLocation(Address address)
  {
    ServiceResult serviceResult = new ServiceResult();
    Location location = new Location();

    // Get address into one string
    String addressString = address.getAddress1() + ", " +
      address.getCity() + ", " +
      address.getState();

    // Set request data
    HashMap<String, String> requestParams = new HashMap<>();
    requestParams.put("address", addressString);
    requestParams.put("sensor", "false");

    // Get longitude and latitude coordinates from API
    ServiceResult geoLocationResponse = httpService.sendRequest(api_endpoint, requestParams, "GET");

    if (geoLocationResponse.isSuccess())
    {
      // Parse the response to get the coordinates to build the location
      String responseString = (String) geoLocationResponse.getData();
      JSONParser jsonParser = new JSONParser();

      try
      {
        JSONObject obj = (JSONObject) jsonParser.parse(responseString);
        JSONArray jsonArray = (JSONArray) obj.get("results");
        JSONObject locationObj = (JSONObject) ((JSONObject) ((JSONObject) jsonArray.get(0)).get("geometry")).get("location");
        Double longitude = (Double) locationObj.get("lng");
        Double latitude = (Double) locationObj.get("lat");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setDateCreated(null);
        location.setDateModified(null);
        serviceResult.setSuccess(true);
        serviceResult.setData(location);
      }
      catch (Exception ex)
      {
        // An error occurred while parsing the JSON response
        serviceResult.setSuccess(false);
        serviceResult.setData(ex.getMessage());
        serviceResult.setResultType(ServiceResultType.PARSE_ERROR);
      }
    }
    else
    {
      // An error occurred during the connection
      serviceResult = geoLocationResponse;
    }

    // Return results
    return serviceResult;
  }
}
