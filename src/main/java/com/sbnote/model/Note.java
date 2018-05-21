package com.sbnote.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "notes")
public class Note {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
	
    @Size(max = 8)
    private String userId;

    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    @Size(max = 250)
    private String content;
    
	public Note(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public Note() {
	}

	public Long getId() {
		return noteId;
	}

	public void setId(Long noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
