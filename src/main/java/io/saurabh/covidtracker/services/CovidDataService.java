package io.saurabh.covidtracker.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.saurabh.covidtracker.models.*;
import io.saurabh.covidtracker.models.StateData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CovidDataService {
    public static final String TODAY_STR = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    public static final String YstDAY_STR = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> locationStatsList = new ArrayList<>();

    public List<LocationStats> getLocationStatsList() {
        return locationStatsList;
    }

    @PostConstruct
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader in = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        List<LocationStats> locationStats = new ArrayList<>();
        for (CSVRecord record : records) {
            LocationStats stats = new LocationStats();
            stats.setCountry(record.get("Country/Region"));
            stats.setState(record.get("Province/State"));
            int latestTotalCases = Integer.parseInt(record.get(record.size() - 1));
            int preDayCases = Integer.parseInt(record.get(record.size() - 2));
            stats.setLatestTotalCases(latestTotalCases);
            stats.setPreDayCasesDiff(latestTotalCases - preDayCases);
            locationStats.add(stats);
        }
        this.locationStatsList = locationStats;
    }

    @PostConstruct
    public void fetchIndiaVirusData() throws IOException {
        String sURL = "https://data.covid19india.org/v4/min/timeseries.min.json"; //just a string

        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        HashMap<String, Object> map;
        map = objectMapper.readValue(url, new TypeReference<>() {
        });
        System.out.println("map Size ----" + map.size());
        List<StateData> response = new ArrayList<>();
        for (String state : map.keySet()) {
            System.out.println("state = " + state);
            HashMap<String, Object> dates = (HashMap<String, Object>) map.get(state);
            LinkedHashMap<String, Object> date = (LinkedHashMap<String, Object>) dates.get("dates");
            LinkedHashMap<String, Object> data;
            Delta delta1 = new Delta();
            Delta delta2 = new Delta();
            Delta delta3 = new Delta();
            String dateString;
            if (date.get(TODAY_STR) != null) {
                data = (LinkedHashMap<String, Object>) date.get(TODAY_STR);
                dateString = TODAY_STR;
                System.out.println("Data for state " + state + " is available for today");
            } else {
                data = (LinkedHashMap<String, Object>) date.get(YstDAY_STR);
                dateString = YstDAY_STR;
                System.out.println("Data for state " + state + " is available for yesterday");
            }
            if (data != null) {
                for (String s : data.keySet()) {
                    if (Objects.equals(s, "delta")) {
                        HashMap<String, Object> delta1Map = (HashMap<String, Object>) data.get("delta");
                        System.out.println(delta1Map);
                        delta1 = new Delta(delta1Map.get("confirmed"), delta1Map.get("recovered"),
                                delta1Map.get("deceased"), delta1Map.get("tested"),
                                delta1Map.get("vaccinated1"), delta1Map.get("vaccinated2"), delta1Map.get("other"));
                        System.out.println(" ---Delta1--- Data for state " + state + " is copied for " + dateString);
                    } else if (Objects.equals(s, "delta7")) {
                        HashMap<String, Object> delta2Map = (HashMap<String, Object>) data.get("delta7");
                        System.out.println(delta2Map);
                        delta2 = new Delta(delta2Map.get("confirmed"), delta2Map.get("recovered"),
                                delta2Map.get("deceased"), delta2Map.get("tested"),
                                delta2Map.get("vaccinated1"), delta2Map.get("vaccinated2"), delta2Map.get("other"));
                        System.out.println(" ---Delta2--- Data for state " + state + " is copied for " + dateString);
                    } else {
                        HashMap<String, Object> delta3Map = (HashMap<String, Object>) data.get("total");
                        System.out.println(delta3Map);
                        delta3 = new Delta(delta3Map.get("confirmed"), delta3Map.get("recovered"),
                                delta3Map.get("deceased"), delta3Map.get("tested"),
                                delta3Map.get("vaccinated1"), delta3Map.get("vaccinated2"), delta3Map.get("other"));
                        System.out.println(" ---Delta3--- Data for state " + state + " is copied for " + dateString);
                    }
                }
            } else {
                System.out.println("Data for state " + state + " is null");
            }
            StateData res = new StateData(dateString, state, delta1, delta2, delta3);
            System.out.println("----------Final response " + res);
            if (state == "TT")
                this.indiaData = res;
            else
                response.add(res);
        }
        this.stateDataList = response;
    }

    private StateData indiaData = new StateData();
    private List<StateData> stateDataList = new ArrayList<>();

    public List<StateData> getStateDataList() {
        return stateDataList;
    }

    public StateData getIndiaData() {
        return indiaData;
    }
}
