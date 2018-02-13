package mce;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mce.Validation.ValidationError;
import mce.util.Utils;

public class MCETest {

	public MCETest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// testMCE();
		// "./models/sbml/test/original/"
		String dirPath = "./models/sbml/test/original/";
		testMCEDir(dirPath);
		// countValidModel("./models/sbml/curated/");
		// Object[] result = new Object[1];
		// result[0] = "MC2";
		// MCTypes targetMC = MCTypes.valueOf(result[0].toString());
		// System.out.println(targetMC);
	}

	/**
	 * 
	 */
	private static void testMCEDir(String dirPath) {
		File sbmlDirectory = new File(dirPath);
		if (sbmlDirectory.isDirectory()) {
			List<File> fList = Utils.getFileList(sbmlDirectory);
			if (fList != null) {
				for (File sbmlFile : fList) {
					if (sbmlFile.isFile()) {
						if (sbmlFile.getName().endsWith(".sbml") || sbmlFile.getName().endsWith(".xml")) {
							Utils.out("[][][][][][][][][][][][][]{}{}{}{}{}{}{}{}{}{}{}{}{}{}[][][][][][][][][]");
							try {
								String arguments = "-s " + sbmlFile.getCanonicalPath() + " -q ./examples/pQuery.pq"
										+ " -o output";
								String[] args = arguments.split(" ");
								MCE.start(args);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}

	}

	private static void testMCE() {
		//
		// SINGLE MODEL
		// EnzymaticReaction2.xml
		// EnzymaticReaction.sbml
		// NaCl_Error.sbml
		// NaCl4.xml/home/memin/git/mce/mce/models/sbml/test/temp/BIOMD0000000098.xml.ssa.sbml
		// BIOMD0000000579.xml
		// BIOMD0000000611
		// BIOMD0000000405
		// BIOMD0000000152.xml
		// "./examples/BIOMD0000000611.xml";
		// "./models/sbml/test/original/BIOMD0000000152.xml"
		String sbmlFile = "./models/sbml/NaCl.sbml";
		String arguments = "-s " + sbmlFile + " -q ./examples/pQuery.pq" + " -o output";
		// TODO we can allow user to specify a target MC if they don't want best
		// one be estimated.

		long startTime = System.currentTimeMillis();
		String directory = "./models/sbml/test/original/";// "./models/sbml/dave/all";//
		// mcpExtra/withFileNo/all";//
		//
		// test/unitTest/";//
		// "./models/sbml/test//451_472/451_465";//
		// "C:\\Users\\Mehmet\\OneDrive\\git\\mce\\mce\\models\\sbml\\test\\unitTest";//
		// arguments = "-d " + directory + " -q ./src/pQuery.pq -lb 0 -upb 100 -simsamples 500 -simDepth 5000";
		String[] args = arguments.split(" ");
		MCE.start(args);
		System.out.println("Total time in ms:" + (System.currentTimeMillis() - startTime));
		// Send the results as an email
		// String subject = "Model run of Extra Models completed for " +
		// MCE.query + " pattern in dir " + directory;
		// String body = subject + "\n\n with args " + arguments;
		// String logFile = "mce.log";
		// String resultFile = PerforRes.csvFilePath;
		// Email.send(subject, body, new String[] { logFile, resultFile });
	}

	public static void countValidModel(String dirPath) {
		File sbmlDirectory = new File(dirPath);
		Validation validation = new Validation();
		List<String> validSBMLList = new ArrayList<String>();
		List<String> notValidSBMLList = new ArrayList<String>();
		// if many sbml model will be retrieved from a directory
		if (sbmlDirectory.isDirectory()) {
			List<File> fList = Utils.getFileList(sbmlDirectory);
			if (fList != null) {
				for (File sbmlFile : fList) {
					if (sbmlFile.isFile()) {
						if (sbmlFile.getName().endsWith(".sbml") || sbmlFile.getName().endsWith(".xml")) {
							Utils.out("[][][][][][][][][][][][][]{}{}{}{}{}{}{}{}{}{}{}{}{}{}[][][][][][][][][]");
							try {
								if (sbmlFile.getAbsolutePath().contains("BIOMD0000000312")) {
									System.out.println("hsttoopp");
								}
								if (validation.validateSBML(sbmlFile.getAbsolutePath())) {
									System.out.println("Valid");

									validSBMLList.add(sbmlFile.getName());
								} else {
									// Get the output of validation which contains error messages.
									System.out.println("No");
									notValidSBMLList.add(sbmlFile.getName());
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
			}
		}
		printValidationErrors(validation);

		System.out.println("Valid Count" + validSBMLList.size());
		System.out.println("NotCount" + notValidSBMLList.size());
		System.out.println("\n\n\n\n\n");
		for (String string : validSBMLList) {
			System.out.println(string);
		}
		// copyValidFiles(validSBMLList);
	}

	/**
	 * @param validSBMLList
	 */
	private static void copyValidFiles(List<String> validSBMLList) {
		String source = "./models/sbml/curated/";
		String to = "./models/sbml/test/original/";
		for (String fileName : validSBMLList) {

			try {
				Files.copy(new File(source + fileName).toPath(), new File(to + fileName).toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param validation
	 */
	private static void printValidationErrors(Validation validation) {
		Map<ValidationError, Integer> validationError = validation.getValidationError();
		for (ValidationError errorKey : validationError.keySet()) {
			System.out.println("Error Type:" + errorKey + " Count:" + validationError.get(errorKey));

		}
	}

}