package tn.teriak.payload.request;




public class SignupRequest {

	private String username;
	private String matricule;

	private String email;

	private String password;

	private String nom;

	private String prenom;

	private Integer role;
	private int active;


	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public SignupRequest() {
		super();
	}

	public SignupRequest(String username, String matricule, String email, String password, String nom, String prenom,
			Integer role,int active) {
		super();
		this.username = username;
		this.matricule = matricule;
		this.email = email;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.role = role;
		this.active=active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
	
	

	

}
