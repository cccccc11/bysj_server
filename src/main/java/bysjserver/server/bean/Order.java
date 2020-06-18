package bysjserver.server.bean;

public class Order {
    private String userId;
    private Integer commodityId,num;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
