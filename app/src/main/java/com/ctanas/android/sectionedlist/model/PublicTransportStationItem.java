package com.ctanas.android.sectionedlist.model;


import com.ctanas.android.sectionedlist.data.PublicTransportStation;

public class PublicTransportStationItem implements SectionedListItem {

    private String commonName;

    public PublicTransportStationItem(String commonName) {
        this.commonName = commonName;
    }

    public PublicTransportStationItem from(PublicTransportStation station) {
        this.commonName = station.getCommonName();
        return this;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
