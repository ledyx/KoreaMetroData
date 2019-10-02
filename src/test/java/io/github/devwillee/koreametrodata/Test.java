package io.github.devwillee.koreametrodata;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Compressor.INSTANCE.compressVertices("src/main/resources/seoul/vertices.json");
        Compressor.INSTANCE.compressEdges("src/main/resources/seoul/edges.json");
    }
}
