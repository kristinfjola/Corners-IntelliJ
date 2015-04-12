/**
 * @author Kristin Fjola Tomasdottir
 * @date 	19.03.2015
 * @goal 	Information about the user's Facebook account
 */
package com.corners.game;

public class FacebookUser {
	private String id;
	private String firstName;
	private String fullName;
	private boolean triedGettingPublishPermission;
	
	/**
	 * @return the user's Facebook id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id
	 * sets the user's Facebook id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the user's Facebook first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName
	 * sets the user's Facebook first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the user's Facebook full name
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName
	 * sets the user's Facebook full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	/**
	 * @return whether we've tried to get permission to publish on facebook
	 */
	public boolean isTriedGettingPublishPermission() {
		return triedGettingPublishPermission;
	}
	/**
	 * @param triedGettingPublishPermission
	 * sets whether we've tried to get permission to publish on facebook
	 */
	public void setTriedGettingPublishPermission(
			boolean triedGettingPublishPermission) {
		this.triedGettingPublishPermission = triedGettingPublishPermission;
	}
}
