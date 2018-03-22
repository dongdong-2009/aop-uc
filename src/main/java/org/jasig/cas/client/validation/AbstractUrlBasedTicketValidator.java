package org.jasig.cas.client.validation;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.util.CommonUtils;

public abstract class AbstractUrlBasedTicketValidator
  implements TicketValidator
{
  protected final Log log = LogFactory.getLog(getClass());
  protected HostnameVerifier hostnameVerifier;
  private final String casServerUrlPrefix;
  private boolean renew;
  private Map<String, String> customParameters;
  private String encoding;

  protected AbstractUrlBasedTicketValidator(String casServerUrlPrefix)
  {
    this.casServerUrlPrefix = casServerUrlPrefix;
    CommonUtils.assertNotNull(this.casServerUrlPrefix, "casServerUrlPrefix cannot be null.");
  }

  protected void populateUrlAttributeMap(Map<String, String> urlParameters)
  {
  }

  protected abstract String getUrlSuffix();

  protected abstract void setDisableXmlSchemaValidation(boolean paramBoolean);

  protected final String constructValidationUrl(String ticket, String serviceUrl)
  {
    Map<String,String> urlParameters = new HashMap();

    this.log.debug("Placing URL parameters in map.");
    urlParameters.put("ticket", ticket);
    urlParameters.put("service", encodeUrl(serviceUrl));

    if (this.renew) {
      urlParameters.put("renew", "true");
    }

    this.log.debug("Calling template URL attribute map.");
    populateUrlAttributeMap(urlParameters);

    this.log.debug("Loading custom parameters from configuration.");
    if (this.customParameters != null) {
      urlParameters.putAll(this.customParameters);
    }

    String suffix = getUrlSuffix();
    StringBuilder buffer = new StringBuilder(urlParameters.size() * 10 + this.casServerUrlPrefix.length() + suffix.length() + 1);

    int i = 0;

    buffer.append(this.casServerUrlPrefix);
    if (!this.casServerUrlPrefix.endsWith("/")) {
      buffer.append("/");
    }
    buffer.append(suffix);
    
    for (Map.Entry entry : urlParameters.entrySet()) {
      String key = (String)entry.getKey();
      String value = (String)entry.getValue();

      if (value != null) {
        buffer.append(i++ == 0 ? "?" : "&");
        buffer.append(key);
        buffer.append("=");
        buffer.append(value);
      }
    }

    return buffer.toString();
  }

  protected final String encodeUrl(String url)
  {
    if (url == null) {
      return null;
    }
    try
    {
      return URLEncoder.encode(url, "UTF-8"); } catch (UnsupportedEncodingException e) {
    }
    return url;
  }

  protected abstract Assertion parseResponseFromServer(String paramString)
    throws TicketValidationException;

  protected abstract String retrieveResponseFromServer(URL paramURL, String paramString);

  public Assertion validate(String ticket, String service)
    throws TicketValidationException
  {
    String validationUrl = constructValidationUrl(ticket, service);
    if (this.log.isDebugEnabled()) {
      this.log.debug("Constructing validation url: " + validationUrl);
    }
    try
    {
      this.log.debug("Retrieving response from server.");
      String serverResponse = retrieveResponseFromServer(new URL(validationUrl), ticket);

      if (serverResponse == null) {
        throw new TicketValidationException("The CAS server returned no response.");
      }

      if (this.log.isDebugEnabled()) {
        this.log.debug("Server response: " + serverResponse);
      }

      return parseResponseFromServer(serverResponse);
    } catch (MalformedURLException e) {
      throw new TicketValidationException(e);
    }
  }

  public final void setRenew(boolean renew) {
    this.renew = renew;
  }

  public final void setCustomParameters(Map<String, String> customParameters) {
    this.customParameters = customParameters;
  }

  public final void setHostnameVerifier(HostnameVerifier verifier) {
    this.hostnameVerifier = verifier;
  }

  public final void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  protected final String getEncoding() {
    return this.encoding;
  }
}