package DeLP_GDPR.delp.examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.tweetyproject.commons.ParserException;
import DeLP_GDPR.delp.parser.DelpParser;
import DeLP_GDPR.delp.reasoner.DelpReasoner;
import DeLP_GDPR.delp.semantics.GeneralizedSpecificity;
import DeLP_GDPR.delp.syntax.DefeasibleLogicProgram;
import DeLP_GDPR.logics.fol.syntax.FolFormula;

public class DeLPExample {
    public static void main(String[] args) throws IOException {
        DelpReasoner reasoner = new DelpReasoner(new GeneralizedSpecificity());

        FileWriter resultWriter = new FileWriter("results.txt", true);
        BufferedWriter writer = new BufferedWriter(resultWriter);

        // Building test-case for different Knowledge-bases
        final File folder = new File("knowledge_base/tests/");
        for (final File fileEntry : folder.listFiles()) {
            String filePath = "/" + fileEntry.toPath().toString();

            System.out.println("Processing this Knowledge-base: " + filePath);
            writer.write("--------");
            writer.write("Knowledge Base test-case: " + fileEntry.getName());
            writer.newLine();

            DelpParser parser = new DelpParser();
            DefeasibleLogicProgram delp = parser.parseBeliefBaseFromFile(fileEntry.getAbsolutePath());

            File QueryFile = new File("queries.txt");
            ArrayList<String> Queries = new QueryReader(QueryFile.getAbsolutePath()).getQueries();

            for (int i = 0; i < Queries.size(); i++) {
                String queryString = Queries.get(i);
                FolFormula query = (FolFormula) parser.parseFormula(queryString);
                System.out.println("\n" + queryString + ": ");
                writer.write("Query to Knowledge base: " + queryString);
                writer.newLine();
                String queryResult;

                ArrayList<Long> times = new ArrayList<Long>(); // Reset times array for each query

                for (int j = 1; j <= 1; j++) {
                    long startTime = System.currentTimeMillis();
                    queryResult = reasoner.query(delp, query).toString();
                    long endTime = System.currentTimeMillis();

                    long executionTime = endTime - startTime;

                    // Write the result to file
                    writer.write(queryResult + " " + executionTime);
                    writer.newLine();

                    // Update the times array
                    times.add(executionTime);
                }
                writer.newLine();

                if (times.size() > 1) {
                    times.remove(0); // ignore first run
                    double[] statistics = calculateStatistics(times);
                    writer.write("First run ignored");
                    writer.newLine();
                    writer.write("Mean: " + statistics[0] + " milliseconds");
                    writer.newLine();
                    writer.write("Standard Deviation: " + statistics[1]);
                    writer.newLine();
                }
            }

            writer.write("---End of test-cases---");
            writer.newLine();
        }

        // Move the closing of the writer outside the loop
        writer.close();
        resultWriter.close();
        System.out.println("Done");
    }

    public static double[] calculateStatistics(ArrayList<Long> arrayList) {
        long sum = 0;
        for (Long time : arrayList) {
            sum += time;
        }
        double mean = (double) sum / arrayList.size();
        double standardDeviation = 0;

        for (Long time : arrayList) {
            standardDeviation += Math.pow(time - mean, 2);
        }

        double[] statistics = new double[2];
        statistics[0] = mean;
        statistics[1] = Math.sqrt(standardDeviation / arrayList.size());
        return statistics;
    }
}