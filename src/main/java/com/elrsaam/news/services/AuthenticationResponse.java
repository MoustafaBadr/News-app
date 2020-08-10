package com.elrsaam.news.services;



public class AuthenticationResponse {

	public AuthenticationResponse(String authenticationToken, String username) {
		this.authenticationToken = authenticationToken;
		this.username = username;
	}
	
	public String getAuthenticationToken() {
		return authenticationToken;
	}
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String authenticationToken;
    private String username;
}
