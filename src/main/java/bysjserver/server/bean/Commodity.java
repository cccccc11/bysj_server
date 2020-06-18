package bysjserver.server.bean;

import java.sql.Date;

public class Commodity {
    private String name,shopId,introduce,state,photosPath;
    private Integer id,number;
    private Double price;
    private Date startDate,endDate;

    @Override
    public String toString() {
        return "Commodity{" +
                "name='" + name + '\'' +
                ", shopId='" + shopId + '\'' +
                ", introduce='" + introduce + '\'' +
                ", state='" + state + '\'' +
                ", photosPath='" + photosPath + '\'' +
                ", id=" + id +
                ", number=" + number +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public String getPhotosPath() {
        return photosPath;
    }

    public void setPhotosPath(String photosPath) {
        this.photosPath = photosPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
