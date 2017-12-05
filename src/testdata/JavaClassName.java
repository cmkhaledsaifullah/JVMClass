package testdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;

public class JavaClassName 
{
	private static final String classFile = "/home/cms500/workspace/testfile.txt";
	//private static final String classFile = "/home/cms500/workspace/allJavaClass.txt";
	private static final String outputClassFile = "/home/cms500/workspace/ClassName.txt";
	private static final String outputMethodFile = "/home/cms500/workspace/MethodName.txt";
	
	public void getAllJavaClass() throws Exception
	{
		FileReader fr = new FileReader(classFile);
		BufferedReader br = new BufferedReader(fr);
		File file = new File(outputClassFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ArrayList<String> classList = new ArrayList<String>();
		//classList.add("");
		String sCurrentLine;
		
		while ((sCurrentLine = br.readLine()) != null) 
		{
			classList.add(sCurrentLine);
			
		}
		
		br.close();
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for( int i =0;i<classList.size();i++)
		{
			String s = classList.get(i);
			StringTokenizer st =new StringTokenizer(s);
			while (st.hasMoreElements()) 
			{
				String temp = st.nextToken();
				if(temp.contains("href"))
				{
					StringTokenizer st1 =new StringTokenizer(temp,"\"");
					while(st1.hasMoreElements())
					{
						String temp1 = st1.nextToken();
						if(!temp1.contains("href"))
						{
							StringTokenizer st2 =new StringTokenizer(temp1,".");
							String foo = st2.nextToken();
							//String foo = temp1.replaceAll(".html", "");
							String stt = foo.replaceAll("/", ".");
							try 
					        {
					            bw.write(stt);
					            bw.newLine();
							} 
					        catch (IOException e) 
					        {
					        	e.printStackTrace();
							}	//catch	
						}
					}
				}
					
			}
		}

		System.out.println("Number of Classes: "+classList.size());
		bw.close();
		fw.close();
		
	}

	public void getAllJavaMethod() throws Exception
	{
		FileReader fr = new FileReader(outputClassFile);
		BufferedReader br = new BufferedReader(fr);
		File file = new File(outputMethodFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ArrayList<String> classList = new ArrayList<String>();
		//classList.add("");
		String sCurrentLine;
		
		while ((sCurrentLine = br.readLine()) != null) 
		{
			classList.add(sCurrentLine);
			
		}
		
		br.close();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		ArrayList<String> methodList = new ArrayList<String>();
		for(String className: classList)
		{
			try
			{
				Class<?> c = Class.forName(className);
				ArrayList<String> temp = getMethods(c);
				for(int i=1;i<temp.size();i++)
				{
					String check="";
					String s = temp.get(i);
					StringTokenizer st =new StringTokenizer(s);
					ArrayList<String> tt = new ArrayList<String>();
					while(st.hasMoreElements())
					{
						tt.add(st.nextToken());
					}
					if(tt.contains("throws"))
					{
						check = tt.get(tt.size()-3);
					}
					else
					{
						int iteration = 1;
						while(true)
						{
							if(tt.get(tt.size()-iteration).contains("("))
							{
								for(int j = iteration ; j > 0; j--)
								{
									if(j == iteration)
										check = check + tt.get(tt.size()-j);
									else
										check = check + " " +tt.get(tt.size()-j);
								}
									
								break;
							}
							iteration++;
						}
					}
					
					if(methodList.contains(check))
						continue;
					
					methodList.add(check);
					bw.write(check);
					bw.newLine();
					if(check.equals("?>,char[])"))
					{
						System.out.println(s);
					}
				}
			}
			catch(Exception ex)
			{
				System.out.println(className);
			}
			
			
		}
		bw.close();
		fw.close();
		System.out.println("Number of Methods: "+methodList.size());
		
	}
	
	public static ArrayList<String> getMethods(Class<?> clazz) 
	{
		ArrayList<Method> found = new ArrayList<Method>();
		ArrayList<String> returnedStment = new ArrayList<String>();
		while (clazz != null) 
		{
			for (Method m1 : clazz.getDeclaredMethods()) 
			{
				boolean overridden = false;
				for (Method m2 : found) 
				{
					if (m2.getName().equals(m1.getName()) && Arrays.deepEquals(m1.getParameterTypes(), m2.getParameterTypes())) 
					{
						overridden = true;
						break;
					}
		        }

		        if (!overridden)
		        {
		        	found.add(m1);
		        	returnedStment.add(m1.toGenericString());
		        }
		        	
		    }
			
			Constructor<?>[] constructors = clazz.getConstructors();
			
			for(int i=0;i<constructors.length;i++)
			{
				returnedStment.add(constructors[i].toGenericString());
			}

		    clazz = clazz.getSuperclass();
		}
		return returnedStment;
	}
}
