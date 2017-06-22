package cn.com.hfrjl.readheatmeter.utils;

public class Frequent {
	static int  HexS1ToInt(char ch){
	   int r=0;
	   if ('a' <= ch && ch <= 'f') { r= ch - 'a' + 10; }
	   if ('A' <= ch && ch <= 'F') { r= ch - 'A' + 10; }
   	   if ('0' <= ch && ch <= '9') { r= ch - '0'; }
	   return r;
	}
    public static int HexS2ToInt(String S){
 	   int r=0;
 	   char a[]=S.toCharArray(); 
 	   r=HexS1ToInt(a[0])*16+HexS1ToInt(a[1]);
 	   return r;
    }
    public static int HexS4ToInt(String S){
  	   int r=0;
  	   char a[]=S.toCharArray(); 
  	   r=HexS1ToInt(a[0])*16*16*16+HexS1ToInt(a[1])*16*16+HexS1ToInt(a[2])*16+HexS1ToInt(a[3]);
  	   return r;
    }
    public static int HexS8ToInt(String S){
   	   int r=0;
   	   r=HexS4ToInt(S.substring(0, 4))*16*16*16*16+HexS4ToInt(S.substring(4, 8));
   	   return r;
     }
	public static String HexS2ToBinS(String S){
  	   String r="";
  	   S=S.toUpperCase();
  	   char a[]=S.toCharArray(); 
  	   if (a[0]=='0') {
  		   r=r+"0000";
  	   }else if (a[0]=='1') {
  		   r=r+"0001";   
       }else if (a[0]=='2') {
    	   r=r+"0010";
       }else if (a[0]=='3') {
    	   r=r+"0011";		   
       }else if (a[0]=='4') {
    	   r=r+"0100";
       }else if (a[0]=='5') {
    	   r=r+"0101";		   
       }else if (a[0]=='6') {
    	   r=r+"0110";
       }else if (a[0]=='7') {
    	   r=r+"0111"; 	  		   
       }else if (a[0]=='8') {
    	   r=r+"1000";
       }else if (a[0]=='9') {
    	   r=r+"1001"; 	    		   
       }else if (a[0]=='A') {
    	   r=r+"1010";
       }else if (a[0]=='B') {
    	   r=r+"1011"; 	    	  		   
       }else if (a[0]=='C') {
    	   r=r+"1100";
       }else if (a[0]=='D') {
    	   r=r+"1101";	    	    		   
       }else if (a[0]=='E') {
    	   r=r+"1110";
       }else if (a[0]=='F') {
    	   r=r+"1111";
  	   }
  	   
	  	 if (a[1]=='0') {
			r=r+"0000";
		 }else if (a[1]=='1') {
			r=r+"0001";   
	     }else if (a[1]=='2') {
	  	    r=r+"0010";
	     }else if (a[1]=='3') {
	  	    r=r+"0011";		   
	     }else if (a[1]=='4') {
	  	    r=r+"0100";
	     }else if (a[1]=='5') {
	  	    r=r+"0101";		   
	     }else if (a[1]=='6') {
	  	    r=r+"0110";
	     }else if (a[1]=='7') {
	  	    r=r+"0111"; 	  		   
	     }else if (a[1]=='8') {
	  	    r=r+"1000";
	     }else if (a[1]=='9') {
	  	    r=r+"1001"; 	    		   
	     }else if (a[1]=='A') {
	  	    r=r+"1010";
	     }else if (a[1]=='B') {
	  	    r=r+"1011"; 	    	  		   
	     }else if (a[1]=='C') {
	  	    r=r+"1100";
	     }else if (a[1]=='D') {
	  	    r=r+"1101";	    	    		   
	     }else if (a[1]=='E') {
	  	    r=r+"1110";
	     }else if (a[1]=='F') {
	  	    r=r+"1111";
		 }
  	   return r;
     }
    public static String BinS4ToHexS2(String S){
	   String r="";
	   if (S.equals("0000")) r="0";
	   else if (S.equals("0001")) r="1";
 	   else if (S.equals("0010")) r="2";
 	   else if (S.equals("0011")) r="3";
 	   else if (S.equals("0100")) r="4";
 	   else if (S.equals("0101")) r="5";
 	   else if (S.equals("0110")) r="6";
 	   else if (S.equals("0111")) r="7";
 	   else if (S.equals("1000")) r="8";
 	   else if (S.equals("1001")) r="9";
 	   else if (S.equals("1010")) r="A";
 	   else if (S.equals("1011")) r="B";
 	   else if (S.equals("1100")) r="C";
 	   else if (S.equals("1101")) r="D";
 	   else if (S.equals("1110")) r="E";
 	   else if (S.equals("1111")) r="F";
       return r;
    }
    public static String BinS8ToHexS2(String S){
   	   String r="";
   	   if (S.substring(0,4).equals("0000")) r="0";
   	   else if (S.substring(0,4).equals("0001")) r="1";
	   else if (S.substring(0,4).equals("0010")) r="2";
	   else if (S.substring(0,4).equals("0011")) r="3";
	   else if (S.substring(0,4).equals("0100")) r="4";
	   else if (S.substring(0,4).equals("0101")) r="5";
	   else if (S.substring(0,4).equals("0110")) r="6";
	   else if (S.substring(0,4).equals("0111")) r="7";
	   else if (S.substring(0,4).equals("1000")) r="8";
	   else if (S.substring(0,4).equals("1001")) r="9";
	   else if (S.substring(0,4).equals("1010")) r="A";
	   else if (S.substring(0,4).equals("1011")) r="B";
	   else if (S.substring(0,4).equals("1100")) r="C";
	   else if (S.substring(0,4).equals("1101")) r="D";
	   else if (S.substring(0,4).equals("1110")) r="E";
	   else if (S.substring(0,4).equals("1111")) r="F";
   	   
   	   if (S.substring(4,8).equals("0000")) r=r+"0";
	   else if (S.substring(4,8).equals("0001")) r=r+"1";
	   else if (S.substring(4,8).equals("0010")) r=r+"2";
	   else if (S.substring(4,8).equals("0011")) r=r+"3";
	   else if (S.substring(4,8).equals("0100")) r=r+"4";
	   else if (S.substring(4,8).equals("0101")) r=r+"5";
	   else if (S.substring(4,8).equals("0110")) r=r+"6";
	   else if (S.substring(4,8).equals("0111")) r=r+"7";
	   else if (S.substring(4,8).equals("1000")) r=r+"8";
	   else if (S.substring(4,8).equals("1001")) r=r+"9";
	   else if (S.substring(4,8).equals("1010")) r=r+"A";
	   else if (S.substring(4,8).equals("1011")) r=r+"B";
	   else if (S.substring(4,8).equals("1100")) r=r+"C";
	   else if (S.substring(4,8).equals("1101")) r=r+"D";
	   else if (S.substring(4,8).equals("1110")) r=r+"E";
	   else if (S.substring(4,8).equals("1111")) r=r+"F";
   	   return r;
      }
    public static String getsum(String S, int Start){
  	   String r="";
  	   int sumL=0;
 	   for (int i=(Start*2);i<S.length();i=i+2){
 		  sumL=(sumL+Frequent.HexS2ToInt(S.substring(i,i+2)))%256;
 	   }
 	   r= Integer.toHexString(sumL/16)+ Integer.toHexString(sumL%16);
 	   r=r.toUpperCase();
  	   return r;
     }
	//@SuppressLint("DefaultLocale")
	public static int checksum(String S, int Start, String checksumS){
 	   int r=0;
 	   int sumL=0;
 	   for (int i=(Start*2);i<S.length();i=i+2){
 		  sumL=(sumL+Frequent.HexS2ToInt(S.substring(i,i+2)))%256;
 	   }
 	   if ((Integer.toHexString(sumL/16)+ Integer.toHexString(sumL%16)).toUpperCase().equals(checksumS)) r=1;
 	   return r;
    }
	public static byte[] getdatacodeforgprs(String control, String gprs_ASCII, String dataarea){
	   byte[] arri =null;
	   int lens=("7B"+control+"00"+gprs_ASCII+dataarea+"7B").length()/2+1;
	   String Lens= Integer.toHexString(lens/16)+ Integer.toHexString(lens%16);
	   String rx="7B"+control+"00"+Lens+gprs_ASCII+dataarea+"7B";
	   arri = new byte[rx.length()/2];
	   for(int i=0;i<(rx.length()/2);i++){ 
			arri[i]=(byte)Frequent.HexS2ToInt(rx.substring(2*i, 2*i+2));
	   }
	   return arri;
	}
}
