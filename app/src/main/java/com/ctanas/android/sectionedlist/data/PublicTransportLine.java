package com.ctanas.android.sectionedlist.data;


import java.util.List;

public class PublicTransportLine {

    private int lineId;
    private String lineCommonName;
    private List<PublicTransportStation> listOfStations;

    public PublicTransportLine() {  }

    public PublicTransportLine(int lineId, String lineCommonName, List<PublicTransportStation> listOfStations) {
        this.lineId = lineId;
        this.lineCommonName = lineCommonName;
        this.listOfStations = listOfStations;
    }

    public int getLineId() {
        return lineId;
    }

    public String getLineCommonName() {
        return lineCommonName;
    }

    public List<PublicTransportStation> getListOfStations() {
        return listOfStations;
    }


    public static class Builder {

        private int lineId;
        private String lineCommonName;
        private List<PublicTransportStation> listOfStations;

        public Builder setLineId(int lineId) {
            this.lineId = lineId;
            return this;
        }

        public Builder setLineCommonName(String lineCommonName) {
            this.lineCommonName = lineCommonName;
            return this;
        }

        public Builder setListOfStations(List<PublicTransportStation> listOfStations) {
            this.listOfStations = listOfStations;
            return this;
        }

        public PublicTransportLine createPublicTransportLine() {
            return new PublicTransportLine(lineId, lineCommonName, listOfStations);
        }
    }
}
