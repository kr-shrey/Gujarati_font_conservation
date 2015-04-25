import java.util.*;
import java.io.*;
class ConversionTool
{
    private static int i;
    private static String str;
    private static HashMap<String,String> hhash;
    private static HashMap<String,Integer> nu2a;
    private static HashMap<Integer,String> a2u;
    public static void main(String[] args)throws IOException
    {
        i=0;
        if (args.length==0){
        	System.out.println("Usage: ConversionTool <inputfile>");
        	return;
        }
        str=new String();
        hhash=new HashMap<String,String>();
        nu2a=new HashMap<String,Integer>();
        a2u=new HashMap<Integer,String>();
        FileInputStream unicode_map = new FileInputStream("ASCIItoUnicode.dat");
        BufferedReader sc1 = new BufferedReader(new InputStreamReader(unicode_map,"UTF-8"));
        FileInputStream hl_map = new FileInputStream("HalantMapping.dat");
        BufferedReader sc3 = new BufferedReader(new InputStreamReader(hl_map,"UTF-8"));
        FileInputStream ascii_map = new FileInputStream("NonUnicodetoASCII.dat");
        BufferedReader sc2 = new BufferedReader(new InputStreamReader(ascii_map,"ISO8859-1"));
        FileInputStream fr = new FileInputStream(args[0]);
        FileOutputStream f=new FileOutputStream("output.txt");
        OutputStreamWriter few=new OutputStreamWriter(f,"UTF-8");
        BufferedWriter fw=new BufferedWriter(few);
        BufferedReader br = new BufferedReader(new InputStreamReader(fr,"ISO8859-1"));
        /*FileOutputStream o1=new FileOutputStream("NonUnicodetoASCII.map");
        ObjectOutputStream o11=new ObjectOutputStream(o1);
        FileOutputStream o2=new FileOutputStream("ASCIItoUnicode.map");
        ObjectOutputStream o22=new ObjectOutputStream(o2);*/
        /*FileInputStream oScript=new FileInputStream("Script.map");
        ObjectInputStream o3=new ObjectInputStream(oScript);
        FileInputStream oNu2A=new FileInputStream("NonUnicodetoASCII.map");
        ObjectInputStream o2=new ObjectInputStream(oNu2A);
        FileInputStream oA2U=new FileInputStream("ASCIItoUnicode.map");
        ObjectInputStream o1=new ObjectInputStream(oA2U);*/
        /*try
        {
        finalhash=(HashMap<String,String>)o3.readObject();
        }catch(ClassNotFoundException e)
        {
        }
        try
        {
        nu2a=(HashMap<String,Integer>)o2.readObject();
        }catch(ClassNotFoundException e)
        {
        }
        try
        {
        a2u=(HashMap<Integer,String>)o1.readObject();
        }catch(ClassNotFoundException e)
        {
        }           
        oScript.close();
        oNu2A.close();
        oA2U.close();*/
        String k;
        while((k=sc1.readLine())!=null)
        {
            if(!(Character.isDigit(k.charAt(0))))
            {
                k=k.substring(1);
            }
            int p=k.indexOf(" ");
            if(p>0)
            {
                Integer v=Integer.parseInt(k.substring(0,p));
                k=k.substring(p+1);
                a2u.put(v,k);
            }
        }
        while((k=sc2.readLine())!=null)
        {
            int p=k.indexOf(" ");
            if(p>0)
            {
                Integer v=Integer.parseInt(k.substring(0,p));
                k=k.substring(p+1);
                nu2a.put(k,v);
            }
        }
        int gy=0;
        while((k=sc3.readLine())!=null)
        {
            if(gy==0)
            {
                k=k.substring(1);
                gy++;
            }
            int p=k.indexOf(" ");
            if(p>0)
            {
                String v=k.substring(0,p);
                k=k.substring(p+1);
                hhash.put(v,k);
            }
        }
        sc1.close();
        sc2.close();
        sc3.close();
        unicode_map.close();
        hl_map.close();
        ascii_map.close();
        /*oo.writeObject(finalhash);
        oo.flush();
        oo.close();
        ooutput.close();*/
        /*o11.writeObject(nu2a);
        o11.flush();
        o11.close();
        o1.close();
        o22.writeObject(a2u);
        o22.flush();
        o22.close();
        o2.close();*/
        String temp;
        while((temp=(br.readLine()))!=null)
        {
            str=str+temp+"\n";
        }
        br.close();
        fr.close();
        String rep;
        out:
        while(hasNextKey())
        {
            rep="";
            if(str.charAt(i)==' ')
            {
                i++;
                String sp=" ";
                fw.write(sp,0,sp.length());
                continue out;
            }
            else if(str.charAt(i)=='\n')
            {
                i++;
                String nl="\r\n";
                fw.write(nl,0,nl.length());
                continue out;
            }
            int key1[]=nextKey();
            if(key1[1]>=0)
            {
                i=i+key1[1];
                if((key1[0]==108) || (key1[0]==115) || (key1[0]==122) || (key1[0]==142))        //rendering for choti ee
                {
                    int key2[]=nextKey();
                    if(key2[1]>=0)
                    {
                        rep=a2u.get(key2[0]);
                        i=i+key2[1];
                        if(hhash.containsKey(rep))
                        {
                            if(str.charAt(i)==' ')
                            {
                                rep=hhash.get(rep)+a2u.get(key1[0]);
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                            else
                            {
                                int key3[]=nextKey();
                                if(key3[0]==107)
                                {
                                    i=i+key3[1];
                                    rep=hhash.get(rep)+a2u.get(key1[0]);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                            }
                            rep=rep+a2u.get(key1[0]);
                            fw.write(rep,0,rep.length());
                            continue out;
                        }
                        else
                        {
                            rep=rep+a2u.get(key1[0]);
                            fw.write(rep,0,rep.length());
                            continue out;
                        }
                    }
                }
                else if((key1[0]==129) || (key1[0]==130))           //rendering for aaou
                {
                    if(hasNextKey())
                    {
                        int key2[]=nextKey();
                        if(key2[1]>=0)
                        {
                            if(key2[0]==118)
                            {
                                i=i+key2[1];
                                rep=a2u.get(225);
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                            rep=a2u.get(key1[0]);
                            fw.write(rep,0,rep.length());
                            continue out;
                        }
                    }
                    else
                    {
                        rep=a2u.get(key1[0]);
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                }
                else if(key1[0]==107)           //rendering for aae and aaeey
                {
                    if(hasNextKey())
                    {
                        int key2[]=nextKey();
                        if(key2[1]>=0)
                        {
                            if(key2[0]==118)
                            {
                                i=i+key2[1];
                                rep=a2u.get(223);
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                            else if(key2[0]==120)
                            {
                                i=i+key2[1];
                                rep=a2u.get(224);
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                            else
                            {
                                rep=a2u.get(key1[0]);
                                fw.write(rep,0,rep.length());
                                continue out;
                            }
                        }
                    }
                    else
                    {
                        rep=a2u.get(key1[0]);
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                }
                else                            //normal charcter with halant mapping at places required
                {
                    rep=a2u.get(key1[0]);
                    if(hhash.containsKey(rep))
                    {
                        if(str.charAt(i)==' ')
                        {
                            rep=hhash.get(rep);
                            fw.write(rep,0,rep.length());
                            continue out;
                        }
                        else
                        {
                            if(hasNextKey())
                            {
                                int key2[]=nextKey();
                                if(key2[0]==107)
                                {
                                    i=i+key2[1];
                                    rep=hhash.get(rep);
                                    fw.write(rep,0,rep.length());
                                    continue out;
                                }
                            }
                        }
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                    else
                    {
                        fw.write(rep,0,rep.length());
                        continue out;
                    }
                }
            }
        }
        fw.close();
        few.close();
        f.close();
    }
    
    /*
     * returns an integer array{<key>,<offset>}
     */
    private static int[] nextKey()
    {
        if(nu2a.containsKey(str.charAt(i)))                     //method 1 to get key
        {
            int arr[]={nu2a.get(""+str.charAt(i)),1};
            return arr;
        }
        if(str.charAt(i)=='<')                                  //method 2 to get key
        {
            if(Character.isDigit(str.charAt(i+1)))
            {
                boolean flag=true;
                int ei=str.indexOf('>',i+1);
                String temp=str.substring(i+1,ei);
                for(int n=0;n<temp.length();n++)
                {
                    if(!(Character.isDigit(temp.charAt(n))))
                    {
                        flag=false;
                    }
                }
                if(flag)
                {
                    int ky=Integer.parseInt(temp);
                    if(a2u.containsKey(ky))
                    {
                        int arr[]={ky,(ei+1-i)};
                        return arr;
                    }
                }
            }
        }
        int ext=str.charAt(i);                                  //method 3 to get key
        if(a2u.containsKey(ext))
        {
            int arr[]={ext,1};
            return arr;
        }
        int arr[]={-1,-1};
        i++;
        return arr;
    }

    private static boolean hasNextKey()
    {
        if(i<str.length())
        {
            return true;
        }
        return false;
    }
}
