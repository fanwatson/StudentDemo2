package tw.com.watsonfan.studentdemo;

public class BusItem {

    private String station;
    private String startS;
    private String endS;
    private String Route;
    private String Rid;

    public String getRid() {
        return Rid;
    }

    public void setRid(String rid) {
        Rid = rid;
    }


    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStartS() {
        return startS;
    }

    public void setStartS(String startS) {
        this.startS = startS;
    }

    public String getEndS() {
        return endS;
    }

    public void setEndS(String endS) {
        this.endS = endS;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }
}




