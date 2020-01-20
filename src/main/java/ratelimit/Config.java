package ratelimit;

import java.util.ArrayList;
import java.util.List;

public class Config {

private List<ServiceLimit> serviceLimits = new ArrayList<>();

public List<ServiceLimit> getServiceLimits() {
return serviceLimits;
}

public void setServiceLimits(List<ServiceLimit> serviceLimits) {
this.serviceLimits = serviceLimits;
}

}