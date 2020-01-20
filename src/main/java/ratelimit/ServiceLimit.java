package ratelimit;

import java.util.ArrayList;
import java.util.List;

public class ServiceLimit {

private String service = "";

private GlobalLimits globalLimits = new GlobalLimits();

private List<ApiLimit> apiLimits = new ArrayList<>();

public String getService() {
return service;
}

public void setService(String service) {
this.service = service;
}

public GlobalLimits getGlobalLimits() {
return globalLimits;
}

public void setGlobalLimits(GlobalLimits globalLimits) {
this.globalLimits = globalLimits;
}

public List<ApiLimit> getApiLimits() {
return apiLimits;
}

public void setApiLimits(List<ApiLimit> apiLimits) {
this.apiLimits = apiLimits;
}

}