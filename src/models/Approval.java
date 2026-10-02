/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

public class Approval {
    
    private int requestId;
    private String staffName;
    private String module;
    private String requestType;
    private Date date;
    private String status;

    public Approval(int requestId, String staffName, String module,
                    String requestType, Date date, String status) {
        this.requestId = requestId;
        this.staffName = staffName;
        this.module = module;
        this.requestType = requestType;
        this.date = date;
        this.status = status;
    }

    public int getRequestId() { return requestId; }
    public String getStaffName() { return staffName; }
    public String getModule() { return module; }
    public String getRequestType() { return requestType; }
    public Date getDate() { return date; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
}
