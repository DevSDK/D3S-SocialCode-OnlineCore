package Compiler;

import javax.tools.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * Created by 송석호 on 2017-02-04.
 */
public class Program
{

    public Program(String SourceCode, String Language)
    {
        this.SourceCode = SourceCode;
        this.LanguageType = Language;
    }



    public CompileResult Compile()
    {
        CompileResult result =  null;
        if(LanguageType.equals(CPP))
        {

        }
        else if(LanguageType.equals(C))
        {

        }
        else if(LanguageType.equals(JAVA))
        {
          result =  CompileJavaSourceCode();
        }

        return result;
      }


    private CompileResult CompileJavaSourceCode()
    {

        String [] tokens = SourceCode.split(" |;|\n");
        String CurrentClass = "";
        for(int i = 0 ; i < tokens.length; i++)
        {
            if(tokens[i].equals("public")){
                if(i + 1  >= tokens.length)
                    break;
                else
                {
                    if(tokens[++i].equals("class"))
                    {
                        if(i + 1  >= tokens.length)
                            break;
                        else
                        {
                            CurrentClass = tokens[++i];
                            CurrentClass = CurrentClass.replaceAll("\\W", "");
                            ClassPath =  CurrentClass;
                        }
                    }
                }
            }
        }



        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileObject file = new JavaSourceFromString(CurrentClass, SourceCode);
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

        boolean success = task.call();

        StringBuilder ErrorMessage = new StringBuilder();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            ErrorMessage.append("Line No : "+diagnostic.getLineNumber() + "  Message : " +diagnostic.getMessage(null) + "\n");
        }
        CompileResult result = new CompileResult();

        if(success)
        {
            result.isCompileOk = true;
            isCompileOk = true;
            result.Message = "Successes";
        }
        else
        {
            result.isCompileOk = false;
            result.Message = ErrorMessage.toString();
        }
        return result;
    }


    public RunResult Run(String [] arguments) {
        RunResult result =  null;
        if (isCompileOk) {
            result = JavaRun(arguments);
        }

        return result;
    }
    RunResult JavaRun(String[] alguments)
    {
        RunResult result = new RunResult();
        try {
            final PrintStream BackUp_OutPutStream = System.out;
            final InputStream BackUpInputStream = System.in;

            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("").toURI().toURL()});
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(out));

            StringBuilder inputString = new StringBuilder();
            for(String str : alguments)
            {
                inputString.append(str + "\n");
            }
            ByteArrayInputStream inputStream = new ByteArrayInputStream(inputString.toString().getBytes());
            System.setIn(inputStream);

            long timeCheck_begin = System.currentTimeMillis();
            Class.forName(ClassPath, true, classLoader).getDeclaredMethod("main", new Class[]{String[].class}).invoke(null, new Object[]{null});
            long timeCheck_end = System.currentTimeMillis();

            System.setIn(BackUpInputStream);
            System.setOut(BackUp_OutPutStream);
            result.Runtime = (timeCheck_end - timeCheck_begin) / 1000.0;
            result.Message = out.toString();
            result.isOK= true;
        } catch (ClassNotFoundException e) {
            result.isOK = false;
            result.Message = "Class not found: " + e;
        } catch (NoSuchMethodException e) {
            result.isOK = false;
            result.Message = "No such method: " + e;
        } catch (IllegalAccessException e) {
            result.isOK = false;
            result.Message = "Illegal access: " + e;
        } catch (InvocationTargetException e) {
            result.isOK = false;
            result.Message = "Invocation target: " + e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private String SourceCode;
    private String LanguageType;
    private String ClassPath =  "";
    private boolean isCompileOk = false;
    public static String CPP  = "CPP";
    public static String JAVA  = "JAVA";
    public static String C = "C";

    public class CompileResult
    {
        boolean isCompileOk = false;
        String Message;

    }

    public class RunResult
    {
        boolean isOK = false;
        String Message;
        double Runtime;
    }



};
