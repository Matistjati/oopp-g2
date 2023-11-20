package oopp.cli;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CLIOption {
    private GenericCLI.CliCallbackInterface target;
    private ArrayList<Pattern> regexRules;
    private ArrayList<String> cliArgPrompts;
    private String cliPrompt;

    public CLIOption(GenericCLI.CliCallbackInterface target, String cliPrompt)
    {
        this(target, cliPrompt, new ArrayList<Pattern>(), new ArrayList<String>());
    }
    public CLIOption(GenericCLI.CliCallbackInterface target, String cliPrompt, ArrayList<Pattern> regexRules, ArrayList<String> cliPrompts)
    {
        this.target = target;
        this.cliPrompt = cliPrompt;
        this.regexRules = regexRules;
        this.cliArgPrompts = cliPrompts;
    }
    public void addArgPrompt(String prompt, String regexRule)
    {
        cliArgPrompts.add(prompt);
        regexRules.add(Pattern.compile(regexRule));
    }

    public String getPrompt()
    {
        return cliPrompt;
    }
    public String execute()
    {
        CLIArgs args = new CLIArgs();
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < regexRules.size(); i++)
        {
            while (true)
            {
                System.out.println(cliArgPrompts.get(i));
                String newLine = input.nextLine();
                if (regexRules.get(i).matcher(newLine).matches())
                {
                    args.addArg(newLine);
                    break;
                }
            }
        }
        return target.callback(args);
    }
}