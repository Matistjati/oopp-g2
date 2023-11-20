package oopp.cli;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GenericCLI {
    private boolean running = false;
    private ArrayList<CLIOption> options;
    private String CLIName;

    public GenericCLI()
    {
        this("");
    }

    public GenericCLI(String CLIName)
    {
        this(CLIName, new ArrayList<CLIOption>());
    }

    public GenericCLI(String CLIName, ArrayList<CLIOption> options)
    {
        this.CLIName = CLIName;
        this.options = options;
    }

    public void addCliOption(CLIOption option)
    {
        options.add(option);
    }

    public void run(String name)
    {
        CLIName = name;
        run();
    }

    public void run()
    {
        running = true;
        Scanner clInput = new Scanner(System.in);
        Pattern inputPattern = Pattern.compile("^[0-9]*?$");

        while (running)
        {
            printMainCLI();
            String a = clInput.nextLine();
            if (inputPattern.matcher(a).matches() && Integer.valueOf(a) < options.size()) {
                System.out.println(options.get(Integer.valueOf(a)).execute());
            }

        }
    }

    public void stop()
    {
        running = false;
    }

    private void printMainCLI()
    {
        StringBuilder builder = new StringBuilder("Welcome to: " + CLIName +  ". Please select your option\n");
        for (int i = 0; i < options.size(); i++)
        {
            builder.append("[" + String.valueOf(i) + "]: " +options.get(i).getPrompt() + "\n");
        }
        System.out.println(builder.toString());
    }


    @FunctionalInterface
    public interface CliCallbackInterface
    {
        String callback(CLIArgs args);
    }



}
