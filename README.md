# PHD Task
This is the PHD Task  from Prof. Lili Wei and is done by Byron Chen.
In this task, package [Soot](https://github.com/soot-oss/soot) is used with some simple methods.

---
## How to Run This?
Before executing this code, you need to install java (version 19 suggested) and have all the files downloaded in a new directory. 
To help you check the completeness of your download, the whole structure of this game is shown in the Section: Project Structure. 
After installing everything correctly, please build this project using Maven and run the MainDriver class.


---
## Notes

### Classes
**[BodyTransformer](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/BodyTransformer.html)** - any customized analysis should extend this class,
This method is invoked once for every method in the analysis subject. By using:

``` java
String jarFilePath = <Path of the Subject>;
String[] sootArgs = {
    <cmd lines for OS>
};

// InterIntegrate customized analysis code to Soot.
Pack jtp = PackManager.v().getPack("jtp");
jtp.add(new Transform("jtp.MyTrans", 
        new <Function that extend BodyTransformer for analysis>()));

//Invoke the main method of Soot to perform the analysis
Main.main(sootArgs);
```

**[internalTransform](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/BodyTransformer.html)** - This 
method is called to perform the transformation itself. It is declared abstract; subclasses must implement this method by making it the entry point to their actual Body transformation.

```java
protected abstract void internalTransform(Body b, 
                                          String phaseName, 
                                          Map<String, String> options)
```

**[Body](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/JastAddJ/Body.html)** - the
body of the code for applying the transformation

**[SootMethod](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/SootMethod.html)** - Soot representation of a Java method. 
Can be declared to belong to a SootClass. Does not contain the actual code, which belongs to a Body. 
The `getActiveBody()` method points to the currently-active body.

---

### Interfaces

**[Chain](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/util/Chain.html)** - 
Augmented data type guaranteeing O(1) insertion and removal from a set of ordered, unique elements.

**[Stmt]([https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/JastAddJ/Stmt.html](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/jimple/Stmt.html))** -
Statements

**[AssignStmt](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/jimple/AssignStmt.html)** -
Assignment Statements

**[InvokeStmt](https://soot-build.cs.uni-paderborn.de/public/origin/develop/soot/soot-develop/jdoc/soot/jimple/InvokeStmt.html)** -
Method Invoke Statements

---
## ATTENTION
- Different java files in the same jar pack are run in multi-threads which might lead some unexpected consequence.
- Dependencies are included in the file `pom.xml`

---

## Project Structure

```console
.
├── packages
    └── subject.jar
├── src
    └── main
        └── java
            └── MainDriver.java
├── .gitignore
├── Instruction.md
├── README.md
└── pom.xml
```

