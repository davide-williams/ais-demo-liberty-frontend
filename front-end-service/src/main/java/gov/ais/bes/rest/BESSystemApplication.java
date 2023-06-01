package gov.ais.bes.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//tag::applicationPath[]
@ApplicationPath("api")
//end::applicationPath[]
//tag::systemApplication[]
public class BESSystemApplication extends Application{

}
