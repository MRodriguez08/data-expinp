package com.mrdevelop.dataexpinp.sample.csvtojdbc.model;

import java.util.HashMap;
import java.util.Map;

import com.mrdevelop.dataexpinp.utils.Importable;

public class User implements Importable {

    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private String ip_address;
    private String location;
    private String domain;
    private String company;
    

    public static String[] SOURCE_FIELDS = new String[]{
                "first_name", "last_name", "email", "gender", "ip_address", "location", "domain", "company"
        };

    
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("first_name", first_name);
        map.put("last_name", last_name);
        map.put("email", email);
        map.put("gender", gender);
        map.put("ip_address", ip_address);
        map.put("location", location);
        map.put("domain", domain);
        map.put("company", company);

        return map;
    }

}
