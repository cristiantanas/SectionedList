package com.ctanas.android.sectionedlist.model;


public class StringHeaderItem implements SectionedListItem {

    private String headerTitle;

    public StringHeaderItem(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    @Override
    public boolean isHeader() {
        return true;
    }
}
