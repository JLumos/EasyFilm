package upm.daw.easyfilm.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import upm.daw.easyfilm.model.Pelicula;
import upm.daw.easyfilm.model.Usuario;
import upm.daw.easyfilm.repository.PeliculaRepository;
import upm.daw.easyfilm.repository.UsuarioRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class DatabaseLoader {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PeliculaRepository peliculaRepository;

    @PostConstruct
    private void initDatabase() {
        // Usuario #1: "user", with password "p1" and role "USER"
        GrantedAuthority[] userRoles = { new SimpleGrantedAuthority("ROLE_USER") };
        Usuario u1 = new Usuario();
        u1.setUser("jesus");
        u1.setEmail("jesus@easyfilm.com");
        u1.setPassword("p1");
        u1.setNombre("Jesus");
        u1.setRoles(Arrays.asList(userRoles));

        Usuario u2 = new Usuario();
        u2.setUser("ale");
        u2.setEmail("ale@easyfilm.com");
        u2.setPassword("p2");
        u2.setNombre("Alejandra");
        u2.setRoles(Arrays.asList(userRoles));

        Usuario u3 = new Usuario();
        u3.setUser("lumos");
        u3.setEmail("lumos@easyfilm.com");
        u3.setPassword("p1");
        u3.setNombre("Lumos");
        u3.setRoles(Arrays.asList(userRoles));

        Usuario u4 = new Usuario();
        u4.setUser("pepe");
        u4.setEmail("pepe@easyfilm.com");
        u4.setPassword("p1");
        u4.setNombre("pepe");
        u4.setRoles(Arrays.asList(userRoles));

        Usuario u5 = new Usuario();
        u5.setUser("Eduardo");
        u5.setEmail("edu@easyfilm.com");
        u5.setPassword("p1");
        u5.setNombre("Eduardo");
        u5.setRoles(Arrays.asList(userRoles));

        Usuario u6 = new Usuario();
        u6.setUser("Alejandra");
        u6.setEmail("alejandra@easyfilm.com");
        u6.setPassword("p1");
        u6.setNombre("Alejandra");
        u6.setRoles(Arrays.asList(userRoles));

        Usuario u7 = new Usuario();
        u7.setUser("Chechu");
        u7.setEmail("chechu@easyfilm.com");
        u7.setPassword("p1");
        u7.setNombre("Chechu");
        u7.setRoles(Arrays.asList(userRoles));

        Usuario u8 = new Usuario();
        u8.setUser("Jandris");
        u8.setEmail("jandris@easyfilm.com");
        u8.setPassword("p1");
        u8.setNombre("Jandris");
        u8.setRoles(Arrays.asList(userRoles));

        Usuario u9 = new Usuario();
        u9.setUser("Mariia");
        u9.setEmail("maria@easyfilm.com");
        u9.setPassword("p1");
        u9.setNombre("Maria");
        u9.setRoles(Arrays.asList(userRoles));

        Usuario u10 = new Usuario();
        u10.setUser("Juan");
        u10.setEmail("jn@easyfilm.com");
        u10.setPassword("p1");
        u10.setNombre("JuanFran");
        u10.setRoles(Arrays.asList(userRoles));

        Usuario u11 = new Usuario();
        u11.setUser("Rbn");
        u11.setEmail("rb@easyfilm.com");
        u11.setPassword("p1");
        u11.setNombre("Ruben");
        u11.setRoles(Arrays.asList(userRoles));

        Usuario u12 = new Usuario();
        u12.setUser("Sierra");
        u12.setEmail("sisi@easyfilm.com");
        u12.setPassword("p1");
        u12.setNombre("Sierra");
        u12.setRoles(Arrays.asList(userRoles));

        Usuario u13 = new Usuario();
        u13.setUser("Toni");
        u13.setEmail("ant@easyfilm.com");
        u13.setPassword("p1");
        u13.setNombre("Antonio");
        u13.setRoles(Arrays.asList(userRoles));

        Usuario u14 = new Usuario();
        u14.setUser("Marta");
        u14.setEmail("marta@easyfilm.com");
        u14.setPassword("p1");
        u14.setNombre("Marta");
        u14.setRoles(Arrays.asList(userRoles));

        Usuario u15 = new Usuario();
        u15.setUser("Laus");
        u15.setEmail("laus@easyfilm.com");
        u15.setPassword("p1");
        u15.setNombre("Laura");
        u15.setRoles(Arrays.asList(userRoles));

        Usuario u16 = new Usuario();
        u16.setUser("alee_lugi");
        u16.setEmail("alelugi@easyfilm.com");
        u16.setPassword("p1");
        u16.setNombre("Ale");
        u16.setRoles(Arrays.asList(userRoles));

        Usuario u17 = new Usuario();
        u17.setUser("Antius");
        u17.setEmail("antius@easyfilm.com");
        u17.setPassword("p1");
        u17.setNombre("Antia");
        u17.setRoles(Arrays.asList(userRoles));

        Usuario u18 = new Usuario();
        u18.setUser("Ana");
        u18.setEmail("ana@easyfilm.com");
        u18.setPassword("p1");
        u18.setNombre("Ana");
        u18.setRoles(Arrays.asList(userRoles));

        Usuario u19 = new Usuario();
        u19.setUser("Manu");
        u19.setEmail("manu@easyfilm.com");
        u19.setPassword("p1");
        u19.setNombre("Manuel");
        u19.setRoles(Arrays.asList(userRoles));

        Usuario u20 = new Usuario();
        u20.setUser("Samu");
        u20.setEmail("samu@easyfilm.com");
        u20.setPassword("p1");
        u20.setNombre("Samuel");
        u20.setRoles(Arrays.asList(userRoles));

        Usuario u21 = new Usuario();
        u21.setUser("Angela");
        u21.setEmail("angela@easyfilm.com");
        u21.setPassword("angela");
        u21.setNombre("Cara huevo❤");
        u21.setRoles(Arrays.asList(userRoles));

        usuarioRepository.save(u1);
        usuarioRepository.save(u2);
        usuarioRepository.save(u3);
        usuarioRepository.save(u4);
        usuarioRepository.save(u5);
        usuarioRepository.save(u6);
        usuarioRepository.save(u7);
        usuarioRepository.save(u8);
        usuarioRepository.save(u9);
        usuarioRepository.save(u10);
        usuarioRepository.save(u11);
        usuarioRepository.save(u12);
        usuarioRepository.save(u13);
        usuarioRepository.save(u14);
        usuarioRepository.save(u15);
        usuarioRepository.save(u16);
        usuarioRepository.save(u17);
        usuarioRepository.save(u18);
        usuarioRepository.save(u19);
        usuarioRepository.save(u20);
        usuarioRepository.save(u21);


        // Usuario #2: "root", with password "p2" and roles "USER" and "ADMIN"
        GrantedAuthority[] adminRoles = { new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER") };
        usuarioRepository.save(new Usuario("admin", "admin", "ab@x.com",Arrays.asList(adminRoles)));

        Pelicula p1 = new Pelicula();
        p1.setNombrePelicula("Titanic");
        p1.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg");
        p1.setDescripcion("seventeen-year-old aristocrat (Rose) falls in love with a kind but poor artist (Jack) aboard the luxurious, ill-fated R.M.S. Titanic. Don't forget though, that Titanic is indeed a ship of dreams. Climb aboard and bon voyage.");
        p1.setUrl_trailer("https://www.youtube-nocookie.com/embed/2e-eXJ6HgkQ");

        Pelicula p2 = new Pelicula();
        p2.setNombrePelicula("Harry Potter and the Goblet of Fire");
        p2.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMTI1NDMyMjExOF5BMl5BanBnXkFtZTcwOTc4MjQzMQ@@._V1_SX300.jpg");
        p2.setDescripcion("Harry starts his fourth year at Hogwarts, competes in the treacherous Triwizard Tournament and faces the evil Lord Voldemort. Ron and Hermione help Harry manage the pressure – but Voldemort lurks, awaiting his chance to destroy Harry and all that he stands for.");
        p2.setUrl_trailer("https://www.youtube-nocookie.com/embed/PFWAOnvMd1Q");

        Pelicula p3 = new Pelicula();
        p3.setNombrePelicula("Taken");
        p3.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMTM4NzQ0OTYyOF5BMl5BanBnXkFtZTcwMDkyNjQyMg@@._V1_SX300.jpg");
        p3.setDescripcion("A retired CIA agent travels across Europe and relies on his old skills to save his estranged daughter, who has been kidnapped while on a trip to Paris.");
        p3.setUrl_trailer("https://www.youtube-nocookie.com/embed/CvUxdQ4q-Lg");

        Pelicula p4 = new Pelicula();
        p4.setNombrePelicula("The Transporter");
        p4.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMTk2NDc2MDAxN15BMl5BanBnXkFtZTYwNDc1NDY2._V1_SX300.jpg");
        p4.setDescripcion("Frank is hired to \"transport\" packages for unknown clients and has made a very good living doing so. But when asked to move a package that begins moving, complications arise.");
        p4.setUrl_trailer("https://www.youtube-nocookie.com/embed/0poXFSvX0_4");

        Pelicula p5 = new Pelicula();
        p5.setNombrePelicula("Fantastic Beasts and Where to Find Them");
        p5.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMjMxOTM1OTI4MV5BMl5BanBnXkFtZTgwODE5OTYxMDI@._V1_SX300.jpg");
        p5.setDescripcion("The adventures of writer Newt Scamander in New York's secret community of witches and wizards seventy years before Harry Potter reads his book in school.");
        p5.setUrl_trailer("https://www.youtube-nocookie.com/embed/Vso5o11LuGU");

        Pelicula p6 = new Pelicula();
        p6.setNombrePelicula("A Clockwork Orange");
        p6.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMTY3MjM1Mzc4N15BMl5BanBnXkFtZTgwODM0NzAxMDE@._V1_SX300.jpg");
        p6.setDescripcion("In future Britain, Alex DeLarge, a charismatic and psycopath delinquent, who likes to practice crimes and ultra-violence with his gang, is jailed and volunteers for an experimental aversion therapy developed by the government in an effort to solve society's crime problem - but not all goes according to plan.");
        p6.setUrl_trailer("https://www.youtube-nocookie.com/embed/SPRzm8ibDQ8");

        Pelicula p7 = new Pelicula();
        p7.setNombrePelicula("The Lion King");
        p7.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BYTYxNGMyZTYtMjE3MS00MzNjLWFjNmYtMDk3N2FmM2JiM2M1XkEyXkFqcGdeQXVyNjY5NDU4NzI@._V1_SX300.jpg");
        p7.setDescripcion("Lion cub and future king Simba searches for his identity. His eagerness to please others and penchant for testing his boundaries sometimes gets him into trouble.");
        p7.setUrl_trailer("https://www.youtube-nocookie.com/embed/jOIu472cCq0");

        Pelicula p8 = new Pelicula();
        p8.setNombrePelicula("Star Wars: Episode IV - A New Hope");
        p8.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BYzQ2OTk4N2QtOGQwNy00MmI3LWEwNmEtOTk0OTY3NDk2MGJkL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg");
        p8.setDescripcion("Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a wookiee and two droids to save the galaxy from the Empire's world-destroying battle-station, while also attempting to rescue Princess Leia from the evil Darth Vader.");
        p8.setUrl_trailer("https://www.youtube-nocookie.com/embed/1g3_CFmnU7k");

        Pelicula p9 = new Pelicula();
        p9.setNombrePelicula("The Da Vinci Code");
        p9.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMjIxMjQyMTc3Nl5BMl5BanBnXkFtZTcwMTA1MDUzMw@@._V1_SX300.jpg");
        p9.setDescripcion("A murder inside the Louvre and clues in Da Vinci paintings lead to the discovery of a religious mystery protected by a secret society for two thousand years -- which could shake the foundations of Christianity.");
        p9.setUrl_trailer("https://www.youtube-nocookie.com/embed/ZjRPTyazKds");

        Pelicula p10 = new Pelicula();
        p10.setNombrePelicula("The Godfather");
        p10.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BZTRmNjQ1ZDYtNDgzMy00OGE0LWE4N2YtNTkzNWQ5ZDhlNGJmL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg");
        p10.setDescripcion("The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.");
        p10.setUrl_trailer("https://www.youtube-nocookie.com/embed/sY1S34973zA");

        Pelicula p11 = new Pelicula();
        p11.setNombrePelicula("Finding Nemo");
        p11.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BZTAzNWZlNmUtZDEzYi00ZjA5LWIwYjEtZGM1NWE1MjE4YWRhXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg");
        p11.setDescripcion("After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.");
        p11.setUrl_trailer("https://www.youtube-nocookie.com/embed/2zLkasScy7A");

        Pelicula p12 = new Pelicula();
        p12.setNombrePelicula("Indiana Jones and the Last Crusade");
        p12.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMjNkMzc2N2QtNjVlNS00ZTk5LTg0MTgtODY2MDAwNTMwZjBjXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg");
        p12.setDescripcion("When Dr. Henry Jones Sr. suddenly goes missing while pursuing the Holy Grail, eminent archaeologist Indiana Jones must follow in his father's footsteps to stop the Nazis from getting their hands on the Holy Grail first.");
        p12.setUrl_trailer("https://www.youtube-nocookie.com/embed/a6JB2suJYHM");

        Pelicula p13 = new Pelicula();
        p13.setNombrePelicula("Pirates of the Caribbean: The Curse of the Black Pearl");
        p13.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BNGYyZGM5MGMtYTY2Ni00M2Y1LWIzNjQtYWUzM2VlNGVhMDNhXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg");
        p13.setDescripcion("Blacksmith Will Turner teams up with eccentric pirate \"Captain\" Jack Sparrow to save his love, the governor's daughter, from Jack's former pirate allies, who are now undead.");
        p13.setUrl_trailer("https://www.youtube-nocookie.com/embed/naQr0uTrH_s");

        Pelicula p14 = new Pelicula();
        p14.setNombrePelicula("Guardians of the Galaxy");
        p14.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMTAwMjU5OTgxNjZeQTJeQWpwZ15BbWU4MDUxNDYxODEx._V1_SX300.jpg");
        p14.setDescripcion("A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.");
        p14.setUrl_trailer("https://www.youtube-nocookie.com/embed/d96cjJhvlMA");

        Pelicula p15 = new Pelicula();
        p15.setNombrePelicula("The Hangover");
        p15.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BYTYxNGMyZTYtMjE3MS00MzNjLWFjNmYtMDk3N2FmM2JiM2M1XkEyXkFqcGdeQXVyNjY5NDU4NzI@._V1_SX300.jpg");
        p15.setDescripcion("Three buddies wake up from a bachelor party in Las Vegas, with no memory of the previous night and the bachelor missing. They make their way around the city in order to find their friend before his wedding.");
        p15.setUrl_trailer("https://www.youtube-nocookie.com/embed/tcdUhdOlz9M");

        Pelicula p16 = new Pelicula();
        p16.setNombrePelicula("The Avengers");
        p16.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMTk2NTI1MTU4N15BMl5BanBnXkFtZTcwODg0OTY0Nw@@._V1_SX300.jpg");
        p16.setDescripcion("Earth's mightiest heroes must come together and learn to fight as a team if they are to stop the mischievous Loki and his alien army from enslaving humanity.");
        p16.setUrl_trailer("https://www.youtube-nocookie.com/embed/eOrNdBpGMv8");

        Pelicula p17 = new Pelicula();
        p17.setNombrePelicula("The Wolf of Wall Street");
        p17.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMjIxMjgxNTk0MF5BMl5BanBnXkFtZTgwNjIyOTg2MDE@._V1_SX300.jpg");
        p17.setDescripcion("ased on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.");
        p17.setUrl_trailer("https://www.youtube-nocookie.com/embed/pabEtIERlic");

        Pelicula p18 = new Pelicula();
        p18.setNombrePelicula("Quantum of Solace");
        p18.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BMTY0MjI4MDI5MF5BMl5BanBnXkFtZTcwODkwNjk3MQ@@._V1_SX300.jpg");
        p18.setDescripcion("James Bond descends into mystery as he tries to stop a mysterious organization from eliminating a country's most valuable resource. All the while, he still tries to seek revenge over the death of his love.");
        p18.setUrl_trailer("https://www.youtube-nocookie.com/embed/f6acw690AqQ");

        Pelicula p19 = new Pelicula();
        p19.setNombrePelicula("Mary Poppins");
        p19.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BNmJkODczNjItNDI5Yy00MGI1LTkyOWItZDNmNjM4ZGI1ZDVlL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMDI2NDg0NQ@@._V1_SX300.jpg");
        p19.setDescripcion("In turn of the century London, a magical nanny employs music and adventure to help two neglected children become closer to their father.");
        p19.setUrl_trailer("https://www.youtube-nocookie.com/embed/nOfH7uEojKk");

        Pelicula p20 = new Pelicula();
        p20.setNombrePelicula("The Lord of the Rings: The Fellowship of the Ring");
        p20.setUrl_portada("https://images-na.ssl-images-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg");
        p20.setDescripcion("A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle Earth from the Dark Lord Sauron.");
        p20.setUrl_trailer("https://www.youtube-nocookie.com/embed/V75dMMIW2B4");

        peliculaRepository.save(p1);
        peliculaRepository.save(p2);
        peliculaRepository.save(p3);
        peliculaRepository.save(p4);
        peliculaRepository.save(p5);
        peliculaRepository.save(p6);
        peliculaRepository.save(p7);
        peliculaRepository.save(p8);
        peliculaRepository.save(p9);
        peliculaRepository.save(p10);
        peliculaRepository.save(p11);
        peliculaRepository.save(p12);
        peliculaRepository.save(p13);
        peliculaRepository.save(p14);
        peliculaRepository.save(p15);
        peliculaRepository.save(p16);
        peliculaRepository.save(p17);
        peliculaRepository.save(p18);
        peliculaRepository.save(p19);
        peliculaRepository.save(p20);




    }
}

