package models;

public class Plot {
    private int id;
    private String title;
    private String address;
    private double acres;
    private double price;
    private String status;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;

    
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public double getAcres() {return acres;}
    public void setAcres(double acres) {this.acres = acres;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public String getOwnerName() {return ownerName;}
    public void setOwnerName(String ownerName) {this.ownerName = ownerName;}
    public String getOwnerPhone() {return ownerPhone;}
    public void setOwnerPhone(String ownerPhone) {this.ownerPhone = ownerPhone;}
    public String getOwnerEmail() {return ownerEmail;}
    public void setOwnerEmail(String ownerEmail) {this.ownerEmail = ownerEmail;}
}