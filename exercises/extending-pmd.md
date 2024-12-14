# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](https://github.com/selabs-ur1/VV-ISTIC-TP2/blob/master/exercises/designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer
+ Findings from Using the Custom PMD Rule on Different Projects :
  - I applied the custom PMD rule TooManyNestedIfs, which detects methods with three or more nested if statements, to two different open-source projects: Commons Math and Commons Lang. Below are the findings:
  1 - Commons Math :
    File: .\commons-math-transform\src\test\java\org\apache\commons\math4\transform\FastSineTransformerTest.java
    Line 139: The method contains more than three nested if statements, violating the rule.
    File: .\src\userguide\java\org\apache\commons\math4\userguide\genetics\Polygon.java
    Line 86: The method contains more than three nested if statements, violating the rule.
  2 - Commons Lang :
    File: .\src\main\java\org\apache\commons\lang3\text\StrBuilder.java
    Line 2456: The method contains more than three nested if statements.
    Line 2491: The method contains more than three nested if statements.
    Line 2711: The method contains more than three nested if statements.
    Line 2802: The method contains more than three nested if statements.
    Line 2866: The method contains more than three nested if statements.
  
