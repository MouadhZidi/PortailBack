package tn.teriak.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;
	private String matricule;

	private String email;

	private String password;

	private String nom;

	private String prenom;
	private int active;

	public int getActive() {
		return active;
	}


	public void setActive(int active) {
		this.active = active;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(Integer id, String username, String matricule, String email, String password, String nom, String prenom,
			Set<Role> roles,int active) {
		super();
		this.id = id;
		this.username = username;
		this.matricule = matricule;
		this.email = email;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.roles = roles;
		this.active=active;
	}
	

	public User(String username, String matricule, String email, String password, String nom, String prenom) {
		super();
		this.username = username;
		this.matricule = matricule;
		this.email = email;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
	}


	public User() {
		super();
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
