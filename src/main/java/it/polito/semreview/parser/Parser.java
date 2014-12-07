package it.polito.semreview.parser;

import it.polito.semreview.utils.TXTFileFilter;
import it.polito.semreview.utils.Utilities;
import it.polito.softeng.common.exceptions.LoadingException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class Parser {

	private static Logger logger = Logger.getLogger(Parser.class);
	
	private static File txtInputFolder;
	private static File txtOutputFolder;
	private static String fileSeparator;
	private static final String fileSeparatorProperty = "file.separator";

	public static final String ABSTRACT = "Abstract";
	public static final String ABSTRACT_XML = "<doc><section name='abstract'><![CDATA[";

	public static final String REFERENCES = "REFERENCES";
	public static final String REFERENCES_XML = "]]></section><section name='references'><![CDATA[";

	public static final String[] ABSTRACT_READ = { "Abstract", "Abstrct",
			"Abstnwt", "Abslnref", "gIbstmct", "Abstraet", "Abstnct",
			"Abstmct", "Abstmrct", "Abstrmct", "Abshrct", "Abstnact",
			"Abstrat", "Absrruct", "Absfrucf", "Absfracf", "Abstruct",
			"Absrracf", "Absrracr", "Abstracf", "Abshac", "4 b ~ u t f ",
			"Abslract", "Abstracl", "..ibstract", "Abstmcf", "Abstracl",
			"Absfract", "Abslracf", "Absfraet", "Abstrucf", "A6xfmct", "Ab-",
			"Abshct", "Absfract", "Abstmc", "Abslraet", "Absfract", "Abslmet",
			"Absftacf", "Absfmcf", "Absmct", "Abstmcf", "Absfruct", "Abstnrct",
			"Absfruct", "Absrrcrcr", "Abslruct", "AbMmcr", "Abstmc", "Absfmct",
			"Abshct", "Abmw&", "Ab-t", "Abshurct", "Abs ract", "Aktruet",
			"Abstraci", "4bstract", "Abstruet", "Abslmcl",
			"Abslrucl", "Abstrud", "Abslmct", "Absbuct", "Abstrad", "Absh-act",
			"Absftaet", "Ahsfracf", "Abstracr", "Absfracr" };

	public static final String[] REFERENCES_READ = { "References",
			"REFERENC ES", "P EFERENCES", "RFERENCES", "Alexander L. Uolf",
			"Barbara G. Hjder", "REFtRkNCES", "A(.KNOWII.FX.~ ME N ' I",
			"REF ER EN c E s", "REFER~NC", "RIFE:KENCES",
			"R E F E R t~N C F S", "Ac K N o w L EUG M E NI",
			"REF ER EN c E s", "REFERFiNCES", "REFEREWE", "RE~FEREN",
			"EFERENCES", ",FERENCES", ",FERENCES", "EFEREN",
			"Brunswick, New Jersey", "[FERENCES", "REFERENCE",
			"[1 ] A. V. Aho and J. D. Ullman", "[21 C.A.R. Hoare",
			"RErERENCES", "Alexander L. U o l f",
			"RYDER AND PENDERGRAST: EXPERIMENTS IN OPTIMIZING FP 453",
			"R E F t R k N C E S ", "A(.KNOWI.F.  I X ~ M E N ' I  ",
			"R E F E R ~ N C E S ", "R E F  E R  t~ N C F S ",
			"E .  Jungert er U", "REFFRENCI-3" };

	public static final String[] SECTION_2_SEPARATOR = { "\nII. ", "\n11. ",
			"\n2. ", "\n2 ", "\n2 ", "\n[2].\n", "\nA. ", "\nA . ",
			"\nALADDIN\n", "2 RELATED WORK",
			"Abstract\nfunctional specifications",
			"EQUALITY IN SPECIFICATIONS AND\n", "REUSABLE DATA",
			"REUSABLE MODULE DESIGN APPROACH", ": the activity of symbolic",
			"EXISTING MODELS FOR CONCURRENT" };

	public static final String INTRODUCTION = "Introduction";
	public static final String INTRODUCTION_XML = "]]></section><section name='introduction'><![CDATA[";

	public static final String[] INTRODUCTION_READ = { "INTRODUCTION",
			"\nI. INTRODUCTION", "INTRODUCTIOi4",
			"TWENTY-FIVE YEARS OF DATA PROCESSING", "THE USE of", "BACKGROUND",
			"ANDOM", "Fischer and LeBlanc based", "Baskett, Chandy, Muntz",
			"When learning to use their first",
			"Mills' concept of a chief programmer team" };

	public static final String[] CONCLUSIONS = { "CONCLUSIONS", "Conclusions",
			"Summary", "CONCLUSIONS AND FURTHER WORK",
			"A NUMERICAL QUANTIFICATION OF THE", "QUEUE NETWORK",
			"PROJECT STATUS AND FUTURE WORK", "CONCLUDING REMARKS",
			"DISCUSSION", "CONCLUSION: PRISM AS EVIDENCE",
			"SUMMARY AND FUTURE WORK" };

	private static final String XML_END_FILE = "]]></section></doc>";;

	public static void main(String[] args) {
		checkNumberOfArguments(args, 2);
		try {
			checkFirstArgument(args[0]);
			checkSecondArgument(args[1]);
			parse(false);
		} catch (Exception e) {
			fatalError(e.getMessage());
		}
	}

	public static void execute(String inputDirPath, String outputDirPath)
			throws IOException, LoadingException {
		checkFirstArgument(inputDirPath);
		checkSecondArgument(outputDirPath);
		parse(false);
	}

	public static void executeToXml(String inputDirPath, String outputDirPath)
			throws IOException, LoadingException {
		checkFirstArgument(inputDirPath);
		logger.debug("Input dir check: "+inputDirPath);
		checkSecondArgument(outputDirPath);
		logger.debug("Output dir check: "+outputDirPath);
		parse(true);
		logger.debug("Parse ended");
	}

	private static void parse(boolean xml) throws IOException, LoadingException {
		Collection<File> TXTfilesList = Utilities.listFiles(txtInputFolder,
				new TXTFileFilter(), true);
		Iterator<File> TXTfilesIterator = TXTfilesList.iterator();
		File txtFile;

		fileSeparator = System.getProperty(fileSeparatorProperty);

		while (TXTfilesIterator.hasNext()) {
			txtFile = TXTfilesIterator.next();

			boolean loop = true;
			int stringIndex = 0;
			int i = 0, j = 0;
			String startPath = txtInputFolder.getAbsolutePath();
			String filePath = txtFile.getAbsolutePath();
			String text = "";
			String line = null;
			BufferedReader br = null;

			/* Loading current file */
			logger.info("Loading file "+txtFile);
			try {
				br = new BufferedReader(new FileReader(txtFile));
			} catch (Exception e1) {
				throw new IOException("ERROR in file : "
						+ txtFile.getAbsolutePath());
			}

			/* Load file content line by line in variable text */
			try {
				while ((line = br.readLine()) != null) {
					text += line + "\n";
					i++;
				}
				br.close();
			} catch (Exception e1) {
				throw new IOException("Error reading line: " + i
						+ " in file : " + txtFile.getAbsolutePath());
			}

			/* RIMUOVO IL TESTO DALL'INIZIO DELLA STRINGA */
			int indexOfTheFirstAbstractDelimiterInText = 0, indexOfTheSecondAbstractDelimiterInText = 0;
			int firstAbstractDelimiterFound = 0, secondAbstractDelimiterFound = 0;
			logger.debug("Looking for abstract");
			while (loop) {

				if ((stringIndex = text.indexOf(ABSTRACT_READ[j])) > 0) {
					if (indexOfTheFirstAbstractDelimiterInText == 0) {
						indexOfTheFirstAbstractDelimiterInText = stringIndex;
						firstAbstractDelimiterFound = j;
						j++;
					} else if (indexOfTheFirstAbstractDelimiterInText != 0
							&& indexOfTheSecondAbstractDelimiterInText == 0) {
						indexOfTheSecondAbstractDelimiterInText = stringIndex;
						secondAbstractDelimiterFound = j;
						j++;
					} else if (indexOfTheFirstAbstractDelimiterInText != 0
							&& indexOfTheSecondAbstractDelimiterInText != 0) {
						j++;
					}

				} else {
					j++;
				}
				String whatToPutForDelimitAbstract = xml ? ABSTRACT_XML
						: ABSTRACT;
				if (j == ABSTRACT_READ.length) {
					loop = false;
					if (indexOfTheSecondAbstractDelimiterInText != 0) {
						if (indexOfTheFirstAbstractDelimiterInText < indexOfTheSecondAbstractDelimiterInText) {
							text = text
									.substring(indexOfTheFirstAbstractDelimiterInText);
							text = text.replace(
									ABSTRACT_READ[firstAbstractDelimiterFound],
									whatToPutForDelimitAbstract);
						} else {
							text = text
									.substring(indexOfTheSecondAbstractDelimiterInText);
							text = text
									.replace(
											ABSTRACT_READ[secondAbstractDelimiterFound],
											whatToPutForDelimitAbstract);
						}
					} else {
						text = text
								.substring(indexOfTheFirstAbstractDelimiterInText);
						text = text.replace(
								ABSTRACT_READ[firstAbstractDelimiterFound],
								whatToPutForDelimitAbstract);
					}
					if (!xml) {
						text = text.replace(ABSTRACT + "-", ABSTRACT + "\n");
						text = text.replace(ABSTRACT + "?", ABSTRACT + "\n");
						if (text.indexOf("Abstract ") != -1) {
							text = text.replace(ABSTRACT + " ", ABSTRACT + "\n");
						}
					}
				}
			}

			if (indexOfTheFirstAbstractDelimiterInText == -1
					&& indexOfTheSecondAbstractDelimiterInText == -1) {
				throw new LoadingException("Abstract not found in file "
						+ txtFile.getAbsolutePath());
			}

			logger.debug("Abstract processed");
			
			/* INTRO */

			j = 0;
			loop = true;
			boolean found = false;
			
			String introductionMarker = xml?INTRODUCTION_XML:INTRODUCTION;

			while (loop) {
				
				

				if ((stringIndex = text.indexOf(INTRODUCTION_READ[j])) != -1) {
					loop = false;
					if (INTRODUCTION_READ[j].equals("ANDOM")) {
						text = text.replace(INTRODUCTION_READ[j], introductionMarker
								+ "\nANDOM");

					} else if (INTRODUCTION_READ[j]
							.equals("Fischer and LeBlanc based")) {
						text = text.replace(INTRODUCTION_READ[j], introductionMarker
								+ "\nFischer and LeBlanc based");

					} else if (INTRODUCTION_READ[j]
							.equals("Baskett, Chandy, Muntz")) {
						text = text.replace(INTRODUCTION_READ[j], introductionMarker
								+ "\nBaskett, Chandy, Muntz");
					} else if (INTRODUCTION_READ[j]
							.equals("When learning to use their first")) {
						text = text.replace(INTRODUCTION_READ[j], introductionMarker
								+ "\nWhen learning to use their first");

					} else if (INTRODUCTION_READ[j]
							.equals("Mills' concept of a chief programmer team")) {
						text = text
								.replace(
										INTRODUCTION_READ[j],
										introductionMarker
												+ "\nMills' concept of a chief programmer team");

					} else {
						text = text.replace(INTRODUCTION_READ[j], introductionMarker);
						text = text.replaceFirst(".*" + Pattern.quote(introductionMarker),
								introductionMarker);
					}
					found = true;

				} else {
					j++;
				}
				if (j == INTRODUCTION_READ.length) {
					loop = false;
				}
			}

			/*
			 * SE DOPO IL LOOP stringIndex E' ANCORA -1 SIGNIFICA CHE NON HO
			 * TROVATO LE REFERENCES E LO SEGNALO
			 */

			if (!found) {
				int d = text.length();
				text = text.replaceFirst("(\\n)*I. [A-Z]+[ ]*[A-Z]*.*",
						introductionMarker);
				if (d - text.length() == 0)
					throw new LoadingException(
							"INTRODUCTIONS not found in file "
									+ txtFile.getAbsolutePath());
			}

			j = 0;
			loop = true;
			found = false;
			while (loop) {
				if ((stringIndex = text.indexOf(SECTION_2_SEPARATOR[j])) != -1) {
					text = text.substring(0, stringIndex);
					loop = false;
					found = true;
					j++;
				} else {
					j++;
				}
				if (j == SECTION_2_SEPARATOR.length) {
					loop = false;
				}
			}
			boolean regexpOk = false;
			if (!found) {

				text = text.replaceAll(
						"(\\n)*2[ ]+[A-Z]+[:]*[ ]+[A-Z]+.*(\\n)*", "II.");
				if ((stringIndex = text.indexOf("II.")) > 0) {
					text = text.substring(0, stringIndex);
					regexpOk = true;
				}
				if (!regexpOk) {
					text = text
							.replaceAll(
									"(\\n)*[A-Z]+[:]*[ ]+[A-Z]+[ ]*[A-Z]*[ ]*[A-Z]*[ ]*[A-Z]*[ ]*[A-Z]*[ ]*[A-Z]*[ ]*[A-Z]*(\\n)*",
									"II.");
					if ((stringIndex = text.indexOf("II.")) > 0) {
						text = text.substring(0, stringIndex);
						regexpOk = true;
					}
				}
			}

			j = 0;
			loop = true;

			while (loop) {
				if ((stringIndex = text.lastIndexOf(REFERENCES_READ[j])) != -1) {
					loop = false;
					text = text.substring(0, stringIndex);
				} else {
					j++;
				}
				if (j == REFERENCES_READ.length) {
					loop = false;
				}
			}

			filePath = txtOutputFolder.getAbsolutePath()
					+ filePath.substring(startPath.length());

			File newDirTree = new File(filePath.substring(0,
					filePath.lastIndexOf(fileSeparator)));
			newDirTree.mkdirs();

			if (xml){
				filePath = filePath.substring(0,filePath.length()-"txt".length());
				filePath = filePath + "xml";
				
				text += XML_END_FILE;
			}
			File newTXTFile = new File(filePath);

			newTXTFile.createNewFile();
			PrintStream output = new PrintStream(newTXTFile);
			output.print(text);
		}
	}

	private static void checkNumberOfArguments(String[] args, int nArgs) {
		if (args.length != nArgs) {
			fatalError("Usage: java -jar " + Parser.class.getName()
					+ " <path input PDF files> <path output pdf files>");
		}
	}

	private static void fatalError(String message) {
		System.err.println(message);
		throw new RuntimeException();
	}

	private static void checkFirstArgument(String pathToInputFolder)
			throws IOException {
		File pdfContainer = new File(pathToInputFolder);
		if (!pdfContainer.exists() || !pdfContainer.isDirectory()) {

			throw new IOException("Error - Folder : " + pathToInputFolder
					+ " does not exist");
		}
		txtInputFolder = pdfContainer;
	}

	private static void checkSecondArgument(String pathToOutputFolder)
			throws IOException {
		File pdfContainer = new File(pathToOutputFolder);
		if (pdfContainer.exists()) {
			fatalError("Error - Folder : " + pathToOutputFolder
					+ " already exists");
		} else {
			boolean created = false;
			created = pdfContainer.mkdir();
			if (!created) {
				throw new IOException("Error - Cannot create folder : "
						+ pathToOutputFolder);
			} else {
				txtOutputFolder = pdfContainer;
			}
		}
	}

}
