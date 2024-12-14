# Cyclomatic Complexity with JavaParser

With the help of JavaParser implement a program that computes the Cyclomatic Complexity (CC) of all methods in a given Java project. The program should take as input the path to the source code of the project. It should produce a report in the format of your choice (TXT, CSV, Markdown, HTML, etc.) containing a table showing for each method: the package and name of the declaring class, the name of the method, the types of the parameters and the value of CC.
Your application should also produce a histogram showing the distribution of CC values in the project. Compare the histogram of two or more projects.


Include in this repository the code of your application. Remove all unnecessary files like compiled binaries. Do include the reports and plots you obtained from different projects. See the [instructions](../sujet.md) for suggestions on the projects to use.

You may use [javaparser-starter](../code/javaparser-starter) as a starting point.

Answer :

I compared the histogram of the distribution of CC values in the two projects (commons-math and commons-lang)
                               //////// commons-math Histogram:  ////////
Cyclomatic Complexity 1 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 2 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 3 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 4 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 6 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 7 +++++++++++++++++++++++++++++++
Cyclomatic Complexity 8 +++++++++++++++
Cyclomatic Complexity 9 ++++++++++++
Cyclomatic Complexity 10 +++++
Cyclomatic Complexity 11 ++++++++++
Cyclomatic Complexity 12 +++
Cyclomatic Complexity 13 +++
Cyclomatic Complexity 14 +
Cyclomatic Complexity 15 +
Cyclomatic Complexity 16 ++
Cyclomatic Complexity 20 +
Cyclomatic Complexity 28 +

                               //////// commons-lang Histogram:  ////////
Cyclomatic Complexity 1 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 2 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 3 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 4 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 6 +++++++++++++++++++++++++++++++++++++++++++
Cyclomatic Complexity 7 +++++++++++++++
Cyclomatic Complexity 8 ++++++
Cyclomatic Complexity 9 ++++++++
Cyclomatic Complexity 10 ++++++
Cyclomatic Complexity 17 ++
Cyclomatic Complexity 21 ++

+ Key Observations:
  - Cyclomatic Complexity of 1:
         - Both projects have many methods with a complexity of 1 (indicating simple methods with no decision points), but commons-math has more of these methods than commons-lang. 
  - Higher Complexity Values:
         - commons-math has methods with much higher complexity, ranging up to 28, showing that it contains more complex methods with multiple decision points (like if-statements or loops).
         - commons-lang has fewer high-complexity methods, with values going up to only 21. The complexity tends to stay lower.
  - Complexity Spread:
         - commons-math has a more even spread of complexity values (from 1 to 16), meaning that the methods in this project are more varied in their complexity.
         - commons-lang has most of its methods with lower complexity (up to 10), and there are very few methods with complexity higher than 10.
+ Conclusion :
   - commons-math has more complex methods with higher decision points, making it a more intricate codebase.
   - commons-lang has simpler methods with less complexity, suggesting a codebase that is easier to follow with fewer decision-making structures.

Explanation of Values :
   - Cyclomatic Complexity 1: The method has no decision points (e.g., no if, for, while, or switch statements).
   - Higher Values (2-28): The method has more decision points, meaning it contains logic that branches or loops, making it more complex. The higher the number, the more decision points or branches are involved in that method.

