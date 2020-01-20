package ratelimit;

public class TypeConfig {

private Integer limit = 0;
private String granularity = "";

public Integer getLimit() {
return limit;
}

public void setLimit(Integer limit) {
this.limit = limit;
}

public String getGranularity() {
return granularity;
}

public void setGranularity(String granularity) {
this.granularity = granularity;
}

}