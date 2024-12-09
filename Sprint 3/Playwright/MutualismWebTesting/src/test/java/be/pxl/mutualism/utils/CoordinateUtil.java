package be.pxl.mutualism.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class CoordinateUtil {
    public static double[] getCoordinates(String jsonFilePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(jsonFilePath));

            if (!rootNode.isArray()) {
                throw new RuntimeException("De root node is geen array.");
            }

            JsonNode treeNode = rootNode.get(0);

            JsonNode positionNode = treeNode.get("position");
            if (positionNode == null || positionNode.size() < 2) {
                throw new RuntimeException("Position node ontbreekt of heeft een verkeerde structuur.");
            }

            double latitude = positionNode.get(0).asDouble();
            double longitude = positionNode.get(1).asDouble();

            return new double[]{latitude, longitude};

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fout bij het lezen van JSON-bestand.", e);
        }
    }
}
