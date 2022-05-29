import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

            String url = "https://go.olx.com.br/grande-goiania-e-anapolis?q=iphone%2011";

            Document doc = Jsoup.connect(url).get();
            List<Element> anuncios = doc.getElementsByClass("sc-12rk7z2-1 kQcyga");

            List<String> listaTitulos = new ArrayList<>();
            for (Element e: anuncios){
                listaTitulos.add(e.getElementsByTag("h2").first().text());
            };

            List<String> listaUrls = new ArrayList<>();
            for (Element e: anuncios){
                listaUrls.add(e.attr("href"));
            };

            List<Integer> listaPrecos_int = new ArrayList<>();

            for(Element e: anuncios){
                String temp = e.getElementsByClass("m7nrfa-0 cjhQnm sc-bdVaJa cpfGxa").text();
                String[] listString = temp.split(" ");
                listString[1]=listString[1].replace(".","");
                listaPrecos_int.add(Integer.parseInt(listString[1]));
            }
            Integer val = 0;
            Integer i = 0;
            for(Integer preco: listaPrecos_int){
                val+= preco; i++;
            }
            Integer media = val/i;
            System.out.println(media);

            try(PrintWriter writer = new PrintWriter("anunciosIphone11.csv")){
                StringBuilder sb = new StringBuilder();
                //CABEÇALHO
                sb.append("Titulo Anuncio");
                sb.append(",");
                sb.append("Preco");
                sb.append(",");
                sb.append("url");
                sb.append("\n");

                for(int index=0; index<listaTitulos.size(); index++){
                    if(listaPrecos_int.get(index)<=media){
                        sb.append(listaTitulos.get(index));
                        sb.append(",");
                        sb.append(listaPrecos_int.get(index));
                        sb.append(",");
                        sb.append(listaUrls.get(index));
                        sb.append("\n");
                    }
                };
                writer.write(sb.toString());

            }catch (FileNotFoundException e){
                System.out.println(e.getMessage());
            }

    }
}
