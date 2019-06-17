import java.util.*;
import org.json.simple.*;
import java.io.*;
import org.json.simple.parser.*;

public class Alimentos{
    private String Alimento;
    private HashSet<String> interacao;

    public Alimentos(String alimento){
        this.Alimento=alimento;
        JSONParser parser=new JSONParser();
        try{
            Object obj=parser.parse(new FileReader("Drugfood.json"));
            JSONObject novo=(JSONObject)obj;
            JSONObject ali=(JSONObject) novo.get(alimento);
            JSONArray drugs=(JSONArray) ali.get("Medicamentos");
            Iterator<String> iterator=drugs.iterator();
            this.interacao=new HashSet();
            while(iterator.hasNext()){
                interacao.add(iterator.next());
            }
        }catch(FileNotFoundException e){System.out.println("Arquivo nao encontrado");}
        catch(IOException e){}
        catch(ParseException e){}
        catch(NullPointerException e){System.out.println("Alimento sem interacao medicamentosa com diabetes");}
        
    }
    public Boolean Teminteracao(String medicamento){
        if(interacao.contains(medicamento)) return true;
        return false;

    }
    public void Adicionainteracao(String medicamento){
        interacao.add(medicamento);
    }
    
}