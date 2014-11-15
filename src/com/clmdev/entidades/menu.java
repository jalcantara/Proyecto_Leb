package com.clmdev.entidades;

public class menu {
	private String titulo;
	private int img;
	
	
	
	public menu(String titulo, int img) {
		super();
		this.titulo = titulo;
		this.img = img;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getImg() {
		return img;
	}
	public void setImg(int img) {
		this.img = img;
	}
	
	
	
}
