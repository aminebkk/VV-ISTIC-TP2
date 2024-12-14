package fr.istic.vv;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class NoGetterDetector {

    private static BufferedWriter writer;

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

        // Specify the absolute path for the output file in the desired directory
        Path outputFilePath = Paths.get("C:\\Users\\Dell\\Desktop\\project\\VV-ISTIC-TP2\\code\\javaparser-starter\\getter_detection_results.txt").toAbsolutePath();
        writer = new BufferedWriter(new FileWriter(outputFilePath.toString()));

        // Print the location of the output file for confirmation
        System.out.println("Results will be saved to: " + outputFilePath.toString());

        // Write header to the output file
        writer.write("Getter Detection Report\n");
        writer.write("========================\n");

        // Initialize the JavaParser
        JavaParser javaParser = new JavaParser();

        // Start the parsing process
        parseDirectory(dir, javaParser);

        // Close the writer after finishing
        writer.close();
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
                detectNoGetterFields(file, cu);
            } else {
                System.out.println("Failed to parse: " + file.getName());
                writer.write("Failed to parse: " + file.getName() + "\n");
                result.getProblems().forEach(problem -> {
                    try {
                        writer.write("Error: " + problem + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public static void detectNoGetterFields(File file, com.github.javaparser.ast.CompilationUnit cu) {
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(FieldDeclaration field, Void arg) {
                super.visit(field, arg);

                List<VariableDeclarator> variables = field.getVariables();
                for (VariableDeclarator variable : variables) {
                    // Check if the field is private
                    if (field.isPrivate()) {
                        boolean hasGetter = false;

                        // Check if there's a corresponding getter method
                        String fieldName = variable.getNameAsString();
                        String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                        for (MethodCallExpr methodCall : cu.findAll(MethodCallExpr.class)) {
                            if (methodCall.getNameAsString().equals(getterName)) {
                                hasGetter = true;
                                break;
                            }
                        }

                        // If the private field doesn't have a getter, write to the file
                        if (!hasGetter) {
                            try {
                                writer.write("Field '" + fieldName + "' in file " + file.getName() + " does not have a getter.\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }, null);
    }
}
