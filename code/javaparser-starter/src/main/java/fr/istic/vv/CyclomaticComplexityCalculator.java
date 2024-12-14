package fr.istic.vv;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class CyclomaticComplexityCalculator {

    private static BufferedWriter reportWriter;
    private static BufferedWriter histogramWriter;
    private static Map<Integer, Integer> ccDistribution = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // Argument should be a directory path to scan Java files
        if (args.length == 0) {
            System.out.println("Please provide a directory path.");
            return;
        }
        String directoryPath = args[0];
        File dir = new File(directoryPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Invalid directory path.");
            return;
        }

        // Specify the output file paths
        Path reportFilePath = Paths.get("Cyclomatic_Complexity_Report.txt").toAbsolutePath();
        Path histogramFilePath = Paths.get("Cyclomatic_Text-based_Histogram.txt").toAbsolutePath();

        reportWriter = new BufferedWriter(new FileWriter(reportFilePath.toString()));
        histogramWriter = new BufferedWriter(new FileWriter(histogramFilePath.toString()));

        // Write header to the report file
        reportWriter.write("Cyclomatic Complexity Report\n");
        reportWriter.write("============================\n");

        // Initialize the JavaParser
        JavaParser javaParser = new JavaParser();

        // Start the parsing process
        parseDirectory(dir, javaParser);

        // Write the histogram
        writeHistogram();

        // Close the writers after finishing
        reportWriter.close();
        histogramWriter.close();
    }

    public static void parseDirectory(File dir, JavaParser javaParser) throws IOException {
        if (dir.isDirectory()) {
            // Print out the directory name
            System.out.println("Entering directory: " + dir.getPath());

            // List all files in the directory
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    // Recursively parse subdirectories
                    parseDirectory(file, javaParser);
                } else if (file.getName().endsWith(".java")) {
                    // Process Java files
                    System.out.println("Found Java file: " + file.getPath());
                    parseFile(file, javaParser);
                }
            }
        }
    }

    public static void parseFile(File file, JavaParser javaParser) throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            // Parse the file and handle the result
            com.github.javaparser.ParseResult<com.github.javaparser.ast.CompilationUnit> result = javaParser.parse(in);

            // Check if parsing was successful
            Optional<com.github.javaparser.ast.CompilationUnit> cuOptional = result.getResult();
            if (cuOptional.isPresent()) {
                com.github.javaparser.ast.CompilationUnit cu = cuOptional.get();
                System.out.println("Parsed file: " + file.getName());
                detectCyclomaticComplexity(file, cu);
            } else {
                System.out.println("Failed to parse: " + file.getName());
                reportWriter.write("Failed to parse: " + file.getName() + "\n");
                result.getProblems().forEach(problem -> {
                    try {
                        reportWriter.write("Error: " + problem + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public static void detectCyclomaticComplexity(File file, com.github.javaparser.ast.CompilationUnit cu) {
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration method, Void arg) {
                super.visit(method, arg);

                // Check if the method has a body
                if (method.getBody().isPresent()) {
                    // Get all statements in the method body
                    List<Statement> statements = method.getBody().get().getStatements();

                    // Cyclomatic complexity calculation
                    int cc = 1; // Cyclomatic complexity starts at 1 for each method
                    for (Statement stmt : statements) {
                        if (stmt instanceof IfStmt || stmt instanceof ForStmt ||
                                stmt instanceof WhileStmt || stmt instanceof SwitchStmt || stmt instanceof TryStmt) {
                            cc++;
                        }
                    }

                    // Update CC distribution
                    ccDistribution.put(cc, ccDistribution.getOrDefault(cc, 0) + 1);

                    // Get method name and parameters
                    String methodName = method.getNameAsString();
                    String methodParams = method.getParameters().stream()
                            .map(param -> param.getType().toString())
                            .collect(Collectors.joining(", "));

                    // Write the results to the report file
                    try {
                        reportWriter.write(String.format("Package: %s, Class: %s, Method: %s(%s), Cyclomatic Complexity: %d\n",
                                cu.getPackageDeclaration().map(pd -> pd.getNameAsString()).orElse("Default"),
                                method.getParentNode().map(parent -> parent.getClass().getSimpleName()).orElse("Unknown"),
                                methodName, methodParams, cc));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, null);
    }

    public static void writeHistogram() throws IOException {
        histogramWriter.write("Cyclomatic Text-based Histogram:\n");
        histogramWriter.write("=====================================\n");

        // Sort the distribution by cyclomatic complexity values
        List<Integer> sortedKeys = new ArrayList<>(ccDistribution.keySet());
        Collections.sort(sortedKeys);

        // Determine the max width of cyclomatic complexity for better formatting
        int maxCCLength = sortedKeys.stream()
                .map(String::valueOf)
                .max(Comparator.comparingInt(String::length))
                .orElse("0").length();

        // Write the histogram data to the file with proper alignment
        for (int cc : sortedKeys) {
            int count = ccDistribution.get(cc);
            StringBuilder histogramLine = new StringBuilder(String.format("Cyclomatic Complexity %-" + maxCCLength + "d ", cc));

            for (int i = 0; i < count; i++) {
                histogramLine.append("+");
            }

            // Write the formatted line
            histogramWriter.write(histogramLine.toString() + "\n");
        }
    }
}
