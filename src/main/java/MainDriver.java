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




//import soot.*;
//import soot.jimple.IfStmt;
//import soot.jimple.Stmt;
//import soot.util.Chain;
//
//import java.io.File;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Iterator;
//import java.util.Map;
//
//public class MainDriver {
//    // The main method to execute the tool
////    public static String currentPath = "packages/subject.jar";
//
//    public static void main(String[] args) {
//        //set classpath
//        String javapath = System.getProperty("java.class.path");
//        String jredir = System.getProperty("java.home") + "\\lib\\rt.jar";
//        String path = javapath + File.pathSeparator + jredir;
//
//        Scene.v().setSootClassPath(path);
//
//        // Specify the path to the analysis subject jar file. Remember to change it to the path where you save subject.jar
//        String pathToFile = "./packages/subject.jar";
////        System.out.println(pathToFile.toAbsolutePath());
////        String jarFilePath = String.valueOf(pathToFile.toAbsolutePath());
////        System.out.println(jarFilePath);
//
//        // The arguments (i.e., options) to run Soot
//        String[] sootArgs = {
//                "-f",
//                "n",
//                "soot.Main",
//                "-allow-phantom-refs",
//                "-soot-modulepath",
//                "./modules/modules",
//                "-process-dir",
//                pathToFile
//        };
//
//        // Integrate your customized analysis code to Soot. The customized code is implemented in class IfScanner.
//        Pack jtp = PackManager.v().getPack("jtp");
//        jtp.add(new Transform("jtp.MyTrans", new IfScanner()));
//
//        //Invoke the main method of Soot to perform the analysis
//        Main.main(sootArgs);
//    }
//}
//
//// The class containing our customized analysis. It should extend class BodyTransformer
//class IfScanner extends BodyTransformer {
//    // This method is invoked once for every method in the analysis subject.
//    // You may refer to Soot's JavaDoc to check the meaning of the parameters of this method.
//    @Override
//    protected void internalTransform(Body body, String s, Map<String, String> map) {
//        // This method is to calculate the number of if statements in each method in the analysis subject.
//        int if_cnt = 0;
//        SootMethod method = body.getMethod();
//        String sig = method.getSignature(); // Get the signature of the method being analyzed
//
//        //Get an iterator to iterate over all the statements in the method being analyzed
//        Chain chain = body.getUnits();
//        Iterator<Unit> unitIterator = chain.snapshotIterator();
//
//        while (unitIterator.hasNext()) {
//            Stmt stmt = (Stmt) unitIterator.next();
//            // Check whether stmt is an if statement. If so, add one to the counter.
//            if (stmt instanceof IfStmt) {
//                if_cnt++;
//            }
//        }
//
//        // Print out the signature of the method being analyzed and the number of if statements in this method.
//        System.out.println(sig + ", " + if_cnt);
//    }
//}