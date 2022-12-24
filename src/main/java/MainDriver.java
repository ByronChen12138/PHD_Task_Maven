import soot.*;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.util.Chain;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

class MethodCount {
    public static int method_cnt;
}

public class MainDriver {
    // The main method to execute the tool
    public static void main(String[] args) {


        // Specify the path to the analysis subject jar file. Remember to change it to the path where you save subject.jar
        String jarFilePath = "./packages/subject.jar";

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
        jtp.add(new Transform("jtp.MyTrans", new TaskScanner()));

        MethodCount.method_cnt = 0;

        // Invoke the main method of Soot to perform the analysis
        Main.main(sootArgs);

        // Output
        System.out.println("The total number of analyzed methods: " + MethodCount.method_cnt);
    }
}

class TaskScanner extends BodyTransformer {
    @Override
    protected void internalTransform(Body body, String s, Map<String, String> map) {
        int assign_cnt = 0;
        int invoke_cnt = 0;

        MethodCount.method_cnt++;
        SootMethod method = body.getMethod();
        String sig = method.getSignature(); // Get the signature of the method being analyzed

        // If not wanted method, return
        if (!Objects.equals(sig, "<Sort: void heapSort(int[])>")) return;

        // Get an iterator to iterate over all the statements in the method being analyzed
        Chain chain = body.getUnits();
        Iterator<Unit> unitIterator = chain.snapshotIterator();

        while (unitIterator.hasNext()) {
            Stmt stmt = (Stmt) unitIterator.next();

            if (stmt instanceof AssignStmt) {
                assign_cnt++;
            }

            if (stmt instanceof InvokeStmt) {
                invoke_cnt++;
            }
        }

        // Output
        System.out.println("Number of assignment statements in the target method: " + assign_cnt);
        System.out.println("Number of statements containing method invocations in the target method: " + invoke_cnt);
    }
}