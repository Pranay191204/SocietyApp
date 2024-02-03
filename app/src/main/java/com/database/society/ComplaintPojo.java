package com.database.society;

/**
 * Created by Admin on 6/14/2019.
 */

public class ComplaintPojo {

    public String complaint_id;
    public String unitno,complaintby,d,description,severity,remark;


    public String getUnitno() {
        return unitno;
    }

    public void setUnitno(String unitno) {
        this.unitno = unitno;
    }

    public String getComplaintby() {
        return complaintby;
    }

    public void setComplaintby(String complaintby) {
        this.complaintby = complaintby;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ComplaintPojo() {

    }

    public ComplaintPojo(String complaint_id) {
        this.complaint_id = complaint_id;

    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }
}
