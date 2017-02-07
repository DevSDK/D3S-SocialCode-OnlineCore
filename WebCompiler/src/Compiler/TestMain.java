package Compiler;

/**
 * Created by 송석호 on 2017-02-04.
 */
public class TestMain {

           public static  String Test = "{\n" +
                   "  \"Language\": \"JAVA\",\n" +
                   "  \"TimeLimit\": 1000,\n" +
                   "  \"Outputs\": [\n" +
                   "    \"4 8 12 16 20 24 28 32 36 40\"" +
                   "    \"5 10 15 20 25 30 35 40 45 50\"" +
                   "  ],\n" +
                   "  \"Inputs\": [\n" +
                   "    \"4 1 10 \",\n" +
                   "    \"5 1 10 \"\n" +
                   "  ],\n" +
                   "  \"TastCaseCount\": 2,\n" +
                   "  \"SourceCode\": \" \\n\\nimport java.util.Scanner;\\n\\npublic class test {\\npublic static void main(String [] argc)\\n{\\nScanner scan = new Scanner(System.in);\\nint a = Integer.parseInt(scan.next());\\nint b = Integer.parseInt(scan.next());\\nint c = Integer.parseInt(scan.next());\\n\\nfor(int i =b; i<=c; i++)\\n{\\nSystem.out.println(a*i);\\n}\\n}\\n}\\n\"\n" +
                   "}";


    public static void main(String [] args)
            {



                ProgramController program = new ProgramController(Test);
                if(program.isCompileOk())
                {

                ProgramController.TestResult result = program.RunTest();


                for(int i = 0 ; i < result.passList.length;i++)
                {
                    System.err.println("TestCase :" + i + 1 + " "   +result.passList[i]);

                }
                }
                else
                {
                    System.err.print(program.compileErrMessage);
                }
            }


}
