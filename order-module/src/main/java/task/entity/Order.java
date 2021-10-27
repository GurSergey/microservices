package task.entity;


public class Order {
    long id;
    long status;
    long client;

    public Order(long id, long status, long client) {
        this.id = id;
        this.status = status;
        this.client = client;
    }
}
