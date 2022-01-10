package enums;

public enum ServiceEndPoints {

    CreateSalesOrder("/maps/api/place/add/json"),
    GetSalesOrder("maps/api/place/get/json"),
    UpdateSalesOrder("/maps/api/place/update/json");


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
