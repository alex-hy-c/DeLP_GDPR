package DeLP_GDPR.delp.examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.tweetyproject.commons.ParserException;
import DeLP_GDPR.delp.examples.DeLPExample;
import DeLP_GDPR.delp.parser.DelpParser;
import DeLP_GDPR.delp.reasoner.DelpReasoner;
import DeLP_GDPR.delp.semantics.GeneralizedSpecificity;
import DeLP_GDPR.delp.syntax.DefeasibleLogicProgram;
import DeLP_GDPR.logics.fol.syntax.FolFormula;

/**
 * Shows how to parse and query a DeLP program.
 * 
 * Doesn't handle unknown
 */

public class DeLPExample {
	public static void main(String[] args) throws FileNotFoundException, ParserException, IOException {
		DelpReasoner reasoner = new DelpReasoner(new GeneralizedSpecificity());
		FileWriter resultWriter = new FileWriter("results.txt", true);
		BufferedWriter writer = new BufferedWriter(resultWriter);
		File file = new File("knowledge_base/derivation_experiment.txt");//birds      consent.txt
		System.out.println("Processing this Knowledge-base: " + file.getPath());
		writer.write("---Knowledge Base test-case: " + file.getName() + "---");
		writer.newLine();
		DelpParser parser = new DelpParser();
		DefeasibleLogicProgram delp = parser.parseBeliefBaseFromFile(file.getAbsolutePath()); // DeLPExample.class.getResource(filePath).getFile()

		File QueryFile = new File("experiment_queries.txt");// All queries in this file. Each line is a separate query
		ArrayList<String> Queries = new QueryReader(QueryFile.getAbsolutePath()).getQueries();

		for (int i = 0; i < Queries.size(); i++) { // Conducting 10 tests for each case
			String queryString = Queries.get(i);
			FolFormula query = (FolFormula) parser.parseFormula(queryString);
			System.out.println("\n" + queryString+": ");
			writer.write("Query to Knowledge base: " + queryString);
			writer.newLine();
			String result;
			for (int j = 1; j <= 1; j++) {
				result = null;
				long startTime = System.nanoTime();
				result = reasoner.query(delp, query).toString();
				long endTime = System.nanoTime();
				long executionTime = (endTime - startTime) / 1000000;
				// Write the result to file
				writer.write(" Run: " + j + " " + result + " Execution Time: " + executionTime);
				writer.newLine();
			}
		}
		writer.write("---End of test-cases---");
		writer.newLine();
		writer.close();
		resultWriter.close();
		System.out.println("Done");
	}
}