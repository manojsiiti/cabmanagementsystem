package ratelimit;

public class GlobalLimits {

private TypeConfig GET = new TypeConfig();
private TypeConfig POST = new TypeConfig();

public TypeConfig getGET() {
return GET;
}

public void setGET(TypeConfig gET) {
this.GET = gET;
}

public TypeConfig getPOST() {
return POST;
}

public void setPOST(TypeConfig pOST) {
this.POST = pOST;
}

}