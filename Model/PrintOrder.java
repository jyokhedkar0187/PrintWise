package com.projectf.Model;

public class PrintOrder {
    public String orderId;
    public String fileName;
    public String paperSize;
    public int copies;
    public String colorMode;
    public double totalPrice;
    public String dateTime;
    public String status;

   public PrintOrder() {}  // Firebase needs no-arg constructor

    public PrintOrder(String orderId, String fileName, String paperSize, int copies, String colorMode, double totalPrice, String dateTime, String status) {
        this.orderId = orderId;
        this.fileName = fileName;
        this.paperSize = paperSize;
        this.copies = copies;
        this.colorMode = colorMode;
        this.totalPrice = totalPrice;
        this.dateTime = dateTime;
        this.status = status;
    }

  
}

