package ratelimit;

public class ApiLimit {

private Methods methods = new Methods();
private String api = "";

public Methods getMethods() {
return methods;
}

public void setMethods(Methods methods) {
this.methods = methods;
}

public String getApi() {
return api;
}

public void setApi(String api) {
this.api = api;
}

}