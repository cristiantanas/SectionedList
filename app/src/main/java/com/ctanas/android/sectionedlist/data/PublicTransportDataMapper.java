package com.ctanas.android.sectionedlist.data;


import com.ctanas.android.sectionedlist.model.PublicTransportStationItem;
import com.ctanas.android.sectionedlist.model.StringHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class PublicTransportDataMapper {

    public PublicTransportDataMapper() {  }

    public StringHeaderItem transform(PublicTransportLine line) {

        StringHeaderItem headerItem = null;

        if ( line != null ) {
            headerItem = new StringHeaderItem( line.getLineCommonName() );
        }

        return headerItem;
    }

    public PublicTransportStationItem transform(PublicTransportStation station) {

        PublicTransportStationItem stationItem = null;

        if ( station != null ) {
            stationItem = new PublicTransportStationItem(station.getCommonName());
        }

        return stationItem;
    }

    public List<PublicTransportStationItem> transform(List<PublicTransportStation> stationCollection) {

        List<PublicTransportStationItem> stationItemList = new ArrayList<PublicTransportStationItem>();

        PublicTransportStationItem stationItem;
        for ( PublicTransportStation station : stationCollection ) {
            stationItem = this.transform(station);
            if ( stationItem != null ) {
                stationItemList.add(stationItem);
            }
        }

        return stationItemList;
    }
}
