package com.sbnote.service;

import java.util.List;

import com.sbnote.exception.ResourceNotFoundException;
import com.sbnote.model.Note;

public interface NoteService {

	List<Note> getAllNotes(String userId);
	Note createNote(Note note);
	Note getNoteById(Long noteId, String userId);
	Note updateNote(Long noteId, String userId, Note noteDetails) throws ResourceNotFoundException;
	void deleteNote(Long noteId, String userId) throws ResourceNotFoundException;
	void deleteAllNotes(String userId) throws ResourceNotFoundException;
}
