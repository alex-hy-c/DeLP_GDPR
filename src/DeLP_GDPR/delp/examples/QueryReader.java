package DeLP_GDPR.delp.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class QueryReader {

    private ArrayList<String> queries;

    public QueryReader(String filePath) {
        this.queries = new ArrayList<String>();
        readFile(filePath);
    }

    private void readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queries.add(line);
            }
        } catch (IOException e) {
        	System.out.println("Error");
        }
    }

    public ArrayList<String> getQueries() {
        return queries;
    }

}
