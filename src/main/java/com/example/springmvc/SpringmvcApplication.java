package com.example.springmvc;

import java.util.ArrayList;
import java.io.File;
import java.sql.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
@RestController
public class SpringmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmvcApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return "COUCOU SPRING";
	}

	@PostMapping("/login")
	@CrossOrigin
	public int Login(String email, String mdp) throws Exception {
		Client c = new Client();
		c.setEmail(email);
		c.setMdp(mdp);
		return c.Login();
	}

	@PostMapping("/inscription")
	@CrossOrigin
	public int Inscrisption(String nom, String prenom, String mdp, String email, float budget) throws Exception {
		Client c = new Client();
		c.setNom(nom);
		c.setPrenom(prenom);
		c.setMdp(mdp);
		c.setEmail(email);
		c.setBudget(budget);
		return c.Inscrisption();
	}

	@GetMapping("/mesencheres/{id}")
	@CrossOrigin
	public ArrayList<Produit> MesEncheres(@PathVariable int id) throws Exception {
		Client c = new Client();
		c.setIdClient(id);
		return c.MesEncheres();
	}

	@PostMapping("/demanderecharge")
	@CrossOrigin
	public Integer demandeRecharge(int demande, int idClient) throws Exception {
		Client c = new Client();
		c.setIdClient(idClient);
		return c.DemandeRecharge(demande);
	}

	@GetMapping("listeTypeProduit")
	@CrossOrigin
	public ArrayList<TypeProduit> listeTP() throws Exception{
		TypeProduit tp = new TypeProduit();
		return tp.ListTypeProduit();
	}

	@PostMapping("ajoutEnchere")
	@CrossOrigin
	public Integer ajoutEnchere(int idtp, String description, String[] images, int prix, int idClient, int duree) throws Exception {
		Client c = new Client();
		c.setIdClient(idClient);
		TypeProduit tp = new TypeProduit();
		tp.setId(idtp);
		Produit p = new Produit();
		p.setTp(tp);
		p.setDescription(description);
		p.setImage(images[0]+images[1]);
		p.setPrixminimum(prix);
		p.setIdProprio(c.getIdClient());
		p.setDuree(duree);
		if(c.ajoutEnchere(p)>0){
			int lastId = Produit.getLastId();
			
			for(int i=0;i<images.length;i++ ){
				p.setId(lastId);
				p.setImage(images[i]);
				p.InsertPhotos();
			}
			return images.length;
		}
		return 0;
	}

	@GetMapping("notification")
	@CrossOrigin
	public Object GetNotif(int idClient, int idProduit) throws Exception{
		Client c = new Client();
		c.setIdClient(idClient);
		return c.getSubstract(idProduit);
	}

	@GetMapping("test")
	@CrossOrigin
	public int Test(String[] photos){
		int taille = photos.length;
		return taille;
	}
}
