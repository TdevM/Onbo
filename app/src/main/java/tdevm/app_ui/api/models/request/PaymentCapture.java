package tdevm.app_ui.api.models.request;

public class PaymentCapture {
    private String order_id;
    private String payment_id;
    private String restaurant_id;


    public PaymentCapture(String order_id, String payment_id, String restaurant_id) {
        this.order_id = order_id;
        this.payment_id = payment_id;
        this.restaurant_id = restaurant_id;
    }


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
