package testdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Data {
	private static final String inputFile = "/media/cms500/F0E8D9BAE8D97F72/Google Drive/USAK/Fall 2017/Intelligent System/Assignment2 and 3/Code/Source_2_500_exemplars.txt";
	private static final String outputFile = "/media/cms500/F0E8D9BAE8D97F72/Google Drive/USAK/Fall 2017/Intelligent System/Assignment2 and 3/Code/Source_2_500_exemple.txt";
	public static void main(String[] args) throws Exception 
	{
		FileReader fr = new FileReader(inputFile);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
		String sCurrentLine;
		
		while ((sCurrentLine = br.readLine()) != null) 
		{
			String[] token = sCurrentLine.split(" ");
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int i=0;i<token.length;i++)
			{
				temp.add(Integer.parseInt(token[i]));
			}
			data.add(temp);
			
		}
		
		br.close();
		fr.close();
		System.out.println(data.size());
		File file = new File(outputFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		 try 
         {
         	for(int i=0;i<data.size();i++)
         	{
         		ArrayList<Integer> al = data.get(i);
         		bw.write(al.get(0).toString());
         		for(int j=1;j<al.size();j++)
         		{
         			bw.write(","+al.get(j));

         		}
         		bw.newLine();
                System.out.println("Successfully Completed");
			}
            
		} 
        catch (IOException e) 
        {
        	e.printStackTrace();
		}	//catch	
		 
		bw.close();
		fw.close();
		
	}

}
