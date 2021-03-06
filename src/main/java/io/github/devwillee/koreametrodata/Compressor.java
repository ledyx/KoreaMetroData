package io.github.devwillee.koreametrodata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devwillee.koreametrodata.core.Station;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Compressor {
    INSTANCE;

    private final ObjectMapper mapper = new ObjectMapper();

    public void compressVertices(String jsonPath) throws IOException {
        JsonNode root = mapper.readTree(new File(jsonPath)).get("DATA");

        List<Station> temp = mapper.convertValue(root, new TypeReference<List<Station>>() {
        });

        Collections.sort(temp);
        Collections.sort(temp, Comparator.comparing(Station::getLineNum));

        String pathWithoutExtension = getPathWithoutExtension(jsonPath);

        StringBuilder minimalSimplePath = new StringBuilder(pathWithoutExtension);
        minimalSimplePath.append(".min").append(".json");
        mapper.writer().withRootName("DATA").writeValue(new File(minimalSimplePath.toString()), temp);

        StringBuilder prettyPath = new StringBuilder(pathWithoutExtension);
        prettyPath.append(".min.pretty").append(".json");

        mapper.writerWithDefaultPrettyPrinter().withRootName("DATA").writeValue(new File(prettyPath.toString()), temp);
        mapper.writer().withRootName("DATA").writeValue(new File(minimalSimplePath.toString()), temp);
    }

    public void compressEdges(String jsonPath) throws IOException {
        JsonNode root = mapper.readTree(new File(jsonPath));

        StringBuilder simplePath = new StringBuilder(getPathWithoutExtension(jsonPath));
        simplePath.append(".min").append(".json");

        mapper.writer().writeValue(new File(simplePath.toString()), root);
    }

    private String getPathWithoutExtension(String path) {
        return path.substring(0, path.lastIndexOf("."));
    }
}
