package com.sbnote.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnote.exception.ResourceNotFoundException;
import com.sbnote.model.Note;
import com.sbnote.repository.NoteRepository;
import com.sbnote.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {
	
    @Autowired
    NoteRepository noteRepository;
    
    public List<Note> getAllNotes(String userId) {
        return noteRepository.findByUserId(userId);
    }
    
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }
    
    public Note getNoteById(Long noteId, String userId) {
        return noteRepository.findByNoteIdOrUserId(noteId, userId);
    }
    
    public Note updateNote(Long noteId, String userId, Note noteDetails) throws ResourceNotFoundException {

    	Note note = noteRepository.findByNoteIdOrUserId(noteId, userId);
    	if (note == null){
            throw new ResourceNotFoundException("Note to update does not exist");
    	}
    	note.setTitle(noteDetails.getTitle());
    	note.setContent(noteDetails.getContent());

    	Note updatedNote = noteRepository.save(note);
    	return updatedNote;
    }
    
    public void deleteNote(Long noteId, String userId) throws ResourceNotFoundException {
        Note note = noteRepository.findByNoteIdOrUserId(noteId, userId);
        
        if (note == null){
            throw new ResourceNotFoundException("Note to delete does not exist");
    	}
        
        System.out.println(note.getContent());
        noteRepository.delete(note);
    }

	@Override
	public void deleteAllNotes(String userId) throws ResourceNotFoundException {
        List<Note> noteList = noteRepository.findByUserId(userId);
        
        if (noteList == null || noteList.isEmpty()){
            throw new ResourceNotFoundException("Note to delete does not exist");
    	}
        
		noteRepository.delete(noteList);
	}
}
