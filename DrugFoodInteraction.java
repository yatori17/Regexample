import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;

public class DrugFoodInteraction{
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }
    
      public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONObject json = new JSONObject(jsonText);
          return json;
        } finally {
          is.close();
        }
      }
    
    
    public static ArrayList<String> DrugFood(String alimento) throws IOException{
        
        ArrayList<String> medicamentos= new ArrayList();
        String druglink="https://api.fda.gov/drug/label.json?search=drug_interactions:"+alimento;
        JSONObject newjson; 
        
          newjson = readJsonFromUrl(druglink);
        JSONArray results= newjson.getJSONArray("results");
        JSONObject aux = results.getJSONObject(0);
        JSONArray drugfood= aux.getJSONArray("drug_interactions");
        String teste= drugfood.toString();
        teste=teste.toLowerCase();
        String regex= "(\\(\\s\\d\\.\\d\\)\\.\\s+\\w*)";
        Pattern p=Pattern.compile(regex);
        Matcher result=p.matcher(teste);
        String regexaux="(\\w+$)";
        Pattern novo=Pattern.compile(regexaux);
        while(result.find()){
            String Rs=result.group(1);
            Matcher meds=novo.matcher(Rs);
            if(meds.find()){medicamentos.add(meds.group(0));}
        }
        String regex1="(?:including|\\G) (\\w*)(\\,)";
        p=Pattern.compile(regex1);
        result=p.matcher(teste);
        while(result.find()){
          medicamentos.add(result.group(1));
        }
        ///precisa de melhoria
/*        String regex2="hypoglycemia:((\\s*)(\\w*)(\\s*)(\\w*)(\\-*)(\\w*)(\\s*)(\\w*)(\\,*)\\(*\\w*\\)*)*";
        p=Pattern.compile(regex2);
        result=p.matcher(teste);
        while(result.find()){
          String nova=(result.group(0));
          nova=nova.replaceAll("\\w*\\:\\s","");
          String regexaux2="((\\s*)(\\w*)(\\s*)(\\w*)(\\-*)(\\w*)(\\s*)(\\w*)\\(*\\w*\\)*)*";
          Pattern novoaux=Pattern.compile(regexaux2);
          Matcher result2=novoaux.matcher(nova);
          while(result2.find()){
            String a=result2.group(0);
            regexaux="(\\w+\\s*\\w*\\s*\\w*\\s*\\w*$)";
            novo=Pattern.compile(regexaux);
            Matcher result3=novo.matcher(a);
            while(result3.find()){
              String auxiliar=result3.group(0);
              auxiliar=auxiliar.replaceAll("such","");
              auxiliar=auxiliar.replaceAll("as","");
              auxiliar=auxiliar.replaceAll("bound","");
              auxiliar=auxiliar.replaceAll("and","");
              auxiliar=auxiliar.replace("  ", "");
//              System.out.println(auxiliar);
              String elimina[] = auxiliar.split(Pattern.quote(","));
              for(int i=0;i<elimina.length;i++){
                if(elimina[i]!="")medicamentos.add(elimina[i]);
              }
            }

          }
        }
        */       
        String regexaux3="examples of medications (\\w*\\s*)*\\-*((\\s*(\\w*\\s*)*\\/*\\-*\\w*\\,*\\:*((\\(e\\.g\\.)*)\\(*\\w*)*\\)*)*";
        p=Pattern.compile(regexaux3);
        result=p.matcher(teste);
        while(result.find()){
          String nova=result.group(0);
          nova=nova.replaceAll("(\\w*\\s*\\/*\\,*\\-*)*\\:\\s", "");
          String elimina[]=nova.split(Pattern.quote(","));
//          System.out.println(nova);
          for(String aux0:elimina){
            medicamentos.add(aux0);
          }
        }
        return medicamentos;
    }
    public static void main(String []args) throws IOException{
      ArrayList <String> novo= DrugFood("alcohol");
      System.out.println(novo);
    }
    
}