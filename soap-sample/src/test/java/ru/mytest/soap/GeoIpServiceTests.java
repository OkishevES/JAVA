package ru.mytest.soap;

import com.lavasoft.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("146.185.157.238");
    assertEquals(ipLocation, "<GeoIP><Country>NL</Country><State>07</State></GeoIP>");
  }

  @Test
  public void testInvalidIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("146.185.157.xxx");
    assertEquals(ipLocation, "<GeoIP><Country>NL</Country><State>07</State></GeoIP>");
  }
}
