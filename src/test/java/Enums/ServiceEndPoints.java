package APIEndPoints;

public enum ServiceEndPoints {

    CreateSalesOrder("/maps/api/place/add/json"),
    CheckOut("/maps/api/place/add/json"),
    Payment("/maps/api/place/add/json");


    private String endPoint;
    ServiceEndPoints(String endPoint)
    {
        this.endPoint = endPoint;
    }

    public String getEndPoint()
    {
        return endPoint;
    }
}
