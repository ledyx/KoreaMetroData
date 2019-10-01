package io.github.devwillee.koreametrographdata.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.devwillee.koreametrographdata.core.graph.Vertex;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Station implements Vertex<Station> {
    @JsonProperty("station_nm")
    private String stationName;

    @JsonProperty("sub_station_nm")
    private String subStationName;

    @JsonProperty("line_num")
    private String lineNum;

    private Identifier identifier = Identifier.CURRENT;
    private boolean mainLine = true;

    @JsonProperty("fr_code")
    private String foreignCode;

    @JsonProperty("station_cd")
    private String stationCode;
    @JsonProperty("xpoint_wgs")
    private double latitude;
    @JsonProperty("ypoint_wgs")
    private double longitude;

    @JsonProperty("station_nm_han")
    private String stationName_han;
    @JsonProperty("station_nm_eng")
    private String stationName_eng;

    @JsonProperty("sub_station_nm_han")
    private String subStationName_han;
    @JsonProperty("sub_station_nm_eng")
    private String subStationName_eng;

    private Station() {
    }

    public Station(String stationName, String lineNum) {
        this.stationName = stationName;
        this.lineNum = lineNum;
    }

    public Station(String stationName, String lineNum, Identifier identifier) {
        this.stationName = stationName;
        this.lineNum = lineNum;
        this.identifier = identifier;
    }

    @Override
    public int compareTo(Station o) {
        return this.stationName.compareTo(o.stationName);
    }
}