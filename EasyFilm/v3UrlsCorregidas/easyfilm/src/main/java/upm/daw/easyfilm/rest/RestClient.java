package upm.daw.easyfilm.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import upm.daw.easyfilm.model.Pelicula;
import upm.daw.easyfilm.repository.PeliculaRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Service
public class RestClient {

    @Autowired
    PeliculaRepository peliculaRepository;

    private final String keyOMDB = "&apikey=68db6d07";
    private final String keyTMDB = "?api_key=418ae6abc61cc65089c1ddae1b1b5eb6";

    public RestClient() {}


    /**
     * <b>Metodo getPeliculas</b>
     * Este metodo recibe un string con el nombre de la busqueda que se desea realizar
     * Utiliza la api OMDB (api de pago de imdb) para realizar una busqueda que devuelva
     * varios valores que coincidan con el nombre introducido (maximo 10, por la API)
     *
     * @param nombre - Nombre de la pelicula que se desea buscar
     * @return Lista de peliculas que coincidan con el patron del nombre
     * @throws IOException
     */
    public List<Pelicula> getPeliculas(String nombre) throws IOException {

        //Se eliminan los espacios del nombre proporcionado
        String nombreMod = nombre.replace(' ','+');

        //Busqueda en la API
        RestTemplate restTemplate = new RestTemplate();
        String url2 = "http://www.omdbapi.com/?s=" + nombreMod + keyOMDB;
        Search pelis = restTemplate.getForObject(url2,Search.class);

        //Si se encontraron elementos, se eliminan los no deseados
        //(solo queremos peliculas)
        if (pelis.getSearch() != null)
        {
            Pelicula act;
            for (int i = 0; i < pelis.getSearch().size(); i++)
            {
                act = pelis.getSearch().get(i);
                if (act.getType().equals("game") || act.getType().equals("series"))
                    pelis.getSearch().remove(i);
            }
            return pelis.getSearch();
        }
        else return null;
    }

    /**
     * <b>Metodo buscarEnDB</b>
     * Busca en la base de datos si existe una pelicula con el mismo
     * nombre que el que se indica como argumento
     * @param nombre - Nombre de la pelicula a buscar en la BD
     * @return Pelicula encontrada o null e.o.c
     */
    private Pelicula buscarEnDb(String nombre) {

        return peliculaRepository.findByNombrePelicula(nombre);
    }

    /**
     * <b>Metodo rellenar</b>
     * Rellena una pelicula con los datos obtenidos desde una API
     * @param pelicula - Pelicula que se desea rellenar
     * @param restante - Pelicula cuyos datos se inyectaran en la
     *                 pelicula de primer argumento.
     */
    private void rellenar (Pelicula pelicula, Pelicula restante) {

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
    }


    /**
     * <b>Metodo getPelicula</b>
     * Recibe el nombre de una pelicula y realiza las siguientes operaciones:
     * 1) Busca en la base de datos si existe una pelicula con ese nombre
     *      - Si no existia, se creara una nueva
     * 2) Una vez se tiene un objeto pelicula, si sus campos estan vacios
     * (director, reparto...) se usa la API OMDB para obtener informacion
     * y rellenar dichos campos.
     * 3) Si el objeto Pelicula tenia trailer (porque estaba en la BBDD)
     * no se realiza ninguna adicional. E.o.c se utiliza la API TMDB,
     * usando como puente entre ambas el ID de Imdb, para obtener la
     * URL del video de Youtube que representa a la pelicula (trailer)
     *
     * @param nombre - Nombre de la pelicula a buscar
     * @return Pelicula (al usarla en conjunto con getPeliculas() siempre
     * devuelve un resultado)
     * @throws IOException
     */
    public Pelicula getPelicula(String nombre) throws IOException {

        //Se busca en base de datos en primer lugar
        Pelicula pelicula = buscarEnDb(nombre);

        //Si no existia, se crea una nueva
        if (pelicula == null)
        {
            pelicula = new Pelicula();
            pelicula.setNombrePelicula(nombre);
        }

        //Si la pelicula no dispone de datos esenciales, se usa la API OMDB
        if (pelicula.getDirector() == null
                || pelicula.getActors() == null
                || pelicula.getYear() == null
                || pelicula.getValoracion() == null)
        {
            //Se eliminan los espacios del nombre proporcionado
            String nombreMod = nombre.replace(' ','+');

            //Busqueda en la API
            RestTemplate restTemplate = new RestTemplate();
            String url2 = "http://www.omdbapi.com/?t=" + nombreMod + keyOMDB;
            Pelicula restante = restTemplate.getForObject(url2,Pelicula.class);


            //Se rellenan los campos pertinentes
            rellenar(pelicula,restante);

            //Si no se dispone de trailer se intenta buscarlo
            if(pelicula.getUrl_trailer() == null)
            {
                URL urlidTmdb;
                HttpURLConnection conn;
                ObjectMapper mapper = new ObjectMapper();

                urlidTmdb = new URL("https://api.themoviedb.org/3/find/" + pelicula.getImdbId() + keyTMDB + "&external_source=imdb_id");
                conn = (HttpURLConnection) urlidTmdb.openConnection();
                conn.connect();
                JsonNode root = mapper.readTree(conn.getInputStream());
                JsonNode js = root.at("/movie_results/0/id");
                if (js.isMissingNode())
                    System.out.println("ID not found in TMDB");

                pelicula.setTmdbId(js.asText());

                if (!js.isMissingNode()) {
                    URL urlTrailer = new URL("https://api.themoviedb.org/3/movie/" + pelicula.getTmdbId() + "/videos" + keyTMDB);
                    conn = (HttpURLConnection) urlTrailer.openConnection();
                    conn.connect();
                    root = mapper.readTree(conn.getInputStream());
                    js = root.at("/results/0/key");
                    pelicula.setUrl_trailer("https://www.youtube-nocookie.com/embed/" + js.asText());
                }
            }
            //Se guarda en la base de datos la pelicula
            peliculaRepository.save(pelicula);
        }
        return pelicula;
    }
}