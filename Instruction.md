## Programming Task Description

In this task, you are going to use [Soot](https://github.com/soot-oss/soot) to implement a tool to compute and print:

1. The number of assignment statements in the target method: `public static void heapSort(int[] array)` in class `Sort` in `subject.jar`.
2. The number of statements containing method invocations in the previous target method
3. The total number of methods in `subject.jar`.

## Expected Output

Your program output should contain the following three lines:

```
Number of assignment statements in the target method: XX
Number of statements containing method invocations in the target method: XX
The total number of analyzed methods: XX
```

Note:

1. The XX should be replaced by the numbers computed by your program.
2. You shall not output the information of other methods that are not the target method.

## Related Tutorials

Soot is a framework commonly used for Java/Android program analysis and instrumentation.

You can follow the instructions on the home page of Soot to include Soot to your project: https://github.com/soot-oss/soot



You may need to refer to those tutorials provided by the Soot team to understand more about it:

Wiki: https://github.com/soot-oss/soot/wiki

Tutorials: https://github.com/soot-oss/soot/wiki/Tutorials



You may also need to refer to the JavaDoc of Soot when you need to use Soot's interfaces: https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/

There is also a reference on the different options of Soot: https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/options/soot_options.htm



Note: You are also free to search for any other tutorials and documents to help you understand and use Soot.

## Example Code

To help you quickly pick up Soot and finish this task, I provided an example code snippet as follows. This code snippet prints out the number of if statements used in each method in the analysis subject (`subject.jar`). The implementation idea of this code snippet is similar to what you need to achieve in this task. (Hint: You may complete your task by revising `internalTransform` in this code snippet)

```java
import soot.*;
import soot.jimple.IfStmt;
import soot.jimple.Stmt;
import soot.util.Chain;

import java.util.Iterator;
import java.util.Map;

public class MainDriver {
    // The main method to execute the tool
    public static void main(String[] args) {
        // Specify the path to the analysis subject jar file. Remember to change it to the path where you save subject.jar
        String jarFilePath = "../subject.jar";

        // The arguments (i.e., options) to run Soot
        String[] sootArgs = {
                "-f",
                "n",
                "-allow-phantom-refs",
                "-process-dir",
                jarFilePath
        };

        // Integrate your customized analysis code to Soot. The customized code is implemented in class IfScanner. 
        Pack jtp = PackManager.v().getPack("jtp");
        jtp.add(new Transform("jtp.MyTrans", new IfScanner()));

        //Invoke the main method of Soot to perform the analysis
        Main.main(sootArgs);
    }
}

// The class containing our customized analysis. It should extend class BodyTransformer
class IfScanner extends BodyTransformer {
    // This method is invoked once for every method in the analysis subject.
    // You may refer to Soot's JavaDoc to check the meaning of the parameters of this method.
    @Override
    protected void internalTransform(Body body, String s, Map<String, String> map) {
        // This method is to calculate the number of if statements in each method in the analysis subject.
        int if_cnt = 0;
        SootMethod method = body.getMethod();
        String sig = method.getSignature(); // Get the signature of the method being analyzed
        
        //Get an iterator to iterate over all the statements in the method being analyzed
        Chain chain = body.getUnits();
        Iterator<Unit> unitIterator = chain.snapshotIterator();

        while (unitIterator.hasNext()) {
            Stmt stmt = (Stmt) unitIterator.next();
            // Check whether stmt is an if statement. If so, add one to the counter.
            if (stmt instanceof IfStmt) {
                if_cnt++;
            }
        }

        // Print out the signature of the method being analyzed and the number of if statements in this method.
        System.out.println(sig + ", " + if_cnt);
    }
}
```





