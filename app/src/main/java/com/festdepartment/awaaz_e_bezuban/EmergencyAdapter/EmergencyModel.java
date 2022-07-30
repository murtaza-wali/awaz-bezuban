package com.festdepartment.awaaz_e_bezuban.EmergencyAdapter;

public class EmergencyModel {

    private String service_name,service_address;
    private String service_number;


    public EmergencyModel() {
    }

    public EmergencyModel(String service_name, String service_address, String service_number) {
        this.service_name = service_name;
        this.service_address = service_address;
        this.service_number = service_number;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_address() {
        return service_address;
    }

    public void setService_address(String service_address) {
        this.service_address = service_address;
    }

    public String getService_number() {
        return service_number;
    }

    public void setService_number(String service_number) {
        this.service_number = service_number;
    }
}
