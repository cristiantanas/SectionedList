package com.ctanas.android.sectionedlist.data;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonMapper {

    private static final String LOG_TAG = "com.ctanas.android.sectionedlist.data.JsonMapper";

    private static final String JSON_SERVICE_LINES_ARRAY = "serviceLines";

    private static final String JSON_LINE_ID = "lineId";
    private static final String JSON_LINE_DESCRIPTION = "lineDescription";
    private static final String JSON_LINE_STATIONS = "stations";

    private Context context;
    private Gson    gson;

    public JsonMapper(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public ArrayList<PublicTransportLine> arrayOfPublicTransportLinesFromJson(String jsonFile) {

        ArrayList<PublicTransportLine> listOfServiceLines = new ArrayList<PublicTransportLine>();

        JsonReader jsonReader = getJsonReaderFromAssetsFile(jsonFile);
        if ( jsonReader != null ) {

            JsonParser jsonParser = new JsonParser();
            JsonObject publicTransportInfo = jsonParser.parse(jsonReader).getAsJsonObject();

            JsonArray linesInService = publicTransportInfo.getAsJsonArray( JSON_SERVICE_LINES_ARRAY );
            for (int i = 0; i < linesInService.size(); i++) {

                JsonObject lineAsJson = (JsonObject) linesInService.get(i);

                PublicTransportLine.Builder serviceLineBuilder = new PublicTransportLine.Builder()
                        .setLineId(
                                this.gson.fromJson( lineAsJson.get(JSON_LINE_ID) , int.class)
                        )
                        .setLineCommonName(
                                this.gson.fromJson( lineAsJson.get(JSON_LINE_DESCRIPTION) , String.class)
                        );

                Type stationListType = new TypeToken<ArrayList<PublicTransportStation>>() {}.getType();
                ArrayList<PublicTransportStation> stations = this.gson.fromJson( lineAsJson.get(JSON_LINE_STATIONS) , stationListType);
                serviceLineBuilder.setListOfStations(stations);

                PublicTransportLine line = serviceLineBuilder.createPublicTransportLine();
                listOfServiceLines.add(line);
            }
        }

        return listOfServiceLines;

    }

    private JsonReader getJsonReaderFromAssetsFile(String filename) {

        try {

            InputStream inputStream = this.context.getResources().getAssets()
                    .open(filename, Context.MODE_PRIVATE);

            return new JsonReader(new InputStreamReader(inputStream));

        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        }
    }
}
