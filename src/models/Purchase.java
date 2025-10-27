package models;

import java.sql.Timestamp;

public class Purchase {

    private int id;
    private int userId;
    private int plotId;
    private double pricePaid;
    private Timestamp purchasedAt;
    private String plotTitle;

    
    private String ownerName;
    private String ownerContact;

    
    public Purchase() {}

    public Purchase(int id, int userId, int plotId, double pricePaid, Timestamp purchasedAt, String plotTitle) {
        this.id = id;
        this.userId = userId;
        this.plotId = plotId;
        this.pricePaid = pricePaid;
        this.purchasedAt = purchasedAt;
        this.plotTitle = plotTitle;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getPlotId() { return plotId; }
    public void setPlotId(int plotId) { this.plotId = plotId; }

    public double getPricePaid() { return pricePaid; }
    public void setPricePaid(double pricePaid) { this.pricePaid = pricePaid; }

    public Timestamp getPurchasedAt() { return purchasedAt; }
    public void setPurchasedAt(Timestamp purchasedAt) { this.purchasedAt = purchasedAt; }

    public String getPlotTitle() { return plotTitle; }
    public void setPlotTitle(String plotTitle) { this.plotTitle = plotTitle; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getOwnerContact() { return ownerContact; }
    public void setOwnerContact(String ownerContact) { this.ownerContact = ownerContact; }
}