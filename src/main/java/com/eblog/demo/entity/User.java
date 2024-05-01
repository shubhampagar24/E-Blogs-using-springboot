package com.eblog.demo.entity;

import jakarta.persistence.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name="users")
public class User {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String name;

  @Column(nullable=false, unique=true)
  private String email;

  @Column(nullable=false)
  private String password;

  @Column(nullable = true, length = 64)
  private String profileImage;

  @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
  @JoinTable(
      name="users_roles",
      joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
      inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
  private List<Role> roles = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<Post> posts = new ArrayList < > ();

  @Transient
  public String getProfileImagePath() {
    String path = "/img/user-photos/" + id + "/" + profileImage;
   
    
    if (profileImage == null || id == null || (profileImage !=null && profileImage.trim().isEmpty())){
      return "/img/user.png";
    }
    return path;
  }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
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

public String getProfileImage() {
	return profileImage;
}

public void setProfileImage(String profileImage) {
	this.profileImage = profileImage;
}

public List<Role> getRoles() {
	return roles;
}

public void setRoles(List<Role> roles) {
	this.roles = roles;
}

public List<Post> getPosts() {
	return posts;
}

public User(Long id, String name, String email, String password, String profileImage, List<Role> roles,
		List<Post> posts) {
	super();
	this.id = id;
	this.name = name;
	this.email = email;
	this.password = password;
	this.profileImage = profileImage;
	this.roles = roles;
	this.posts = posts;
}

public void setPosts(List<Post> posts) {
	this.posts = posts;
}



public User() {
	super();
	// TODO Auto-generated constructor stub
}
  
}