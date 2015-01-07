package com.ctanas.android.sectionedlist.data;


public class PublicTransportStation {

    private String commonName;
    private String hashValue;

    public PublicTransportStation() { }

    public PublicTransportStation(String withCommonName) {
        this.commonName = withCommonName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

}
