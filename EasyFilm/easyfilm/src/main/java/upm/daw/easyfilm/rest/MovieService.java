package upm.daw.easyfilm.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upm.daw.easyfilm.model.Pelicula;
import upm.daw.easyfilm.repository.PeliculaRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    PeliculaRepository peliculaRepository;

    private String keyOMDB = "&apikey=68db6d07";
    private String keyTMDB = "?api_key=418ae6abc61cc65089c1ddae1b1b5eb6";

    public MovieService () {
    }


    public List<Pelicula> getPeliculas(String nombre) throws IOException {

        String nombreMod = "";
        for (int i = 0; i < nombre.length(); i++)
            nombreMod += nombre.charAt(i)==' ' ? "+" : nombre.charAt(i);


        URL url = new URL("http://www.omdbapi.com/?s=" + nombreMod + keyOMDB);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        ObjectMapper mapper = new ObjectMapper();

        Search pelis = mapper.readValue(conn.getInputStream(),Search.class);

        if (pelis.getSearch() != null) {
            System.out.println(pelis.getSearch().size());

            for (int i = 0; i < pelis.getSearch().size(); i++) {
                if (pelis.getSearch().get(i).getType().equals("game"))
                    pelis.getSearch().remove(i);
            }
            return pelis.getSearch();
        }
        else return null;
    }

    private Pelicula buscarEnDb(String nombre) {

        return peliculaRepository.findByNombrePelicula(nombre);
    }

    public Pelicula getPelicula(String nombre) throws IOException {

        Pelicula pelicula = buscarEnDb(nombre);

        if (pelicula == null)
        {
            pelicula = new Pelicula();
            pelicula.setNombrePelicula(nombre);
        }
        if (pelicula.getDirector() == null || pelicula.getActors() == null
                || pelicula.getYear() == null || pelicula.getValoracion() == null) {
            String nombreMod = "";
            for (int i = 0; i < nombre.length(); i++)
                nombreMod += nombre.charAt(i) == ' ' ? "+" : nombre.charAt(i);

            System.out.println(nombre);
            System.out.println(nombreMod);

            Pelicula trailer;
            URL urlPelicula, urlidTmdb, urlTrailer;
            ObjectMapper mapper = new ObjectMapper();
            HttpURLConnection conn;

            urlPelicula = new URL("http://www.omdbapi.com/?t=" + nombreMod + keyOMDB);
            conn = (HttpURLConnection) urlPelicula.openConnection();
            conn.connect();
            Pelicula restante = mapper.readValue(conn.getInputStream(), Pelicula.class);

            if (pelicula.getDirector()==null)
                pelicula.setDirector(restante.getDirector());
            if (pelicula.getActors()== null)
                pelicula.setActors(restante.getActors());
            if (pelicula.getYear()== null)
                pelicula.setYear(restante.getYear());
            if (pelicula.getValoracion()==null)
                pelicula.setValoracion(restante.getValoracion());
            if (pelicula.getValoracion()==null)
                pelicula.setValoracion(restante.getValoracion());
            if (pelicula.getDescripcion()==null)
                pelicula.setDescripcion(restante.getDescripcion());
            if (pelicula.getImdbId()==null)
                pelicula.setImdbId(restante.getImdbId());
            if (pelicula.getUrl_portada()==null)
                pelicula.setUrl_portada(restante.getUrl_portada());

            if(pelicula.getUrl_trailer() == null)
            {
                urlidTmdb = new URL("https://api.themoviedb.org/3/find/" + pelicula.getImdbId() + keyTMDB + "&external_source=imdb_id");
                conn = (HttpURLConnection) urlidTmdb.openConnection();
                System.out.println("https://api.themoviedb.org/3/find/" + pelicula.getImdbId() + keyTMDB + "&external_source=imdb_id");
                conn.connect();
                JsonNode root = mapper.readTree(conn.getInputStream());
                JsonNode js = root.at("/tv_results/0/id");
                if (js.isMissingNode())
                    js = root.at("/movie_results/0/id");
                if (js.isMissingNode())
                    System.out.println("ID not found in TMDB");

                pelicula.setTmdbId(js.asText());
                System.out.println(js.asText());

                if (!js.isMissingNode()) {
                    urlTrailer = new URL("https://api.themoviedb.org/3/movie/" + pelicula.getTmdbId() + "/videos" + keyTMDB);
                    conn = (HttpURLConnection) urlTrailer.openConnection();
                    conn.connect();
                    root = mapper.readTree(conn.getInputStream());
                    js = root.at("/results/0/key");
                    pelicula.setUrl_trailer("https://www.youtube-nocookie.com/embed/" + js.asText());
                }
            }
            peliculaRepository.save(pelicula);
        }
        return pelicula;
    }

}
