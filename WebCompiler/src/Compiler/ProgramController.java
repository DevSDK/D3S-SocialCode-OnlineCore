package Compiler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by 송석호 on 2017-02-05.
 */
public class ProgramController {

    public ProgramController(String JsonRawData)
    {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonRoot = (JSONObject) parser.parse(JsonRawData);
            Language = (String)jsonRoot.get("Language");
            SourceCode = (String)jsonRoot.get("SourceCode");
            TimeLimit = (Long) jsonRoot.get("TimeLimit");
            JSONArray Inputs = (JSONArray)jsonRoot.get("Inputs");
            JSONArray Outputs = (JSONArray)jsonRoot.get("Outputs");
            TestCaseCount = (Long) jsonRoot.get("TastCaseCount");
            for(int i = 0 ; i < Inputs.toArray().length; i ++)
            {
               TestInput.add (((String)Inputs.get(i)).split(" "));
            }

            for(int i = 0 ; i < Outputs.toArray().length; i ++)
            {
                TestOutput.add(((String)Outputs.get(i)).split(" "));
            }
            CurrentProgram = new Program(SourceCode, Language);
            Program.CompileResult  compileResult =CurrentProgram.Compile();
             isCompiled=compileResult.isCompileOk;
            compileErrMessage = compileResult.Message;
        }catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private Program.RunResult Run(String[] arg)
    {
        return CurrentProgram.Run(arg);
    }
    private boolean isMatch(String [] s1, String []s2)
    {
        boolean result = true;
        for(int i = 0 ; i< s1.length; i ++)
        {
            if(!s1[i].equals(s2[i])){
                result = false;
            }
        }
        return result;
    }
    public boolean isCompileOk()
    {
        return isCompiled;
    }
    public String getCompileMessage()
    {
        return compileErrMessage;
    }
    public TestResult RunTest()
    {
        TestResult result = new TestResult();
        result.TimeList = new String[(int) TestCaseCount];
        result.passList = new boolean[(int)TestCaseCount];
        for(int i = 0 ; i < TestCaseCount; i ++)
        {
            Program.RunResult runRes = Run(TestInput.get(i));
            if(runRes.isOK)
            {
                result.TimeList[i]= (""+ (runRes.Runtime));
                if( isMatch(runRes.Message.replace("\r\n", " ").split(" "), TestOutput.get(i)))
                {
                    result.PassCount ++;
                    result.passList[i] = (true);
                }
            }

        }

        result.isComplate = true;

        return result;
    }

    Program CurrentProgram;
    boolean isCompiled = false;
    String compileErrMessage = "";
    String SourceCode;
    String Language;
    final ArrayList<String []> TestInput = new ArrayList<String[]>();
    final ArrayList<String []> TestOutput = new ArrayList<String[]>();
    final ArrayList<Long> RunningTimes = new ArrayList<Long>();
    long TimeLimit;
    long TestCaseCount;
    class TestResult
    {
        public int PassCount = 0;
        public String[] TimeList ;
        public boolean [] passList;
        public boolean isComplate = false;
        public String message = "";
    }
    }
