# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.

You can use the default [rule base](https://github.com/pmd/pmd/blob/master/pmd-java/src/main/resources/rulesets/java/quickstart.xml) available on the source repository of PMD.

## Answer

the result i got :
C:\Users\Dell\Desktop\project\VV-ISTIC-TP2>pmd check -d . -R rulesets/java/quickstart.xml -f text
[WARN] Progressbar rendering conflicts with reporting to STDOUT. No progressbar will be shown. Try running with argument -r <file> to output the report to a file instead.
[WARN] This analysis could be faster, please consider using Incremental Analysis: https://docs.pmd-code.org/pmd-doc-7.5.0/pmd_userdocs_incremental_analysis.html
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:3:        UnnecessaryImport:      Unused import 'com.github.javaparser.Problem'
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:4:        UnnecessaryImport:      Unused import 'com.github.javaparser.ast.CompilationUnit'
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:5:        UnnecessaryImport:      Unused import 'com.github.javaparser.ast.body.ClassOrInterfaceDeclaration'
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:6:        UnnecessaryImport:      Unused import 'com.github.javaparser.ast.body.MethodDeclaration'
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:7:        UnnecessaryImport:      Unused import 'com.github.javaparser.ast.visitor.VoidVisitor'
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:8:        UnnecessaryImport:      Unused import 'com.github.javaparser.ast.visitor.VoidVisitorAdapter'
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:13:       UnnecessaryImport:      Unused import 'java.nio.file.Path'
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:14:       UnnecessaryImport:      Unused import 'java.nio.file.Paths'
.\code\javaparser-starter\src\main\java\fr\istic\vv\Main.java:16:       UseUtilityClass:        This utility class has a non-private constructor
.\code\javaparser-starter\src\main\java\fr\istic\vv\PublicElementsPrinter.java:4:       UnnecessaryImport:      Unused import 'com.github.javaparser.ast.body.*'
.\code\javaparser-starter\src\main\java\fr\istic\vv\PublicElementsPrinter.java:20:      ControlStatementBraces: This statement should have braces
.\code\javaparser-starter\src\main\java\fr\istic\vv\PublicElementsPrinter.java:28:      ControlStatementBraces: This statement should have braces
.\code\javaparser-starter\src\main\java\fr\istic\vv\PublicElementsPrinter.java:44:      ControlStatementBraces: This statement should have braces

+ True Positive Issue :
  Unnecessary Import: PMD has flagged several imports as unused in the code. These are considered unnecessary because they are not being used in the file.  -
  - Fix: Remove or comment the unused imports to make the code cleaner and reduce unnecessary dependencies.
+ False Positive Issue :
  ControlStatementBraces: This statement should have braces PMD flagged lines 20, 28, and 44 in PublicElementsPrinter.java for missing braces in control statements.
  if(!declaration.isPublic()) return; 
  i will not fix it because In Java, single-line control statements do not require braces, though adding them improves readability and consistency.
  


